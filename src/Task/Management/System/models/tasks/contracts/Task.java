package Task.Management.System.models.tasks.contracts;

import Task.Management.System.models.contracts.Changeable;

import java.util.List;

public interface Task extends Changeable {

    int getID();

    String getTitle();

    String getDescription();

    String getStatus();

    void advanceStatus();

    void retractStatus();

    void addComment(String description, String author);

    List<Comment> getComments();

    String displayComments();

    String getChangeAt(int index);

    String displayDetails();

}
