package Task_Management_System.commands.show;

import Task_Management_System.commands.contracts.Command;
import Task_Management_System.core.CommandFactoryImpl;
import Task_Management_System.core.TaskManagementSystemRepositoryImpl;
import Task_Management_System.core.contracts.CommandFactory;
import Task_Management_System.core.contracts.TaskManagementSystemRepository;
import Task_Management_System.exceptions.InvalidNumberOfArguments;
import Task_Management_System.models.teams.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static Task_Management_System.commands.BaseCommand.NO_ITEMS_TO_DISPLAY;
import static Task_Management_System.commands.BaseCommand.TEAM;
import static Task_Management_System.models.TestData.TeamImpl.VALID_TEAM_NAME;

public class ShowAllTeams_Tests {

    CommandFactory commandFactory;
    TaskManagementSystemRepository repository;
    Command command;

    @BeforeEach
    public void setup() {
        commandFactory = new CommandFactoryImpl();
        repository = new TaskManagementSystemRepositoryImpl();
        command = commandFactory.createCommand("ShowAllTeams", repository);
    }

    @Test
    public void showAllTeams_Should_throwException_WhenValidArguments() {
        List<String> parameters = new ArrayList<>();
        parameters.add("Unnecessary parameter");

        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> command.execute(parameters));
    }

    @Test
    public void showAllTeams_Should_Indicate_When_NoUsersToDisplay() {
        Assertions.assertEquals(String.format(NO_ITEMS_TO_DISPLAY, "teams"), command.execute(List.of()));
    }

    @Test
    public void showAllTeams_Should_Execute_When_ValidInput() {
        Command createTeam = commandFactory.createCommand("CreateTeam", repository);
        List<String> parameters = new ArrayList<>();
        parameters.add(VALID_TEAM_NAME);
        createTeam.execute(parameters);

        Team team = repository.findTeam(VALID_TEAM_NAME);

        String output = String.format("Team: %s - Users: %d - Boards: %d - Tasks: %d",
                team.getName(),
                team.getUsers().size(),
                team.getBoards().size(),
                team.getBoards().stream().mapToInt(board -> board.getTasks().size()).sum());


        Assertions.assertEquals(output, command.execute(List.of()));
    }

}
