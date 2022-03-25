package main.commands.show;

import main.commands.BaseCommand;
import main.core.contracts.TaskManagementSystemRepository;
import main.models.teams.contracts.Board;
import main.models.teams.contracts.Team;
import main.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class ShowTeamBoards extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ShowTeamBoards(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = getRepository().findTeam(parameters.get(0));
        if (team.getBoards().isEmpty()) {
            return String.format(NO_ITEMS_TO_DISPLAY, "boards");
        }

        return team.getBoards()
                .stream()
                .map(Board::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
