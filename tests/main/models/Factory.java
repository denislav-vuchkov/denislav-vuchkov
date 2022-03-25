package main.models;

import main.models.tasks.BugImpl;
import main.models.tasks.FeedbackImpl;
import main.models.tasks.StoryImpl;
import main.models.tasks.contracts.Bug;
import main.models.tasks.contracts.Feedback;
import main.models.tasks.contracts.Story;
import main.models.teams.BoardImpl;
import main.models.teams.TeamImpl;
import main.models.teams.UserImpl;
import main.models.teams.contracts.Board;
import main.models.teams.contracts.Team;
import main.models.teams.contracts.User;

import static main.models.TestData.AssignableTask.VALID_PRIORITY;
import static main.models.TestData.BoardImpl.VALID_BOARD_NAME;
import static main.models.TestData.BugImpl.VALID_SEVERITY;
import static main.models.TestData.BugImpl.STEPS_TO_REPRODUCE;
import static main.models.TestData.FeedbackImpl.VALID_RATING;
import static main.models.TestData.StoryImpl.VALID_SIZE;
import static main.models.TestData.TaskBase.VALID_DESCRIPTION;
import static main.models.TestData.TaskBase.VALID_TITLE;
import static main.models.TestData.TeamImpl.VALID_TEAM_NAME;
import static main.models.TestData.UserImpl.VALID_USER_NAME;

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
