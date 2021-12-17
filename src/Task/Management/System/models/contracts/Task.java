package Task.Management.System.models.contracts;

import java.util.List;

public interface Task {

    int getID();

    String getTitle();

    String getDescription();

    String getStatus();

    void advanceStatus();

    void retractStatus();

    void addComment(String description, String author);

    List<Comment> getComments();

    String displayComments();

    String historyOfChanges();

    String getChangeAt(int index);

    String displayDetails();

}
