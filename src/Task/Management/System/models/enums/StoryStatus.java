package Task.Management.System.models.enums;

public enum StoryStatus {
    NOT_DONE, IN_PROGRESS, DONE;

    @Override
    public String toString() {
        switch (this) {
            case NOT_DONE:
                return "Not done";
            case IN_PROGRESS:
                return "In progress";
            case DONE:
                return "Done";
            default:
                throw new IllegalArgumentException("Unreachable exception: Story Status Enum.");
        }
    }
}
