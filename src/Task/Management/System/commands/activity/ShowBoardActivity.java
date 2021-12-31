package Task.Management.System.commands.activity;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.logger.EventImpl;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class ShowBoardActivity extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    public ShowBoardActivity(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = getRepository().findByName(getRepository().getTeams(), parameters.get(0), TEAM);
        String boardName = parameters.get(1);
        return getRepository()
                .findByName(team.getBoards(), boardName, BOARD)
                .getLog()
                .stream()
                .map(EventImpl::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
