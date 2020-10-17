package network.golem.jfaas.runner;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ObjectTest {
    @Test
    public void objectTest() throws Throwable {
        String invocationId = Long.toString(Math.abs(new Random().nextLong()));
        String tempDir = System.getProperty("java.io.tmpdir");

        ObjectTestArgument argument = new ObjectTestArgument("object", "test");
        Object[] invocation = {null, "network.golem.jfaas.runner.ObjectTestBean", "convert", new Object[]{argument}};

        Path invocationFilePath = Paths.get(tempDir, invocationId + ".invocation");
        try (OutputStream fileOutputStream = Files.newOutputStream(invocationFilePath)) {
            try (ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {
                out.writeObject(invocation);
            }
        }

        JfaasRunner.invoke(invocationFilePath);

        Path returnFilePath = Paths.get(tempDir, invocationId + ".invocation.return");
        assertTrue(Files.isRegularFile(returnFilePath));
    }

    @Test
    public void objectTestException() throws Throwable {
        String invocationId = Long.toString(Math.abs(new Random().nextLong()));
        String tempDir = System.getProperty("java.io.tmpdir");

        ObjectTestArgument argument = new ObjectTestArgument("object", "test");
        Object[] invocation = {null, "network.golem.jfaas.runner.ObjectTestBean", "convertFail", new Object[]{argument}};

        Path invocationFilePath = Paths.get(tempDir, invocationId + ".invocation");
        try (OutputStream fileOutputStream = Files.newOutputStream(invocationFilePath)) {
            try (ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {
                out.writeObject(invocation);
            }
        }

        JfaasRunner.invoke(invocationFilePath);

        Path returnFilePath = Paths.get(tempDir, invocationId + ".invocation.return");
        assertTrue(Files.isRegularFile(returnFilePath));
    }

    @Test
    public void objectTestNull() throws Throwable {
        String invocationId = Long.toString(Math.abs(new Random().nextLong()));
        String tempDir = System.getProperty("java.io.tmpdir");

        ObjectTestArgument argument = new ObjectTestArgument("object", "test");
        Object[] invocation = {null, "network.golem.jfaas.runner.ObjectTestBean", "convertToNull", new Object[]{argument}};

        Path invocationFilePath = Paths.get(tempDir, invocationId + ".invocation");
        try (OutputStream fileOutputStream = Files.newOutputStream(invocationFilePath)) {
            try (ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {
                out.writeObject(invocation);
            }
        }

        JfaasRunner.invoke(invocationFilePath);

        Path returnFilePath = Paths.get(tempDir, invocationId + ".invocation.return");
        assertTrue(Files.isRegularFile(returnFilePath));
    }

    @Test
    public void voidTest() throws Throwable {
        String invocationId = Long.toString(Math.abs(new Random().nextLong()));
        String tempDir = System.getProperty("java.io.tmpdir");

        Object argument = "notNUll";
        Object[] invocation = {null, "network.golem.jfaas.runner.ObjectTestBean", "nullValidate", new Object[]{argument}};

        Path invocationFilePath = Paths.get(tempDir, invocationId + ".invocation");
        try (OutputStream fileOutputStream = Files.newOutputStream(invocationFilePath)) {
            try (ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {
                out.writeObject(invocation);
            }
        }

        JfaasRunner.invoke(invocationFilePath);

        Path returnFilePath = Paths.get(tempDir, invocationId + ".invocation.return");
        assertTrue(Files.exists(returnFilePath) && Files.isRegularFile(returnFilePath));
    }
}
