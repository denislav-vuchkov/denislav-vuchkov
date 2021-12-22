package Task.Management.System.models;

import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Severity;
import Task.Management.System.models.tasks.enums.Size;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.models.tasks.BugImpl.*;
import static Task.Management.System.models.tasks.FeedbackImpl.*;
import static Task.Management.System.models.teams.contracts.Nameable.*;

public class TestData {

    public static class TaskBase {
        public static final String VALID_TITLE = "x".repeat(TITLE_MIN_LENGTH);
        public static final String VALID_DESCRIPTION = "x".repeat(DESCRIPTION_MAX_LENGTH);
    }

    public static class AssignableTask {
        public static final Priority VALID_PRIORITY = Priority.HIGH;
        public static final String VALID_ASSIGNEE = "Peter";
    }

    public static class BugImpl {
        public static final List<String> STEPS_TO_REPRODUCE = new ArrayList<>();

        public BugImpl() {
            STEPS_TO_REPRODUCE.add("Open browser.");
            STEPS_TO_REPRODUCE.add("Open website.");
            STEPS_TO_REPRODUCE.add("Click \"About us\".");
        }

        public static final Severity VALID_SEVERITY = Severity.MAJOR;
    }

    public static class StoryImpl {
        public static final Size VALID_SIZE = Size.SMALL;
    }

    public static class FeedbackImpl {
        public static final int VALID_RATING = RATING_MIN;
    }

    public static class UserImpl {
        public static final String VALID_USER_NAME = "x".repeat(NAME_MIN_LENGTH);
    }

    public static class BoardImpl {
        public static final String VALID_BOARD_NAME = "x".repeat(NAME_MAX_LENGTH);
    }

    public static class TeamImpl {
        public static final String VALID_TEAM_NAME = "x".repeat(NAME_MAX_LENGTH);
    }

}
