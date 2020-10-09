package network.golem.jfaas.proxy;

import org.junit.jupiter.api.Test;

import static network.golem.jfaas.proxy.JfaasProxy.JFAAS_PROXY_WORKING_DIR_PROPERTY;
import static network.golem.jfaas.proxy.JfaasProxy.JFAAS_PROXY_EXTERNAL_COMMAND_PROPERTY;
import static network.golem.jfaas.proxy.JfaasProxy.JFAAS_PROXY_CLIENT_JAR_PROPERTY;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * to run this test you need to run mvn package for jfaas-runner
 * by default these tests are commented (for this reason)
 */
public class JfaasProxyTest {
    static {
        String tempDir = System.getProperty("java.io.tmpdir");
        System.setProperty(JFAAS_PROXY_WORKING_DIR_PROPERTY, tempDir);
        System.setProperty(JFAAS_PROXY_EXTERNAL_COMMAND_PROPERTY, "src/test/scripts/command.sh");
        System.setProperty(JFAAS_PROXY_CLIENT_JAR_PROPERTY, "target/test-classes");
    }

    //@Test
    public void testOne() {
        TestBeanLocal testBean = new TestBean();
        int expected = testBean.one();

        TestBeanLocal proxy = JfaasProxy.getProxy(TestBeanLocal.class, TestBean.class);
        int ret = proxy.one();

        assertEquals(expected, ret);
    }

    //@Test
    public void testAddOne() {
        TestBeanLocal testBean = new TestBean();
        int expected = testBean.addOne(3);

        TestBeanLocal proxy = JfaasProxy.getProxy(TestBeanLocal.class, TestBean.class);
        int ret = proxy.addOne(3);

        assertEquals(expected, ret);
    }

    //@Test
    public void testNull() {
        TestBeanLocal testBean = new TestBean();
        String expected = testBean.nullTest();

        TestBeanLocal proxy = JfaasProxy.getProxy(TestBeanLocal.class, TestBean.class);
        String ret = proxy.nullTest();

        assertEquals(expected, ret);
    }

    //@Test
    public void testNothing() {
        TestBeanLocal proxy = JfaasProxy.getProxy(TestBeanLocal.class, TestBean.class);
        proxy.nothing();
    }

    //@Test
    public void testConcat() {
        TestBeanLocal testBean = new TestBean();
        String[] strings = {"hello", " ", "world", "!"};
        String expected = testBean.concat(strings);

        TestBeanLocal proxy = JfaasProxy.getProxy(TestBeanLocal.class, TestBean.class);
        String ret = proxy.concat(strings);

        assertEquals(expected, ret);
    }
}
