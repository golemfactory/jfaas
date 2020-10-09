package network.golem.jfaas.proxy;

public interface TestBeanLocal {
    int one();

    Integer addOne(Integer i);

    void nothing();

    String nullTest();

    String concat(String[] args);
}
