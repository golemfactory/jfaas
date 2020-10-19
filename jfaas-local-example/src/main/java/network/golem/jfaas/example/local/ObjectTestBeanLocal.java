package network.golem.jfaas.example.local;

import java.util.concurrent.Future;

public interface ObjectTestBeanLocal {
    public ObjectTestReturn convert(ObjectTestArgument argument);
    public ObjectTestReturn convertFail(ObjectTestArgument argument) throws ObjectTestException;
    public Future<ObjectTestReturn> convertAsync(ObjectTestArgument argument);
}
