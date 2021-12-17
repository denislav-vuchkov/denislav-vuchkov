package Task.Management.System.models.contracts;

import java.util.List;

public interface Task {

    int getID();

    String getTitle();

    String getDescription();

    String getStatus();

    List<Comment> getComments();

    String displayComments();

    String historyOfChanges();

    String displayDetails();

}
