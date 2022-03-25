package main.utils;

import main.exceptions.InvalidNumberOfArguments;
import main.exceptions.InvalidUserInput;
import main.models.tasks.contracts.Task;
import main.models.teams.contracts.subcontracts.Nameable;

import java.util.List;

public class ValidationHelpers {

    public static final String DUPLICATE_NAME = "This %s name already exists! Please choose a unique %s name.";

    public static final String ALREADY_EXISTS = "%s with %d already exists in %s's list.";
    public static final String NOT_EXISTS = "%s with %d does not exist in %s's list.";

    public static final String NOT_IN_TEAM = "%s %s is not in team %s";
    public static final String ALREADY_IN_TEAM = "%s %s is already in team %s";
    public static final String INVALID_FILTER = "Invalid filter parameters passed!";

    private static final String INVALID_NUMBER_OF_ARGUMENTS = "Invalid number of arguments. Expected: %d; received: %d.";

    public static void validateRange(int value, int min, int max, String message) {
        if (value < min || value > max) {
            throw new InvalidUserInput(message);
        }
    }

    public static void validateCount(List<String> list, int expectedNumberOfParameters) {
        if (list.size() != expectedNumberOfParameters) {
            throw new InvalidNumberOfArguments(
                    String.format(INVALID_NUMBER_OF_ARGUMENTS, expectedNumberOfParameters, list.size()));
        }
    }

    public static void validateFilterParameters(List<String> parameters) {
        for (String parameter : parameters){
            if (parameter.split(":").length !=2){
                throw new InvalidUserInput(INVALID_FILTER);
            }
        }
    }

    public static <T extends Nameable> void entryNotAlreadyInList(T entry, List<T> list, String objectName) {
        String message = String.format(ALREADY_IN_TEAM, FormatHelpers.getType(entry), entry.getName(), objectName);
        checkIfEntryNotAlreadyInList(entry, list, message);
    }

    public static <T extends Task> void entryNotAlreadyInList(T task, List<T> taskList, String objectName) {
        String message = String.format(ALREADY_EXISTS, FormatHelpers.getType(task), task.getID(), objectName);
        checkIfEntryNotAlreadyInList(task, taskList, message);
    }

    public static <T> void checkIfEntryNotAlreadyInList(T element, List<T> list, String message) {
        if (list.contains(element)) {
            throw new InvalidUserInput(message);
        }
    }

    public static <T extends Nameable> void entryExistInList(T entry, List<T> list, String objectName) {
        String message = String.format(NOT_IN_TEAM, FormatHelpers.getType(entry), entry.getName(), objectName);
        checkIfEntryExists(entry, list, message);
    }

    public static <T extends Task> void entryExistInList(T task, List<T> taskList, String objectName) {
        String message = String.format(NOT_EXISTS, FormatHelpers.getType(task), task.getID(), objectName);
        checkIfEntryExists(task, taskList, message);
    }

    public static <T> void checkIfEntryExists(T element, List<T> list, String message) {
        if (!list.contains(element)) {
            throw new InvalidUserInput(message);

        }
    }

    public static <T extends Nameable> void validateUniqueName(String name, List<T> elements, String type) {
        if (elements.stream().anyMatch(element -> element.getName().equals(name))) {
            throw new InvalidUserInput(String.format(DUPLICATE_NAME, type, type));
        }
    }
}
