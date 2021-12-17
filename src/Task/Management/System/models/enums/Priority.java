package Task.Management.System.models.enums;

public enum Priority {
    HIGH, MEDIUM, LOW;


    @Override
    public String toString() {
        switch (this) {
            case HIGH:
                return "High";
            case MEDIUM:
                return "Medium";
            case LOW:
                return "Low";
            default:
                throw new IllegalArgumentException("Unreachable exception: Priority Enum.");
        }
    }
}
