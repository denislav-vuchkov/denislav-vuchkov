package Task_Management_System.models.tasks.contracts.subcontracts;

import Task_Management_System.models.tasks.contracts.Comment;

import java.util.List;

public interface Commentable {

    void addComment(Comment comment);

    List<Comment> getComments();

}
