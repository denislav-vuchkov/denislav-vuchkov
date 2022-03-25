package main.models.tasks.enums;

import main.models.tasks.contracts.TaskStatus;

public enum BugStatus implements TaskStatus {
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
