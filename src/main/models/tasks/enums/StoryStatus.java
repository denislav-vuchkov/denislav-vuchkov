package main.models.tasks.enums;

import main.models.tasks.contracts.TaskStatus;

public enum StoryStatus implements TaskStatus {
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
