package network.golem.jfaas.example.local;

import java.util.concurrent.Future;

public class ObjectTestBean implements ObjectTestBeanLocal {
    @Override
    public ObjectTestReturn convert(ObjectTestArgument argument) {
        return new ObjectTestReturn(argument.getFirst().toUpperCase()+" "+argument.getSecond().toUpperCase());
    }

    @Override
    public ObjectTestReturn convertFail(ObjectTestArgument argument) throws ObjectTestException {
        throw new ObjectTestException();
    }

    @Override
    public Future<ObjectTestReturn> convertAsync(ObjectTestArgument argument) {
        return new FutureResult<ObjectTestReturn>(convert(argument));
    }
}
