package Task.Management.System.models.tasks.enums;

public enum BugStatus {
    ACTIVE, FIXED;

    @Override
    public String toString() {
        switch (this) {
            case ACTIVE:
                return "Active";
            case FIXED:
                return "Fixed";
            default:
                throw new IllegalArgumentException("Unreachable exception: Bug Status Enum.");
        }
    }
}
