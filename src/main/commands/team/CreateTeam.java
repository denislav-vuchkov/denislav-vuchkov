package main.commands.team;

import main.commands.BaseCommand;
import main.core.contracts.TaskManagementSystemRepository;
import main.utils.ValidationHelpers;

import java.util.List;

public class CreateTeam extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public CreateTeam(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String teamName = parameters.get(0);
        ValidationHelpers.validateUniqueName(teamName, getRepository().getTeams(), TEAM);
        return getRepository().addTeam(teamName);
    }
}
