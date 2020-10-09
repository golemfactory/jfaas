# jFaaS Local Execution

This is an example of using `jfaas-proxy` and `jfaas-runner`. The delegated `jfaas-runner` is executed locally in a separate `jvm`. 
Works on Linux because of `local-command.sh`, sorry.

First run `mvn install` in `jfaas-proxy` and `mvn install` in `jfaas-runner` and `mvn package` in `jfaas-local-example`. Make sure that `local-command.sh` is executable.

In the directory `jfaas-local-example` run the command:
```
java -cp target/jfaas-local-example-1.jar:../jfaas-proxy/target/jfaas-proxy-1.jar -Djfaas.proxy.command=./local-command.sh -Djfaas.proxy.client.jar=target/jfaas-local-example-1.jar network.golem.jfaas.example.local.ObjectTest
```

Investigate `local-command.sh`. It runs `jfaas-runner`. `jfaas-local-example.jar` is the target `jar`, but in general the target jar can be different than the calling jar. 
If the target jar has dependencies, then required libraries should be included in the target jar or should be enlisted in the `-cp` in the command like `local-command.sh`.
