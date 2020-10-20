package network.golem.jfaas.example.yapapi;

import java.util.concurrent.Future;

public interface ObjectTestBeanIntf {
    /**
     * a random walking but the step increases by one every time
     * it walks until it passes limit
     * it does some tries and calculates average number of steps
     * @param limit inclusive
     * @param tries positive number of walks
     */
    public Future<Double> walk(long limit, int tries);
}
