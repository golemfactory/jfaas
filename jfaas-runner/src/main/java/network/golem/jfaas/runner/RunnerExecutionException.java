package network.golem.jfaas.runner;

public class RunnerExecutionException extends RuntimeException {
    public RunnerExecutionException() {
    }

    public RunnerExecutionException(String message) {
        super(message);
    }

    public RunnerExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RunnerExecutionException(Throwable cause) {
        super(cause);
    }

    public RunnerExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
