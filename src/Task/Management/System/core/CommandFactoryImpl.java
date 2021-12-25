package Task.Management.System.core;

import Task.Management.System.commands.activity.ShowBoardActivityCommand;
import Task.Management.System.commands.activity.ShowTeamActivityCommand;
import Task.Management.System.commands.activity.ShowUserActivityCommand;
import Task.Management.System.commands.contracts.Command;
import Task.Management.System.commands.enums.CommandType;
import Task.Management.System.commands.list.*;
import Task.Management.System.commands.show.*;
import Task.Management.System.commands.task.*;
import Task.Management.System.commands.team.*;
import Task.Management.System.core.contracts.CommandFactory;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.utils.ParsingHelpers;

public class CommandFactoryImpl implements CommandFactory {

    @Override
    public Command createCommandFromCommandName(String commandTypeAsString, TaskManagementSystemRepository taskManagementSystemRepository) {

        CommandType commandType = ParsingHelpers.tryParseCommand(commandTypeAsString, CommandType.class);

        switch (commandType) {

            case SHOW_ALL_TEAMS:
                return new ShowAllTeamsCommand(taskManagementSystemRepository);
            case SHOW_ALL_USERS:
                return new ShowAllUsersCommand(taskManagementSystemRepository);
            case SHOW_ALL_TASKS:
                return new ShowAllTasksCommand(taskManagementSystemRepository);
            case SHOW_TEAM_USERS:
                return new ShowTeamUsersCommand(taskManagementSystemRepository);
            case SHOW_TEAM_BOARDS:
                return new ShowTeamBoardsCommand(taskManagementSystemRepository);
            case SHOW_TEAM_ACTIVITY:
                return new ShowTeamActivityCommand(taskManagementSystemRepository);
            case SHOW_BOARD_ACTIVITY:
                return new ShowBoardActivityCommand(taskManagementSystemRepository);
            case SHOW_USER_ACTIVITY:
                return new ShowUserActivityCommand(taskManagementSystemRepository);
            case CREATE_TEAM:
                return new CreateTeamCommand(taskManagementSystemRepository);
            case CREATE_BOARD:
                return new CreateBoardCommand(taskManagementSystemRepository);
            case CREATE_USER:
                return new CreateUserCommand(taskManagementSystemRepository);
            case ADD_USER_TO_TEAM:
                return new AddUserToTeamCommand(taskManagementSystemRepository);
            case ADD_COMMENT_TO_TASK:
                return new AddCommentToTaskCommand(taskManagementSystemRepository);
            case ASSIGN_TASK_TO_USER:
                return new AssignTaskToUserCommand(taskManagementSystemRepository);
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
            case LIST_ALL_TASKS_FILTERED:
                return new ListAllTasksFiltered(taskManagementSystemRepository);
            case LIST_ALL_TASKS_SORTED:
                return new ListAllTasksSorted(taskManagementSystemRepository);
            case LIST_BUGS_FILTERED:
                return new ListBugsFiltered(taskManagementSystemRepository);
            case LIST_BUGS_SORTED:
                return new ListBugsSorted(taskManagementSystemRepository);
            case LIST_STORIES_FILTERED:
                return new ListStoriesFiltered(taskManagementSystemRepository);
            case LIST_STORIES_SORTED:
                return new ListStoriesSorted(taskManagementSystemRepository);
            case LIST_FEEDBACKS_FILTERED:
                return new ListFeedbacksFiltered(taskManagementSystemRepository);
            case LIST_FEEDBACKS_SORTED:
                return new ListFeedbacksSorted(taskManagementSystemRepository);
            case LIST_TASKS_WITH_ASSIGNEE_FILTERED:
                return new ListAssignableTasksFiltered(taskManagementSystemRepository);
            case LIST_TASKS_WITH_ASSIGNEE_SORTED:
                return new ListAssignableTasksSorted(taskManagementSystemRepository);
            default:
                throw new IllegalArgumentException();
        }
    }
}
