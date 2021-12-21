package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.FeedbackImpl;
import Task.Management.System.models.tasks.contracts.Feedback;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class CreateNewFeedbackInTeamBoardCommand extends BaseCommand {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String FEEDBACK_ADDED_TO_BOARD =
            "Feedback with ID %d successfully added to board %s in team %s.";

    public CreateNewFeedbackInTeamBoardCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = getRepository().findTeam(parameters.get(0));

        Board board = team
                .getBoards()
                .stream()
                .filter(b -> b.getName().equals(parameters.get(1)))
                .findAny().orElseThrow();

        //TODO
        Feedback feedback = new FeedbackImpl(
                1,
                "title",
                "description",
                5);

        board.addTask(feedback);
        return String.format(FEEDBACK_ADDED_TO_BOARD, feedback.getID(), board.getName(), team.getName());
    }
}
