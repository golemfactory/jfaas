package network.golem.jfaas.example.yapapi;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureResult<V> implements Future<V>, Serializable {
    private V result;

    public FutureResult(V result) {
        this.result = result;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return result;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return result;
    }
}

