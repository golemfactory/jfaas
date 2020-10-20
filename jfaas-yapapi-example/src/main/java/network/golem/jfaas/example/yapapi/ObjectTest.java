package network.golem.jfaas.example.yapapi;

import network.golem.jfaas.proxy.JfaasProxy;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ObjectTest {
    public static void main(String[] args) {
        System.out.println("testing a random walking");
        long limit = 10_000_000L;
        int triesPerTask = 100;
        int tasks = 4;

        ObjectTestBeanIntf objectTestBean = JfaasProxy.getProxy(ObjectTestBeanIntf.class, ObjectTestBean.class);
//        ObjectTestBeanIntf objectTestBean = new ObjectTestBean();

        ArrayList<Future<Double>> futures = new ArrayList<>();
        for (int n = 0; n < tasks; n++) {
            Future<Double> future = objectTestBean.walk(limit, triesPerTask);
            futures.add(future);
        }
        double averageSteps = 0.0;
        for (Future<Double> future : futures) {
            try {
                double steps = future.get();
                averageSteps += steps/tasks;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("average number of steps to reach "+limit+" is "+averageSteps);
    }
}
