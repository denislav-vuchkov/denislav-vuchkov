package Task.Management.System.commands.filter;

import Task.Management.System.commands.contracts.Command;
import Task.Management.System.core.CommandFactoryImpl;
import Task.Management.System.core.TaskManagementSystemRepositoryImpl;
import Task.Management.System.core.contracts.CommandFactory;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.enums.Priority;
import Task.Management.System.models.tasks.enums.Severity;
import Task.Management.System.models.tasks.enums.Size;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.models.TestData.AssignableTask.VALID_PRIORITY;
import static Task.Management.System.models.TestData.BoardImpl.VALID_BOARD_NAME;
import static Task.Management.System.models.TestData.BugImpl.VALID_SEVERITY;
import static Task.Management.System.models.TestData.FeedbackImpl.VALID_RATING;
import static Task.Management.System.models.TestData.TaskBase.VALID_DESCRIPTION;
import static Task.Management.System.models.TestData.TaskBase.VALID_TITLE;
import static Task.Management.System.models.TestData.TeamImpl.VALID_TEAM_NAME;
import static Task.Management.System.models.TestData.UserImpl.VALID_USER_NAME;

public class FilteringTests_Base {

    protected static CommandFactory commandFactory;
    protected static TaskManagementSystemRepository repository;
    protected static TaskManagementSystemRepository emptyRepository;

    public FilteringTests_Base() {
        commandFactory = new CommandFactoryImpl();
        repository = new TaskManagementSystemRepositoryImpl();
        emptyRepository = new TaskManagementSystemRepositoryImpl();

        generateUsers();
        generateTeams();

        addUsersToTeams();

        generateBoards();
        generateTasks();

    }

    private void generateUsers() {
        Command createUser = commandFactory.createCommand("CreateUser", repository);
        createUser.execute(List.of(VALID_USER_NAME));
        createUser.execute(List.of("Tihomir"));
        createUser.execute(List.of("Denis"));
        createUser.execute(List.of("Plamenna"));
        createUser.execute(List.of("Alexander"));
    }

    private void generateTeams() {
        Command createTeam = commandFactory.createCommand("CreateTeam", repository);
        createTeam.execute(List.of(VALID_TEAM_NAME));
        createTeam.execute(List.of("Team Rocket"));
        createTeam.execute(List.of("Team Two"));
    }

    private void addUsersToTeams() {
        Command addUserToTeam = commandFactory.createCommand("AddUserToTeam", repository);
        addUserToTeam.execute(List.of(VALID_USER_NAME, VALID_TEAM_NAME));
        addUserToTeam.execute(List.of("Tihomir", "Team Rocket"));
        addUserToTeam.execute(List.of("Denis", "Team Rocket"));
        addUserToTeam.execute(List.of("Plamenna", "Team Two"));
        addUserToTeam.execute(List.of("Alexander", "Team Two"));
    }

    private void generateBoards() {
        Command createBoard = commandFactory.createCommand("CreateBoard", repository);
        createBoard.execute(List.of(VALID_BOARD_NAME, VALID_TEAM_NAME));
        createBoard.execute(List.of("Rocket Board", "Team Rocket"));
        createBoard.execute(List.of("Two Board", "Team Two"));
    }

    private void generateTasks() {
        Command createBug = commandFactory.createCommand("CreateBug", repository);
        Command createStory = commandFactory.createCommand("CreateStory", repository);
        Command createFeedback = commandFactory.createCommand("CreateFeedback", repository);

        //Tasks in first team with default name
        List<String> parameters = List.of(VALID_USER_NAME,
                VALID_TEAM_NAME,
                VALID_BOARD_NAME,
                VALID_TITLE,
                VALID_DESCRIPTION,
                "Step 1;Step 2;Step 3",
                Priority.LOW.toString(),
                Severity.MINOR.toString(),
                VALID_USER_NAME);
        createBug.execute(parameters); //ID 1

        parameters = List.of(VALID_USER_NAME,
                VALID_TEAM_NAME,
                VALID_BOARD_NAME,
                VALID_TITLE,
                VALID_DESCRIPTION,
                Priority.MEDIUM.toString(),
                Size.LARGE.toString(),
                VALID_USER_NAME);
        createStory.execute(parameters);//ID 2

        parameters = List.of(VALID_USER_NAME,
                VALID_TEAM_NAME,
                VALID_BOARD_NAME,
                VALID_TITLE,
                VALID_DESCRIPTION,
                String.valueOf(VALID_RATING));
        createFeedback.execute(parameters);//ID 3


        //Tasks in Team Rocker
        parameters = List.of("Tihomir",
                "Team Rocket",
                "Rocket Board",
                "Finance related tasks",
                "Bitcoins, Mitcoins, all the coins!",
                "Step 1;Step 2;Step 3",
                Priority.HIGH.toString(),
                Severity.CRITICAL.toString(),
                "Denis");
        createBug.execute(parameters); //ID 4

        parameters = List.of("Tihomir",
                "Team Rocket",
                "Rocket Board",
                "NFT is all the craze",
                "Create non-fungible tokens that will sell for milions. ASAP!",
                "Step 1;Step 2;Step 3",
                Priority.HIGH.toString(),
                Severity.CRITICAL.toString(),
                "Tihomir");
        createBug.execute(parameters); //ID 5

        parameters = List.of("Tihomir",
                "Team Rocket",
                "Rocket Board",
                "Create conspiracy about COVID-19 / vaccines",
                "Create conspiracies like those made in Russia to make people scared of the vaccines!",
                Priority.MEDIUM.toString(),
                Size.MEDIUM.toString(),
                "Tihomir");
        createStory.execute(parameters); //ID 6

        parameters = List.of("Tihomir",
                "Team Rocket",
                "Rocket Board",
                "Create a new telenovela for the bulgarian market",
                "Create something like the old telenovela of Three Sisters and Three Brothers. 30000 episodes minimum!",
                Priority.HIGH.toString(),
                Size.LARGE.toString(),
                "");
        createStory.execute(parameters); //ID 7

        parameters = List.of("Tihomir",
                "Team Rocket",
                "Rocket Board",
                "Amazing performance from Denis!",
                "This feedback is about all the great work Denis did during the OOP project!",
                "9");
        createFeedback.execute(parameters); //ID 8

        parameters = List.of("Denis",
                "Team Rocket",
                "Rocket Board",
                "Mediocre performance from Tihomir!",
                "This feedback the not so great work Tihomir did during the OOP project!",
                "6");
        createFeedback.execute(parameters); //ID 9


        //Tasks in Team Two
        parameters = List.of("Plamenna",
                "Team Two",
                "Two Board",
                "Finance and fintech projects",
                "Bitcoins, Mitcoins, all the coins!",
                "Step 1;Step 2;Step 3",
                Priority.HIGH.toString(),
                Severity.MAJOR.toString(),
                "Alexander");
        createBug.execute(parameters); //ID 10

        parameters = List.of("Plamenna",
                "Team Two",
                "Two Board",
                "NFT tokens are absolutely fantastic",
                "Create non-fungible tokens that will sell for milions. ASAP!",
                "Step 1;Step 2;Step 3",
                Priority.MEDIUM.toString(),
                Severity.MINOR.toString(),
                "Alexander");
        createBug.execute(parameters); //ID 11

        parameters = List.of("Plamenna",
                "Team Two",
                "Two Board",
                "Choose the best conspiracies about COVID",
                "Create conspiracies like those made in Russia to make people scared of the vaccines!",
                Priority.HIGH.toString(),
                Size.SMALL.toString(),
                "Plamenna");
        createStory.execute(parameters); //ID 12

        parameters = List.of("Plamenna",
                "Team Two",
                "Two Board",
                "Spanish telenovela for the turkish market",
                "Create something like the old telenovela of Three Sisters and Three Brothers. 30000 episodes minimum!",
                Priority.HIGH.toString(),
                Size.LARGE.toString(),
                "");
        createStory.execute(parameters); //ID 13

        parameters = List.of("Plamenna",
                "Team Two",
                "Two Board",
                "Very unsatisfactory performance from Alex!",
                "This feedback is about all the great work Alex did not do during the OOP project!",
                "3");
        createFeedback.execute(parameters); //ID 14

        parameters = List.of("Alexander",
                "Team Two",
                "Two Board",
                "Amazing performance from Plamenna!",
                "This feedback is about the great work Plamenna did during the OOP project!",
                "10");
        createFeedback.execute(parameters); //ID 15

    }
}
