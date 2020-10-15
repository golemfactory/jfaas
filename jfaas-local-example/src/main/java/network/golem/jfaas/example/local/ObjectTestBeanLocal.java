package network.golem.jfaas.example.local;

public interface ObjectTestBeanLocal {
    public ObjectTestReturn convert(ObjectTestArgument argument);
    public ObjectTestReturn convertFail(ObjectTestArgument argument) throws ObjectTestException;
}
