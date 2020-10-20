package network.golem.jfaas.example.yapapi;

import java.util.concurrent.Future;

public class ObjectTestBean implements ObjectTestBeanIntf {
    @Override
    public Future<Double> walk(long limit, int tries) {
        double sum = 0;
        for (int n = 0; n < tries; n++) {
            long point = 0, step = 0;
            while (point <= limit && point >= -limit) {
                if (Math.random() < 0.5)
                    point -= step;
                else
                    point += step;
                step++;
            }
            sum += step;
        }
        return new FutureResult<>(sum/tries);
    }
}
