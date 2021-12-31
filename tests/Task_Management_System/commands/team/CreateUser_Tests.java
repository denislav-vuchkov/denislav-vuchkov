package Task_Management_System.commands.team;

import Task_Management_System.commands.contracts.Command;
import Task_Management_System.core.CommandFactoryImpl;
import Task_Management_System.core.TaskManagementSystemRepositoryImpl;
import Task_Management_System.core.contracts.CommandFactory;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.exceptions.InvalidNumberOfArguments;
import Task_Management_System.exceptions.InvalidUserInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static Task_Management_System.commands.team.CreateUser.EXPECTED_NUMBER_OF_ARGUMENTS;
import static Task_Management_System.models.TestData.UserImpl.VALID_USER_NAME;
import static Task_Management_System.models.teams.contracts.subcontracts.Nameable.NAME_MAX_LEN;
import static Task_Management_System.models.teams.contracts.subcontracts.Nameable.NAME_MIN_LEN;

public class CreateUser_Tests {

    CommandFactory commandFactory;
    TaskManagementSystemRepository repository;
    Command createUser;

    @BeforeEach
    public void setup() {
        commandFactory = new CommandFactoryImpl();
        repository = new TaskManagementSystemRepositoryImpl();
        createUser = commandFactory.createCommand("CreateUser", repository);
    }

    @ParameterizedTest(name = "with length {0}")
    @ValueSource(ints = {EXPECTED_NUMBER_OF_ARGUMENTS-1, EXPECTED_NUMBER_OF_ARGUMENTS+1})
    public void createUser_Should_throwException_WhenValidArgumentsCounts(int parametersCount) {
        List<String> parameters = new ArrayList<>();

        for (int i = 1; i <= parametersCount; i++) {
            parameters.add("1");
        }

        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> createUser.execute(parameters));
        Assertions.assertEquals(0, repository.getUsers().size());
    }

    @ParameterizedTest(name = "with length {0}")
    @ValueSource(ints = {NAME_MIN_LEN -1, NAME_MAX_LEN +1})
    public void createUser_Should_throwException_whenInvalidUserName(int nameLength) {
        List<String> parameters = List.of("x".repeat(nameLength));

        Assertions.assertThrows(InvalidUserInput.class, () -> this.createUser.execute(parameters));
        Assertions.assertEquals(0, repository.getUsers().size());
    }

    @Test
    public void createUser_Should_Execute_WhenValidInput() {
        List<String> parameters = List.of(VALID_USER_NAME);

        Assertions.assertDoesNotThrow(() -> this.createUser.execute(parameters));
        Assertions.assertEquals(1, repository.getUsers().size());
    }

    @Test
    public void createUser_Should_ThrowException_When_UserNameExists() {
        List<String> parameters = List.of(VALID_USER_NAME);
        createUser.execute(parameters);

        Assertions.assertThrows(InvalidUserInput.class, () -> createUser.execute(parameters));
        Assertions.assertEquals(1, repository.getUsers().size());
    }

}
