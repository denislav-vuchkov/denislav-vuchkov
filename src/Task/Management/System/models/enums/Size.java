package Task.Management.System.models.enums;

public enum Size {
    SMALL, MEDIUM, LARGE;

    @Override
    public String toString() {
        switch (this) {
            case SMALL:
                return "Small";
            case MEDIUM:
                return "Medium";
            case LARGE:
                return "Large";
            default:
                throw new IllegalArgumentException("Unreachable exception - Size Enum");
        }
    }
}
