package Task.Management.System.commands.task;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.CommentImpl;
import Task.Management.System.models.tasks.contracts.Comment;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.Team;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;

import java.util.List;

public class AddCommentToTaskCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;
    public static final String INVALID_ID = "Invalid ID provided.";
    public static final String COMMENT_SUCCESSFULLY_ADDED = "Comment by %s added to Task ID %d.";
    public static final String COMMENT_ADDED = "User %s added a comment to task %d.";

    public AddCommentToTaskCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        long taskID = ParsingHelpers.tryParseLong(parameters.get(0), INVALID_ID);
        String content = parameters.get(1);
        User author = getRepository().findUser(parameters.get(2));

        Task task = getRepository().findTask(taskID);
        Team team = getRepository().findTeamWhereTaskIsLocated(task);
        getRepository().validateUserIsFromTeam(author.getName(), team.getName());

        Comment comment = new CommentImpl(content, author.getName());
        task.addComment(comment);

        author.recordActivity(String.format(COMMENT_ADDED, author.getName(), task.getID()));

        return String.format(COMMENT_SUCCESSFULLY_ADDED, author, taskID);
    }
}
