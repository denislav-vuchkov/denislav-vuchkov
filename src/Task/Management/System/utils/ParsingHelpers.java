package Task.Management.System.utils;

public class ParsingHelpers {
    public static final String NO_SUCH_ENUM = "There is no %s in %ss.";

    public static double tryParseDouble(String valueToParse, String errorMessage) {
        try {
            return Double.parseDouble(valueToParse);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static int tryParseInt(String valueToParse, String errorMessage) {
        try {
            return Integer.parseInt(valueToParse);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
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
            throw new IllegalArgumentException(String.format(NO_SUCH_ENUM, valueToParse, type.getSimpleName()));
        }
    }

    public static <E extends Enum<E>> E tryParseEnum(String value, Class<E> type) {
        try {
            return Enum.valueOf(type, value.replace(" ", "_").toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format(NO_SUCH_ENUM, value, type.getSimpleName()));
        }
    }
}
