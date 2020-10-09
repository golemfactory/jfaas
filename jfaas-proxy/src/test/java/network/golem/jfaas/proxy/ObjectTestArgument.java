package network.golem.jfaas.proxy;

import java.io.Serializable;

public class ObjectTestArgument implements Serializable {
    private String first;
    private String second;

    public ObjectTestArgument(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }
}
