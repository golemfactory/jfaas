package network.golem.jfaas.runner;

import java.io.Serializable;
import java.util.Objects;

public class ObjectTestReturn implements Serializable {
    private String value;

    public ObjectTestReturn(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectTestReturn that = (ObjectTestReturn) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
