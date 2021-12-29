package Task.Management.System.utils;

public class FormatHelpers {

    public static String getType(Object object) {
        return object.getClass().getSimpleName().replace("Impl", "");
    }
}
