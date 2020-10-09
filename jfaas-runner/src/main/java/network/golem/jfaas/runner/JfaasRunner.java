package network.golem.jfaas.runner;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JfaasRunner {
    public static void main(String[] args) throws Throwable {
        if (args.length != 1) {
            throw new RunnerException("give me one argument, the invocation data file");
        }
        invoke(Paths.get(args[0]));
    }

    public static void invoke(Path invocationFilePath) throws Throwable {
        if (!Files.isRegularFile(invocationFilePath))
            throw new RunnerException("input file does not exist: "+invocationFilePath.toString());
        Object invocationObject;
        try (InputStream fileInputStream = Files.newInputStream(invocationFilePath)) {
            try (ObjectInputStream in = new ObjectInputStream(fileInputStream)) {
                invocationObject = in.readObject();
            }
        }

        if (!(invocationObject instanceof Object[])) throw new RunnerException("input data not an array");
        Object[] invocation = (Object[]) invocationObject;
        if (invocation.length !=4 || !(invocation[0] instanceof String) ||
                !(invocation[1] instanceof String) || !(invocation[2] instanceof String) ||
                !(invocation[3] == null || invocation[3] instanceof Object[])) {
            throw new RunnerException("improper invocation data, should be: intfClassName (String), implClassName (String), methodName (String), args (Object[])");
        }
        String interfaceClassName = (String) invocation[0];
        String implClassName = (String) invocation[1];
        String methodName = (String) invocation[2];
        Object[] args = (Object[]) invocation[3];

        Class<?> interfaceClass = Class.forName(interfaceClassName);
        Class<?> implClass = Class.forName(implClassName);

        Object instance = implClass.newInstance();  //not so pretty ...
        Method method = null;
        for (Method interfaceClassMethod : interfaceClass.getMethods()) {
            if (interfaceClassMethod.getName().equals(methodName)) {
                method = interfaceClassMethod;
                break;
            }
        }
        if (method == null) {
            throw new RunnerException("the method "+methodName+" not found in the interface "+interfaceClassName);
        }

        Object result = method.invoke(instance, args);

        if (method.getReturnType().equals(Void.class))
            return;
        Path resultFilePath = Paths.get(invocationFilePath.getParent().toString(), invocationFilePath.getFileName().toString() + ".return");
        try (OutputStream fileOutputStream = Files.newOutputStream(resultFilePath)) {
            try (ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {
                out.writeObject(result);
            }
        }
    }
}
