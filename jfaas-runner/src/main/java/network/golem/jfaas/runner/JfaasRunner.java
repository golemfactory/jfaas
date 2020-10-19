package network.golem.jfaas.runner;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JfaasRunner {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new RunnerException("give me one argument, the invocation data file");
        }
        invoke(Paths.get(args[0]));
    }

    public static void invoke(Path invocationFilePath) throws IOException {
        try {
            invokeInt(invocationFilePath);
        } catch (Throwable throwable) {
            RunnerExecutionException runnerExecutionException = new RunnerExecutionException(throwable);
            Path resultFilePath = Paths.get(invocationFilePath.getParent().toString(), invocationFilePath.getFileName().toString() + ".return");
            try (OutputStream fileOutputStream = Files.newOutputStream(resultFilePath)) {
                try (ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {
                    out.writeObject(resultFilePath);
                }
            }

        }
    }

    public static void invokeInt(Path invocationFilePath) throws Throwable {
        if (!(Files.exists(invocationFilePath) && Files.isRegularFile(invocationFilePath)))
            throw new RunnerException("input file does not exist: "+invocationFilePath.toString());
        Object invocationObject;
        try (InputStream fileInputStream = Files.newInputStream(invocationFilePath)) {
            try (ObjectInputStream in = new ObjectInputStream(fileInputStream)) {
                invocationObject = in.readObject();
            }
        }

        if (!(invocationObject instanceof Object[])) throw new RunnerException("input data not an array");
        Object[] invocation = (Object[]) invocationObject;
        if (invocation.length !=4 ||
                !(invocation[1] instanceof String) || !(invocation[2] instanceof String) ||
                !(invocation[3] == null || invocation[3] instanceof Object[])) {
            throw new RunnerException("improper invocation data, should be: intfClassName (String), implClassName (String), methodName (String), args (Object[])");
        }
        Object target = invocation[0];
        String targetClassName = (String) invocation[1];
        String methodName = (String) invocation[2];
        Object[] args = (Object[]) invocation[3];

        Class<?> targetClass = Class.forName(targetClassName);

        Method method = null;
        for (Method interfaceClassMethod : targetClass.getMethods()) {
            if (interfaceClassMethod.getName().equals(methodName)) {
                method = interfaceClassMethod;
                break;
            }
        }
        if (method == null) {
            throw new RunnerException("the method "+methodName+" not found in the class "+targetClassName);
        }

        if (target == null) {
            target = targetClass.newInstance();  //not so pretty ...
	}
        Object result;
        try {
            result = method.invoke(target, args);
        } catch (InvocationTargetException ite) {
            result = ite.getCause();
        }

        Path resultFilePath = Paths.get(invocationFilePath.getParent().toString(), invocationFilePath.getFileName().toString() + ".return");
        try (OutputStream fileOutputStream = Files.newOutputStream(resultFilePath)) {
            try (ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {
                out.writeObject(result);
            }
        }
    }
}
