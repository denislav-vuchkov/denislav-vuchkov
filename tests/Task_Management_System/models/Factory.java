package Task_Management_System.models;

import Task_Management_System.models.tasks.BugImpl;
import Task_Management_System.models.tasks.FeedbackImpl;
import Task_Management_System.models.tasks.StoryImpl;
import Task_Management_System.models.tasks.contracts.Bug;
import Task_Management_System.models.tasks.contracts.Feedback;
import Task_Management_System.models.tasks.contracts.Story;
import Task_Management_System.models.teams.BoardImpl;
import Task_Management_System.models.teams.TeamImpl;
import Task_Management_System.models.teams.UserImpl;
import Task_Management_System.models.teams.contracts.Board;
import Task_Management_System.models.teams.contracts.Team;
import Task_Management_System.models.teams.contracts.User;

import static Task_Management_System.models.TestData.AssignableTask.VALID_PRIORITY;
import static Task_Management_System.models.TestData.BoardImpl.VALID_BOARD_NAME;
import static Task_Management_System.models.TestData.BugImpl.VALID_SEVERITY;
import static Task_Management_System.models.TestData.BugImpl.STEPS_TO_REPRODUCE;
import static Task_Management_System.models.TestData.FeedbackImpl.VALID_RATING;
import static Task_Management_System.models.TestData.StoryImpl.VALID_SIZE;
import static Task_Management_System.models.TestData.TaskBase.VALID_DESCRIPTION;
import static Task_Management_System.models.TestData.TaskBase.VALID_TITLE;
import static Task_Management_System.models.TestData.TeamImpl.VALID_TEAM_NAME;
import static Task_Management_System.models.TestData.UserImpl.VALID_USER_NAME;

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
