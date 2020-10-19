package network.golem.jfaas.proxy;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureImpl<V> implements Future<V> {
    private boolean done = false;
    private V value = null;
    private Throwable error = null;
    final Object mutex = new Object();

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        synchronized (mutex) {
            return false; //not implemented
        }
    }

    @Override
    public boolean isCancelled() {
        synchronized (mutex) {
            return false; //not implemented
        }
    }

    @Override
    public boolean isDone() {
        synchronized (mutex) {
            return done;
        }
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        synchronized (mutex) {
            if (!done) {
                mutex.wait();
            }
            if (error == null)
                return value;
            else
                throw new ExecutionException(error);
        }
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        synchronized (mutex) {
            if (!done) {
                mutex.wait(unit.toMillis(timeout));
                if (!done) {
                    throw new TimeoutException();
                }
            }
            if (error == null)
                return value;
            else
                throw new ExecutionException(error);
        }
    }

    void setValue(V value) {
        synchronized (mutex) {
            done = true;
            this.value = value;
        }
    }

    void setException(Throwable throwable) {
        synchronized (mutex) {
            done = true;
            this.error = throwable;
        }
    }
}
