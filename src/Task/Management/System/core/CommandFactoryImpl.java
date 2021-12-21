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
            case CREATE_USER:
                return new CreateUserCommand(taskManagementSystemRepository);
            case SHOW_ALL_USERS:
                return new ShowAllUsersCommand(taskManagementSystemRepository);
            case SHOW_USER_ACTIVITY:
                return new ShowUserActivityCommand(taskManagementSystemRepository);
            case CREATE_TEAM:
                return new CreateTeamCommand(taskManagementSystemRepository);
            case SHOW_ALL_TEAMS:
                return new ShowAllTeamsCommand(taskManagementSystemRepository);
            case SHOW_TEAM_ACTIVITY:
                return new ShowTeamActivityCommand(taskManagementSystemRepository);
            case ADD_USER_TO_TEAM:
                return new AddUserToTeamCommand(taskManagementSystemRepository);
            case SHOW_ALL_USERS_IN_TEAM:
                return new ShowAllUsersInTeamCommand(taskManagementSystemRepository);
            case CREATE_BOARD:
                return new CreateBoardCommand(taskManagementSystemRepository);
            case SHOW_ALL_TEAM_BOARDS:
                return new ShowAllTeamBoardsCommand(taskManagementSystemRepository);
            case SHOW_BOARD_ACTIVITY:
                return new ShowBoardActivityCommand(taskManagementSystemRepository);
            case CREATE_BUG:
                return new CreateBugCommand(taskManagementSystemRepository);
            case CREATE_STORY:
                return new CreateStoryCommand(taskManagementSystemRepository);
            case CREATE_FEEDBACK:
                return new CreateFeedbackCommand(taskManagementSystemRepository);
            case CHANGE_BUG:
                return new ChangeBugCommand(taskManagementSystemRepository);
            case CHANGE_STORY:
                return new ChangeStoryCommand(taskManagementSystemRepository);
            case CHANGE_FEEDBACK:
                return new ChangeFeedbackCommand(taskManagementSystemRepository);
            case ASSIGN_TASK_TO_USER:
                return new AssignTaskToUserCommand(taskManagementSystemRepository);
            case ADD_COMMENT_TO_TASK:
                return new AddCommentToTaskCommand(taskManagementSystemRepository);
            default:
                throw new IllegalArgumentException();
        }
    }
}
