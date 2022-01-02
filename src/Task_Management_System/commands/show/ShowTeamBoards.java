package Task_Management_System.commands.show;

import Task_Management_System.commands.BaseCommand;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.models.teams.contracts.Board;
import Task_Management_System.models.teams.contracts.Team;
import Task_Management_System.utils.ValidationHelpers;

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
