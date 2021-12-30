package Task.Management.System.utils;

import Task.Management.System.exceptions.InvalidUserInput;

public class ParsingHelpers {

    public static final String NO_SUCH_ENUM = "%s is not a valid %s type.";

    public static int tryParseInt(String valueToParse, String errorMessage) {
        try {
            return Integer.parseInt(valueToParse);
        } catch (NumberFormatException e) {
            throw new InvalidUserInput(errorMessage);
        }
    }

    public static long tryParseLong(String valueToParse, String errorMessage) {
        try {
            return Long.parseLong(valueToParse);
        } catch (NumberFormatException e) {
            throw new InvalidUserInput(errorMessage);
        }
    }

    public static <E extends Enum<E>> E tryParseCommand(String valueToParse, Class<E> type) {
        try {
            StringBuilder command = new StringBuilder();
            for (Character ch : valueToParse.toCharArray()) {
                if (String.valueOf(ch).matches("[A-Z]")) {
                    command.append("_");
                }
                command.append(ch);
            }
            return Enum.valueOf(type, command.substring(1).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidUserInput(String.format(NO_SUCH_ENUM, valueToParse, type.getSimpleName()));
        }
    }

    public static <E extends Enum<E>> E tryParseEnum(String value, Class<E> type) {
        try {
            return Enum.valueOf(type, value.replace(" ", "_").toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidUserInput(String.format(NO_SUCH_ENUM, value, type.getSimpleName()));
        }
    }
}
