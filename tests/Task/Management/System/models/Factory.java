package Task.Management.System.models;

import Task.Management.System.models.tasks.BugImpl;
import Task.Management.System.models.tasks.FeedbackImpl;
import Task.Management.System.models.tasks.StoryImpl;
import Task.Management.System.models.tasks.contracts.Bug;
import Task.Management.System.models.tasks.contracts.Feedback;
import Task.Management.System.models.tasks.contracts.Story;
import Task.Management.System.models.teams.BoardImpl;
import Task.Management.System.models.teams.TeamImpl;
import Task.Management.System.models.teams.UserImpl;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;

import static Task.Management.System.models.TestData.AssignableTask.VALID_ASSIGNEE;
import static Task.Management.System.models.TestData.AssignableTask.VALID_PRIORITY;
import static Task.Management.System.models.TestData.BoardImpl.VALID_BOARD_NAME;
import static Task.Management.System.models.TestData.BugImpl.VALID_SEVERITY;
import static Task.Management.System.models.TestData.BugImpl.STEPS_TO_REPRODUCE;
import static Task.Management.System.models.TestData.FeedbackImpl.VALID_RATING;
import static Task.Management.System.models.TestData.StoryImpl.VALID_SIZE;
import static Task.Management.System.models.TestData.TaskBase.VALID_DESCRIPTION;
import static Task.Management.System.models.TestData.TaskBase.VALID_TITLE;
import static Task.Management.System.models.TestData.TeamImpl.VALID_TEAM_NAME;
import static Task.Management.System.models.TestData.UserImpl.VALID_USER_NAME;

public class Factory {

    private static int nextID = 0;

    public static Bug createBug() {
        return new BugImpl(++nextID, VALID_TITLE, VALID_DESCRIPTION, STEPS_TO_REPRODUCE,
                VALID_PRIORITY, VALID_SEVERITY);
    }

    public static Story createStory() {
        return new StoryImpl(++nextID, VALID_TITLE, VALID_DESCRIPTION, VALID_PRIORITY, VALID_SIZE);
    }

    public static Feedback createFeedback() {
        return new FeedbackImpl(++nextID, VALID_TITLE, VALID_DESCRIPTION, VALID_RATING);
    }

    public static User createUser() {
        return new UserImpl(VALID_USER_NAME);
    }

    public static Board createBoard() {
        return new BoardImpl(VALID_BOARD_NAME);
    }

    public static Team createTeam() {
        return new TeamImpl(VALID_TEAM_NAME);
    }


}
