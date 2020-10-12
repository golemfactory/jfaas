# jfaas-proxy

The library required by the caller java app. 

## Usage

Within the java app select a bean. Calls to that bean will be delegated. 
The bean must be plain - the call is serialized but the bean instance is not.
Old style without annotations was adopted for the start. The bean must have an interface.
Usage is simple. First get a proxy.
```
BeanInterface proxy = JfaasProxy.getProxy(BeanInterface.class, BeanClass.class);
```
Now every call to the proxy object is delegated.

## Configuration

The library `jfaas-proxy.jar` should be added to the java app classpath.

There are three configurations enabled through java system properties.
1. `jfaas.proxy.command` - an external command for the remote execution. It takes two parameters: a path to a client jar and a name of a file containing serialized call.
2. `jfaas.proxy.client.jar` - a jar conaining classes of bean and interface to be called. It can be different than the caller jar.
3. `jfaas.proxy.working.dir` - (optional) a working directory for serialized calls and return values. By default it is the system temp dir.

## Calls

All delegated calls are serialized with java built-in mechanism. The call is save to the file `${callId}.invocation` where `callId` is a random number.
The return value is expected in the file `${callId}.invocation.return`. 

At the moment an execution of the external command is synchronuous - when the command ends, then a return file is expected.

## Beans

A target bean must be POJO. All method parameters and return values must be Serializable. A target bean instance is created with an empty constructor on the remote side.
