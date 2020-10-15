# jfaas-runner

The java app that calls the target client jar.

## Usage

1. Make sure that the client jar is in the classpath.
2. The main class is `network.golem.jfaas.runner.JfaasRunner`. The `jfaas-runner.jar` is not executable.
3. It takes one parameter: a path to the file with serialized call.
4. It returns a serialized value from the call. The return file is stored in the directory containing the call file, and it has `.return` appended to the name.
5. It does not operate as a service. One jvm run - one call execution.
