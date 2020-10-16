from datetime import timedelta

import argparse
import asyncio
import pathlib
import sys

import yapapi
from yapapi.log import enable_default_logger, log_summary, log_event_repr  # noqa
from yapapi.runner import Engine, Task, vm
from yapapi.runner.ctx import WorkContext
import utils

async def main(subnet_tag, tasks):
    package = await vm.repo(
        image_hash="1504e113cf2d70e9a60858bbf8b2c8b28559c6e7d3ea381a4664e95a", # mbenke/yaja-0.0.1
        min_mem_gib=1.5,
        min_storage_gib=2.0,
    )

    async def worker(ctx: WorkContext, tasks):


        async for task in tasks:
            data = task.data
            ctx.send_file(data["jar"], "/golem/input/work.jar") 
            ctx.run("/bin/sh", "/golem/run.sh")
            ctx.download_file("/golem/output/output.txt", "output.txt")
            yield ctx.commit()
            # TODO: Check if job results are valid
            # and reject by: task.reject_task(msg = 'invalid file')
            task.accept_task(result="OK")

        ctx.log("done")

    init_overhead: timedelta = timedelta(minutes=3)

    async with Engine(
        package=package,
        max_workers=1,
        budget=10.0,
        timeout=init_overhead + timedelta(minutes=len(tasks) * 3),
        subnet_tag=subnet_tag,
        event_emitter=log_summary(log_event_repr),
    ) as engine:

        async for proc_task in engine.map(
            worker, [Task(data=task) for task in tasks]
        ):
            print(f"Task computed: {proc_task} result=...")


if __name__ == "__main__":
    ap = argparse.ArgumentParser()
    ap.add_argument("--jar", required=True)
    arguments = ap.parse_args()
    jar = arguments.jar
    # output = arguments.output
    subnet = "devnet-alpha.2" # "kubkon"
    tasks = [
        {"jar" : jar }
    ]
    print(tasks)

    loop = asyncio.get_event_loop()
    sys.stderr.write(
        f"yapapi version: {utils.TEXT_COLOR_YELLOW}{yapapi.__version__}{utils.TEXT_COLOR_DEFAULT}\n"
    )
    sys.stderr.write(f"Using subnet: {utils.TEXT_COLOR_YELLOW}{subnet}{utils.TEXT_COLOR_DEFAULT}\n")
    task = loop.create_task(main(subnet_tag=subnet,tasks=tasks))
    try:
        asyncio.get_event_loop().run_until_complete(task)
    except (Exception, KeyboardInterrupt) as e:
        print(e)
        task.cancel()
        asyncio.get_event_loop().run_until_complete(asyncio.sleep(0.3))
