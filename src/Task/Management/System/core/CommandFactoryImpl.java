package Task.Management.System.core;

import Task.Management.System.commands.*;
import Task.Management.System.commands.contracts.Command;
import Task.Management.System.commands.enums.CommandType;
import Task.Management.System.core.contracts.CommandFactory;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.utils.ParsingHelpers;

public class CommandFactoryImpl implements CommandFactory {

    @Override
    public Command createCommandFromCommandName(String commandTypeAsString, TaskManagementSystemRepository taskManagementSystemRepository) {

        CommandType commandType = ParsingHelpers.tryParseEnum(commandTypeAsString, CommandType.class);

        switch (commandType) {
            case CREATE_NEW_PERSON:
                return new CreateNewPersonCommand(taskManagementSystemRepository);
            case SHOW_ALL_PEOPLE:
                return new ShowAllPeopleCommand(taskManagementSystemRepository);
            case SHOW_PERSON_ACTIVITY:
                return new ShowPersonActivityCommand(taskManagementSystemRepository);
            case CREATE_NEW_TEAM:
                return new CreateNewTeamCommand(taskManagementSystemRepository);
            case SHOW_ALL_TEAMS:
                return new ShowAllTeamsCommand(taskManagementSystemRepository);
            case SHOW_TEAM_ACTIVITY:
                return new ShowTeamActivityCommand(taskManagementSystemRepository);
            case ADD_PERSON_TO_TEAM:
                return new AddPersonToTeamCommand(taskManagementSystemRepository);
            case SHOW_ALL_TEAM_MEMBERS:
                return new ShowAllTeamMembersCommand(taskManagementSystemRepository);
            case CREATE_NEW_BOARD_IN_TEAM:
                return new CreateNewBoardInTeamCommand(taskManagementSystemRepository);
            case SHOW_ALL_TEAM_BOARDS:
                return new ShowAllTeamBoardsCommand(taskManagementSystemRepository);
            case SHOW_BOARD_ACTIVITY:
                return new ShowBoardActivityCommand(taskManagementSystemRepository);
            case CREATE_NEW_TASK_IN_BOARD:
                return new CreateNewTaskInBoardCommand(taskManagementSystemRepository);
            case CHANGE_BUG:
                return new ChangeBugCommand(taskManagementSystemRepository);
            case CHANGE_STORY:
                return new ChangeStoryCommand(taskManagementSystemRepository);
            case CHANGE_FEEDBACK:
                return new ChangeFeedbackCommand(taskManagementSystemRepository);
            case ASSIGN_TASK_TO_PERSON:
                return new AssignTaskToPersonCommand(taskManagementSystemRepository);
            case ADD_COMMENT_TO_TASK:
                return new AddCommentToTaskCommand(taskManagementSystemRepository);
            default:
                throw new IllegalArgumentException();
        }
    }
}
