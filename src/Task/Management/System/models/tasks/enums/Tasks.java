package Task.Management.System.models.tasks.enums;

public enum Tasks {
    BUG, STORY, FEEDBACK;

    @Override
    public String toString() {
        switch (this) {
            case BUG:
                return "Bug";
            case STORY:
                return "Story";
            case FEEDBACK:
                return "Feedback";
            default:
                throw new IllegalArgumentException("Unreachable exception - Tasks Enum.");
        }
    }
}
