package main.models.tasks.enums;

public enum Size {
    LARGE, MEDIUM, SMALL;

    @Override
    public String toString() {
        switch (this) {
            case LARGE:
                return "Large";
            case MEDIUM:
                return "Medium";
            case SMALL:
                return "Small";
            default:
                throw new IllegalArgumentException("Unreachable exception - Size Enum");
        }
    }
}
