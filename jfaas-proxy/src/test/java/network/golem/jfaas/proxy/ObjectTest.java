package network.golem.jfaas.proxy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static network.golem.jfaas.proxy.JfaasProxy.*;
import static network.golem.jfaas.proxy.JfaasProxy.getProxy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectTest {
    @Test
    public void emptyTest() throws IOException {
        String tempDir = System.getProperty("java.io.tmpdir");
        System.setProperty(JFAAS_PROXY_WORKING_DIR_PROPERTY, tempDir);
        System.setProperty(JFAAS_PROXY_EXTERNAL_COMMAND_PROPERTY, "src/test/scripts/objectTestCommand.sh");
        System.setProperty(JFAAS_PROXY_CLIENT_JAR_PROPERTY, "target/test-classes");

        ObjectTestBeanLocal objectTestBean = new ObjectTestBean();
        ObjectTestArgument argument = new ObjectTestArgument("object", "test");
        ObjectTestReturn retval1 = objectTestBean.convert(argument);

        Path resultFilePath = Paths.get("objectTest.invocation.return");
        try (OutputStream fileOutputStream = Files.newOutputStream(resultFilePath)) {
            try (ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {
                out.writeObject(retval1);
            }
        }

        ObjectTestBeanLocal proxy = getProxy(ObjectTestBeanLocal.class, ObjectTestBean.class);
        ObjectTestReturn retval2 = proxy.convert(argument);

        assertEquals(retval1, retval2);
    }

}
