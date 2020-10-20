# jFaaS through yapapi

This is an example of using `jfaas-proxy` and `jfaas-runner`. The delegated `jfaas-runner` is executed on Yagna using `yapapi`. 
Works on Linux because of `yapapi-command.sh`, sorry.

First run `mvn install` in `jfaas-proxy` and `mvn install` in `jfaas-runner` and `mvn package` in `jfaas-yapapi-example`. Make sure that `yapapi-command.sh` is executable.

Finally we need `jfaas-runner` at the hand. There is one reference to it in `run.py`.
```
cp ../jfaas-runner/target/jfaas-runner-1.jar .
```

In the directory `jfaas-yapapi-example` run the command:
```
java -cp target/jfaas-yapapi-example-1.jar:../jfaas-proxy/target/jfaas-proxy-1.jar -Djfaas.proxy.command=./yapapi-command.sh -Djfaas.proxy.client.jar=target/jfaas-yapapi-example-1.jar network.golem.jfaas.example.yapapi.ObjectTest
```

`jfaas-yapapi-example.jar` is the target `jar`, but in general the target jar can be different than the calling jar. 
If the target jar has dependencies, then required libraries should be included in the target jar except `jfaas-proxy` jar.
