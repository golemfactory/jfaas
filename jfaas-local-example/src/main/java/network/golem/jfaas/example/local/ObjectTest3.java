package network.golem.jfaas.example.local;

import network.golem.jfaas.proxy.JfaasProxy;
import java.util.concurrent.Future;

public class ObjectTest3 {
    public static void main(String[] args) throws Exception {
        System.out.println("testing hello+world");
        ObjectTestBeanLocal proxy = JfaasProxy.getProxy(ObjectTestBeanLocal.class, ObjectTestBean.class);
	Future<ObjectTestReturn> future = proxy.convertAsync(new ObjectTestArgument("hello", "world"));
	ObjectTestReturn result = future.get();
        System.out.println("result: "+result.getValue());
    }
}
