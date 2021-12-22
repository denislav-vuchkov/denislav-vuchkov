package Task.Management.System.models.exceptions;

public class InvalidUserInput extends RuntimeException {

    public InvalidUserInput() {
    }

    public InvalidUserInput(String message) {
        super(message);
    }
}
