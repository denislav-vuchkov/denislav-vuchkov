package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class ShowAllTeamBoardsCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String NO_BOARDS_HEADER = "--NO BOARDS IN TEAM %s--";
    public static final String BOARDS_HEADER = "--BOARDS IN TEAM: %s";

    public ShowAllTeamBoardsCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        Team team = getRepository().findTeam(parameters.get(0));

        StringBuilder result = new StringBuilder();

        if (team.getBoards().isEmpty()) {
            result.append(String.format(NO_BOARDS_HEADER, parameters.get(0)));
        } else {
            result.append(String.format(BOARDS_HEADER, parameters.get(0))).append("\n");
            team.getBoards().forEach(e -> result.append(e.toString()).append("\n"));
            result.append(String.format(BOARDS_HEADER, parameters.get(0)));
        }

        return result.toString();
    }
}
