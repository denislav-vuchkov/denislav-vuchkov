package Task.Management.System.commands.tasks;

import Task.Management.System.core.CommandFactoryImpl;
import Task.Management.System.core.TaskManagementSystemRepositoryImpl;
import Task.Management.System.core.contracts.CommandFactory;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.exceptions.InvalidUserInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateStory_Tests {

    CommandFactory commandFactory;
    TaskManagementSystemRepository repository;
    StringBuilder input = new StringBuilder("CreateStory");

    @BeforeEach
    void setupPreliminaryProgramState() {
        commandFactory = new CommandFactoryImpl();
        repository = new TaskManagementSystemRepositoryImpl();

        commandFactory.createCommand("CreateTeam {Team 1}", repository);
        commandFactory.createCommand("CreateUser {Denis}", repository);
        commandFactory.createCommand("CreateUser {Tisho}", repository);

        commandFactory.createCommand("AddUserToTeam {Denis} {Team 1}", repository);
        commandFactory.createCommand("AddUserToTeam {Tisho} {Team 1}", repository);
        commandFactory.createCommand("CreateBoard {TaskManagement} {Team 1}", repository);
    }


    @Test
    void executeCommand_ShouldThrowException_ifCreatorIsInvalid() {

        input.append(" {Invalid}");
        input.append(" {Team 1}");
        input.append(" {TaskManagement}");
        input.append(" {Activity Log Is Incomplete}");
        input.append(" {The activity report needs to show more stuff}");
        input.append(" {High}");
        input.append(" {Medium}");
        input.append(" {Tisho}");



    }

}
