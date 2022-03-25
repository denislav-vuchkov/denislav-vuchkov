package main.models.tasks.contracts.subcontracts;

import main.models.tasks.contracts.Comment;

import java.util.List;

public interface Commentable {

    void addComment(Comment comment);

    List<Comment> getComments();

}
