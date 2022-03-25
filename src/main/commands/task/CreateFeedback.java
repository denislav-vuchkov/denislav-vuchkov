package main.commands.task;

import main.commands.BaseCommand;
import main.core.contracts.TaskManagementSystemRepository;
import main.models.tasks.FeedbackImpl;
import main.models.teams.contracts.Board;
import main.models.teams.contracts.Team;
import main.models.teams.contracts.User;
import main.utils.ParsingHelpers;
import main.utils.ValidationHelpers;

import java.util.List;

public class CreateFeedback extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 6;

    public CreateFeedback(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        User creator = getRepository().findUser(parameters.get(0));
        Team team = getRepository().findTeam(parameters.get(1));
        getRepository().validateUserIsFromTeam(creator.getName(), team.getName());
        Board board = getRepository().findBoard(parameters.get(2), team.getName());
        String title = parameters.get(3);
        String description = parameters.get(4);
        int rating = ParsingHelpers.tryParseInt(parameters.get(5), RATING_ERR);
        ValidationHelpers.validateRange(rating, FeedbackImpl.RATING_MIN, FeedbackImpl.RATING_MAX, RATING_ERR);
        return getRepository().addFeedback(creator, team, board, title, description, rating);
    }
}
