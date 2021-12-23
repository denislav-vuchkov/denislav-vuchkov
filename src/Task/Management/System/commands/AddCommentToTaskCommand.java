package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.tasks.CommentImpl;
import Task.Management.System.models.tasks.contracts.Comment;
import Task.Management.System.utils.ParsingHelpers;

import java.util.List;

public class AddCommentToTaskCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;
    public static final String INVALID_ID = "Invalid ID provided.";
    public static final String COMMENT_SUCCESSFULLY_ADDED = "Comment by %s added to Task ID %d.";

    public AddCommentToTaskCommand(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        long taskID = ParsingHelpers.tryParseLong(parameters.get(0), INVALID_ID);
        String content = parameters.get(1);
        String author = parameters.get(2);
        getRepository().findUser(author);
        Comment comment = new CommentImpl(content, author);
        getRepository().findTask(taskID).addComment(comment);
        return String.format(COMMENT_SUCCESSFULLY_ADDED, author, taskID);
    }
}
