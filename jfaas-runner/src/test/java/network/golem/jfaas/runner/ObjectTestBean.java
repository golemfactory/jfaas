package network.golem.jfaas.runner;

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
    public ObjectTestReturn convertToNull(ObjectTestArgument argument) {
        return null;
    }
    @Override
    public void nullValidate(Object argument) {
        if (argument == null)
            throw new RunnerException("object is null");
    }
}
