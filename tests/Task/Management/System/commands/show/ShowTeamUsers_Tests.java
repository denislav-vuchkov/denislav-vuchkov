package Task.Management.System.commands.show;

import Task.Management.System.commands.contracts.Command;
import Task.Management.System.core.CommandFactoryImpl;
import Task.Management.System.core.TaskManagementSystemRepositoryImpl;
import Task.Management.System.core.contracts.CommandFactory;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidNumberOfArguments;
import Task.Management.System.models.teams.contracts.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.commands.BaseCommand.NO_ITEMS_TO_DISPLAY;
import static Task.Management.System.commands.BaseCommand.USER;
import static Task.Management.System.commands.show.ShowTeamUsers.EXPECTED_NUMBER_OF_ARGUMENTS;
import static Task.Management.System.models.TestData.BoardImpl.VALID_BOARD_NAME;
import static Task.Management.System.models.TestData.TeamImpl.VALID_TEAM_NAME;
import static Task.Management.System.models.TestData.UserImpl.VALID_USER_NAME;

public class ShowTeamUsers_Tests {

    CommandFactory commandFactory;
    TaskManagementSystemRepository repository;
    Command command;

    @BeforeEach
    public void setup() {
        commandFactory = new CommandFactoryImpl();
        repository = new TaskManagementSystemRepositoryImpl();
        command = commandFactory.createCommand("ShowTeamUsers", repository);

    }

    @ParameterizedTest(name = "with length {0}")
    @ValueSource(ints = {EXPECTED_NUMBER_OF_ARGUMENTS-1, EXPECTED_NUMBER_OF_ARGUMENTS+1})
    public void showTeamUsers_Should_throwException_WhenValidArguments(int parametersCount) {
        List<String> parameters = new ArrayList<>();

        for (int i = 1; i <= parametersCount; i++) {
            parameters.add("1");
        }

        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> command.execute(parameters));
    }

    @Test
    public void showTeamUsers_Should_Indicate_When_NoCommentsToDisplay() {
        Command createTeam = commandFactory.createCommand("CreateTeam", repository);
        createTeam.execute(List.of(VALID_TEAM_NAME));

        Assertions.assertEquals(String.format(NO_ITEMS_TO_DISPLAY, "users"), command.execute(List.of(VALID_TEAM_NAME)));
    }

    @Test
    public void showTeamUsers_Should_Execute_When_ValidInput() {
        Command createUser = commandFactory.createCommand("CreateUser", repository);
        createUser.execute(List.of(VALID_USER_NAME));

        Command createTeam = commandFactory.createCommand("CreateTeam", repository);
        createTeam.execute(List.of(VALID_TEAM_NAME));

        Command addUserToTeam = commandFactory.createCommand("AddUserToTeam", repository);
        addUserToTeam.execute(List.of(VALID_USER_NAME, VALID_TEAM_NAME));

        User user = repository.findByName(repository.getUsers(), VALID_USER_NAME, USER);

        String output = String.format("User: %s - Tasks: %d",
                user.getName(),
                user.getTasks().size());

        Assertions.assertEquals(output, command.execute(List.of(VALID_BOARD_NAME)));
    }

}
