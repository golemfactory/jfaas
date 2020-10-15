package network.golem.jfaas.runner;

public interface ObjectTestBeanLocal {
    public ObjectTestReturn convert(ObjectTestArgument argument);
    public ObjectTestReturn convertFail(ObjectTestArgument argument) throws ObjectTestException;
    ObjectTestReturn convertToNull(ObjectTestArgument argument);
}
