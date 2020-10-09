package network.golem.jfaas.proxy;

public class CommandException extends RuntimeException {
    public CommandException(int i) {
        super("command failed, status: "+Integer.toString(i));
    }

    public CommandException(String message) {
        super(message);
    }
}
