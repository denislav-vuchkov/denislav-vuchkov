package Task.Management.System.models.tasks.contracts;

import Task.Management.System.models.contracts.Changeable;

import java.util.List;

public interface Task extends Changeable {

    int getID();

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    String getStatus();

    void advanceStatus();

    void retractStatus();

    void addComment(Comment comment);

    List<Comment> getComments();

    String displayComments();

    String getChangeAt(int index);

    String displayAllDetails();

}
