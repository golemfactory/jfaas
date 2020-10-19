# jfaas

`jfaas` is a tool for running java apps on Yagna. It delegates calls and executes on the remote machine.

See README in modules below for more detailed information. In particular see `jfaas-local-example` for information on how to use it.

`jfaas-proxy` -- the library required by the caller java app. Add it to your classpath.

`jfaas-runner` -- the java app that calls the target client jar. Requires the client jar in the classpath.

`jfaas-local-example` -- an example on how to use jfaas and the demonstation how it works locally. In order to run your jfaas app locally see `local-command.sh` and the app run command in paticular.
