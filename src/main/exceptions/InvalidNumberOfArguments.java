package main.exceptions;

public class InvalidNumberOfArguments extends RuntimeException {

    public InvalidNumberOfArguments() {
    }

    public InvalidNumberOfArguments(String message) {
        super(message);
    }

}
