package network.golem.jfaas.proxy;

import java.util.Arrays;

public class TestBean implements TestBeanLocal {
    @Override
    public int one() {
        return 1;
    }

    @Override
    public Integer addOne(Integer i) {
        return i+1;
    }

    @Override
    public void nothing() {

    }

    @Override
    public String nullTest() {
        return null;
    }

    @Override
    public String concat(String[] args) {
        if (args == null || args.length ==0)
            return "";
        return Arrays.toString(args);
    }
}
