package Task.Management.System.models.tasks.contracts.small_contracts;

import Task.Management.System.models.tasks.contracts.Comment;

import java.util.List;

public interface Commentable {

    void addComment(Comment comment);

    List<Comment> getComments();

    String displayComments();

}
