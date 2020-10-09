package network.golem.jfaas.proxy;

import org.junit.jupiter.api.Test;

import static network.golem.jfaas.proxy.JfaasProxy.*;

public class VoidTest {
    @Test
    public void emptyTest() {
        System.setProperty(JFAAS_PROXY_WORKING_DIR_PROPERTY, System.getProperty("java.io.tmpdir"));
        System.setProperty(JFAAS_PROXY_EXTERNAL_COMMAND_PROPERTY, "src/test/scripts/voidTestCommand.sh");
        System.setProperty(JFAAS_PROXY_CLIENT_JAR_PROPERTY, "target/test-classes");
        VoidTestBeanLocal proxy = getProxy(VoidTestBeanLocal.class, VoidTestBean.class);
        proxy.emptyTest();
    }
    @Test
    public void intTest() {
        System.setProperty(JFAAS_PROXY_WORKING_DIR_PROPERTY, System.getProperty("java.io.tmpdir"));
        System.setProperty(JFAAS_PROXY_EXTERNAL_COMMAND_PROPERTY, "src/test/scripts/voidTestCommand.sh");
        System.setProperty(JFAAS_PROXY_CLIENT_JAR_PROPERTY, "target/test-classes");
        VoidTestBeanLocal proxy = getProxy(VoidTestBeanLocal.class, VoidTestBean.class);
        proxy.intTest(17);
    }
    @Test
    public void integerTest() {
        System.setProperty(JFAAS_PROXY_WORKING_DIR_PROPERTY, System.getProperty("java.io.tmpdir"));
        System.setProperty(JFAAS_PROXY_EXTERNAL_COMMAND_PROPERTY, "src/test/scripts/voidTestCommand.sh");
        System.setProperty(JFAAS_PROXY_CLIENT_JAR_PROPERTY, "target/test-classes");
        VoidTestBeanLocal proxy = getProxy(VoidTestBeanLocal.class, VoidTestBean.class);
        proxy.integerTest(1234);
    }
    @Test
    public void arrayTest() {
        System.setProperty(JFAAS_PROXY_WORKING_DIR_PROPERTY, System.getProperty("java.io.tmpdir"));
        System.setProperty(JFAAS_PROXY_EXTERNAL_COMMAND_PROPERTY, "src/test/scripts/voidTestCommand.sh");
        System.setProperty(JFAAS_PROXY_CLIENT_JAR_PROPERTY, "target/test-classes");
        VoidTestBeanLocal proxy = getProxy(VoidTestBeanLocal.class, VoidTestBean.class);
        proxy.arrayTest(null);
        proxy.arrayTest(new String[]{});
        proxy.arrayTest(new String[]{"abc", null, "123"});
    }

}
