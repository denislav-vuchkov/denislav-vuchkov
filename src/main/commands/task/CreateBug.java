package main.commands.task;

import main.commands.BaseCommand;
import main.core.contracts.TaskManagementSystemRepository;
import main.models.tasks.enums.Priority;
import main.models.tasks.enums.Severity;
import main.models.teams.contracts.Board;
import main.models.teams.contracts.Team;
import main.models.teams.contracts.User;
import main.utils.ParsingHelpers;
import main.utils.ValidationHelpers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateBug extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 9;

    public CreateBug(TaskManagementSystemRepository repository) {
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
        List<String> steps = Arrays
                .stream(parameters.get(5).split(";"))
                .map(String::trim)
                .collect(Collectors.toList());
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(6), Priority.class);
        Severity severity = ParsingHelpers.tryParseEnum(parameters.get(7), Severity.class);
        String assignee = parameters.get(8);
        if (!assignee.isBlank()) getRepository().validateUserIsFromTeam(assignee, team.getName());
        return getRepository().addBug(creator, team, board, title, description, steps, priority, severity, assignee);
    }
}
