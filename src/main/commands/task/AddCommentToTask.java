package main.commands.task;

import main.commands.BaseCommand;
import main.core.contracts.TaskManagementSystemRepository;
import main.models.tasks.CommentImpl;
import main.models.tasks.contracts.Comment;
import main.models.tasks.contracts.Task;
import main.models.teams.contracts.Team;
import main.models.teams.contracts.User;
import main.utils.FormatHelpers;
import main.utils.ParsingHelpers;
import main.utils.ValidationHelpers;

import java.util.List;

public class AddCommentToTask extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;

    public static final String COMMENT_EVENT = "User %s: Added a comment to %s with ID %d.";

    public AddCommentToTask(TaskManagementSystemRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        User author = getRepository().findUser(parameters.get(0));
        long ID = ParsingHelpers.tryParseLong(parameters.get(1), INVALID_ID);
        Task task = getRepository().findTask(ID);
        getRepository().validateUserAndTaskFromSameTeam(author.getName(), task.getID());
        Comment comment = new CommentImpl(parameters.get(2), author.getName());
        String event = String.format(COMMENT_EVENT, author.getName(), FormatHelpers.getType(task), task.getID());
        Team team = getRepository().findTeam(task);
        author.log(event, team.getName());
        task.addComment(comment);
        return event;
    }
}
