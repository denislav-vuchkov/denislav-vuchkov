package Task.Management.System.commands.task;

import Task.Management.System.commands.BaseCommand;
import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.CommentImpl;
import Task.Management.System.models.tasks.contracts.Comment;
import Task.Management.System.models.tasks.contracts.Task;
import Task.Management.System.models.teams.contracts.User;
import Task.Management.System.utils.ParsingHelpers;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class AddCommentToTask extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;
    public static final String COMMENT_ADDED = "Comment by %s added to Task ID %d.";
    public static final String COMMENT_EVENT = "User %s added a comment to task %d.";

    public AddCommentToTask(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {

        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        User author = getRepository().findUser(parameters.get(0));
        long ID = ParsingHelpers.tryParseLong(parameters.get(1), INVALID_ID);
        Task task = getRepository().findTask(ID);
        String content = parameters.get(2);
        getRepository().validateUserAndTaskFromSameTeam(author.getName(), task.getID());

        Comment comment = new CommentImpl(content, author.getName());

        task.addComment(comment);

        author.recordActivity(String.format(COMMENT_EVENT, author.getName(), task.getID()));
        return String.format(COMMENT_ADDED, author, task.getID());
    }
}
