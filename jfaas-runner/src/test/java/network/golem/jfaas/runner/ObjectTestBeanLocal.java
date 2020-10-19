package network.golem.jfaas.runner;

public interface ObjectTestBeanLocal {
    public ObjectTestReturn convert(ObjectTestArgument argument);
    public ObjectTestReturn convertFail(ObjectTestArgument argument) throws ObjectTestException;
    public ObjectTestReturn convertToNull(ObjectTestArgument argument);
    public void nullValidate(Object argument);
}
