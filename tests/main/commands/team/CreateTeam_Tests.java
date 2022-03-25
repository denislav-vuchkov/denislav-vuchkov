package main.commands.team;

import main.commands.contracts.Command;
import main.core.CommandFactoryImpl;
import main.core.TaskManagementSystemRepositoryImpl;
import main.core.contracts.CommandFactory;
import main.core.contracts.TaskManagementSystemRepository;
import main.exceptions.InvalidNumberOfArguments;
import main.exceptions.InvalidUserInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static main.commands.team.CreateTeam.EXPECTED_NUMBER_OF_ARGUMENTS;
import static main.models.TestData.TeamImpl.VALID_TEAM_NAME;
import static main.models.teams.contracts.subcontracts.Nameable.NAME_MAX_LEN;
import static main.models.teams.contracts.subcontracts.Nameable.NAME_MIN_LEN;

public class CreateTeam_Tests {

    CommandFactory commandFactory;
    TaskManagementSystemRepository repository;
    Command createTeam;

    @BeforeEach
    public void setup() {
        commandFactory = new CommandFactoryImpl();
        repository = new TaskManagementSystemRepositoryImpl();
        createTeam = commandFactory.createCommand("CreateTeam", repository);
    }

    @ParameterizedTest(name = "with length {0}")
    @ValueSource(ints = {EXPECTED_NUMBER_OF_ARGUMENTS-1, EXPECTED_NUMBER_OF_ARGUMENTS+1})
    public void createTeam_Should_throwException_WhenValidArgumentsCounts(int parametersCount) {
        List<String> parameters = new ArrayList<>();

        for (int i = 1; i <= parametersCount; i++) {
            parameters.add("1");
        }

        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> createTeam.execute(parameters));
        Assertions.assertEquals(0, repository.getTeams().size());
    }

    @ParameterizedTest(name = "with length {0}")
    @ValueSource(ints = {NAME_MIN_LEN -1, NAME_MAX_LEN +1})
    public void createTeam_Should_throwException_whenInvalidTeamName(int nameLength) {
        List<String> parameters = List.of("x".repeat(nameLength));

        Assertions.assertThrows(InvalidUserInput.class, () -> this.createTeam.execute(parameters));
        Assertions.assertEquals(0, repository.getTeams().size());
    }

    @Test
    public void createTeam_Should_Execute_WhenValidInput() {
        List<String> parameters = List.of(VALID_TEAM_NAME);

        Assertions.assertDoesNotThrow(() -> this.createTeam.execute(parameters));
        Assertions.assertEquals(1, repository.getTeams().size());
    }

    @Test
    public void createTeam_Should_ThrowException_When_TeamNameExists() {
        List<String> parameters = List.of(VALID_TEAM_NAME);
        createTeam.execute(parameters);

        Assertions.assertThrows(InvalidUserInput.class, () -> createTeam.execute(parameters));
        Assertions.assertEquals(1, repository.getTeams().size());
    }

}
