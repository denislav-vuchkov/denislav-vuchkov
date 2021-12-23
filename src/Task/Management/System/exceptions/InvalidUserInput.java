package Task.Management.System.exceptions;

public class InvalidUserInput extends RuntimeException {

    public InvalidUserInput() {
    }

    public InvalidUserInput(String message) {
        super(message);
    }
}
