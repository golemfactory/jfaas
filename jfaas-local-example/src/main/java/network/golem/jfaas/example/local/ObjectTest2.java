package network.golem.jfaas.example.local;

import network.golem.jfaas.proxy.JfaasProxy;

public class ObjectTest2 {
    public static void main(String[] args) {
        System.out.println("testing hello+world");
        ObjectTestBeanLocal proxy = JfaasProxy.getProxy(ObjectTestBeanLocal.class, ObjectTestBean.class);
        try {
            System.out.println("result: "+proxy.convertFail(new ObjectTestArgument("hello", "world")).getValue());
        } catch (ObjectTestException e) {
            System.out.println("as expected, exception: "+e.getClass().getName());
        }
    }
}
