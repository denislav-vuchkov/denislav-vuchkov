package Task.Management.System.models.contracts;

import Task.Management.System.models.enums.*;

public interface Story extends Task {

    void setStatus(StoryStatus status);

    Priority getPriority();

    void increasePriority();

    void decreasePriority();

    void setPriority(Priority priority);

    Size getSize();

    void setSize(Size size);

    String getAssignee();

    void setAssignee(String assignee);

    void unAssign();

}
