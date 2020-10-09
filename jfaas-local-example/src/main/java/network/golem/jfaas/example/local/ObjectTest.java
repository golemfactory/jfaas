package network.golem.jfaas.example.local;

import network.golem.jfaas.proxy.JfaasProxy;

public class ObjectTest {
    public static void main(String[] args) {
        System.out.println("testing hello+world");
        ObjectTestBeanLocal proxy = JfaasProxy.getProxy(ObjectTestBeanLocal.class, ObjectTestBean.class);
        System.out.println("result: "+proxy.convert(new ObjectTestArgument("hello", "world")).getValue());
    }
}
