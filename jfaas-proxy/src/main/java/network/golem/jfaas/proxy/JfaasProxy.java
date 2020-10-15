package network.golem.jfaas.proxy;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class JfaasProxy implements InvocationHandler {
    public static final String JFAAS_PROXY_WORKING_DIR_PROPERTY = "jfaas.proxy.working.dir";
    public static final String JFAAS_PROXY_EXTERNAL_COMMAND_PROPERTY = "jfaas.proxy.command";
    public static final String JFAAS_PROXY_CLIENT_JAR_PROPERTY = "jfaas.proxy.client.jar";

    private Class<?> interfaceClass;
    private Class<?> implClass;

    private JfaasProxy(Class<?> interfaceClass, Class<?> implClass) {
        this.interfaceClass = interfaceClass;
        this.implClass = implClass;
    }

    public static <I, T extends I> I getProxy(Class<I> interfaceClass, Class<T> implClass) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        JfaasProxy jfaasProxy = new JfaasProxy(interfaceClass, implClass);
        return (I) Proxy.newProxyInstance(classLoader, new Class[]{interfaceClass}, jfaasProxy);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String invocationId = Long.toString(Math.abs(new Random().nextLong()));
        String directory = System.getProperty(JFAAS_PROXY_WORKING_DIR_PROPERTY) != null ?
                System.getProperty(JFAAS_PROXY_WORKING_DIR_PROPERTY) : System.getProperty("java.io.tmpdir");
        String command = System.getProperty(JFAAS_PROXY_EXTERNAL_COMMAND_PROPERTY);
        String clientJar = System.getProperty(JFAAS_PROXY_CLIENT_JAR_PROPERTY);

        Object[] invocation = {interfaceClass.getName(), implClass.getName(), method.getName(), args};
        String invocationFileName = invocationId +".invocation";
        Path invocationFilePath = Paths.get(directory, invocationFileName);
        try (OutputStream fileOutputStream = Files.newOutputStream(invocationFilePath)) {
            try (ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {
                out.writeObject(invocation);
            }
        }

        ProcessBuilder processBuilder = new ProcessBuilder(new String[]{command, clientJar, invocationFilePath.toAbsolutePath().toString(),});
        Process process = processBuilder.start();
        new Thread(() -> {
            BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            try {
                while ((line = processOutputReader.readLine()) != null)
                    System.out.println(line);
            } catch (Exception e) {
                e.printStackTrace();     //not so pretty
            }
        }).start();
        new Thread(() -> {
            BufferedReader processErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            try {
                while ((line = processErrorReader.readLine()) != null)
                    System.out.println(line);
            } catch (Exception e) {
                e.printStackTrace();     //not so pretty
            }
        }).start();
        int commandStatus = process.waitFor();
        if (commandStatus != 0) {
            throw new CommandException("the command executed in the separate process returned with non-zero code: "+commandStatus);
        }

        String resultFileName = invocationId +".invocation.return";
        Path resultFilePath = Paths.get(directory, resultFileName);
        if (!Files.isRegularFile(resultFilePath)) {
            throw new CommandException("return file is missing: "+resultFileName+", in the directory: "+directory);
        }
        try (InputStream fileInputStream = Files.newInputStream(resultFilePath)) {
            try (ObjectInputStream in = new ObjectInputStream(fileInputStream)) {
                Object result = in.readObject();
                if (result instanceof Throwable)
                    throw (Throwable) result;
                return result;
            }
        }
    }

}
