package Task.Management.System.commands.show;

import Task.Management.System.commands.contracts.Command;
import Task.Management.System.core.CommandFactoryImpl;
import Task.Management.System.core.TaskManagementSystemRepositoryImpl;
import Task.Management.System.core.contracts.CommandFactory;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidNumberOfArguments;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.commands.BaseCommand.NO_ITEMS_TO_DISPLAY;
import static Task.Management.System.models.TestData.UserImpl.VALID_USER_NAME;

public class ShowAllUsers_Tests {

    private CommandFactory commandFactory;
    private TaskManagementSystemRepository repository;
    private Command command;

    @BeforeEach
    public void setup() {
        commandFactory = new CommandFactoryImpl();
        repository = new TaskManagementSystemRepositoryImpl();
        command = commandFactory.createCommandFromCommandName("ShowAllUsers", repository);
    }

    @Test
    public void showAllUsers_Should_throwException_WhenValidArguments() {
        List<String> parameters = new ArrayList<>();
        parameters.add("Unnecessary parameter");

        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> command.execute(parameters));
    }

    @Test
    public void showAllUsers_Should_Indicate_When_NoUsersToDisplay() {
        Assertions.assertEquals(String.format(NO_ITEMS_TO_DISPLAY, "users"), command.execute(List.of()));
    }

    @Test
    public void showAllUsers_Should_Execute_When_ValidInput() {
        Command createUser = commandFactory.createCommandFromCommandName("CreateUser", repository);
        List<String> parameters = new ArrayList<>();
        parameters.add(VALID_USER_NAME);
        createUser.execute(parameters);

        String output = String.format("User: %s - Tasks: %d", VALID_USER_NAME, 0);

        Assertions.assertEquals(output, command.execute(List.of()));
    }

}
