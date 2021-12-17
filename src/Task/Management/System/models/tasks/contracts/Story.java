package Task.Management.System.models.tasks.contracts;

import Task.Management.System.models.tasks.enums.*;

public interface Story extends AssignableTask {

    void setStatus(StoryStatus status);

    Size getSize();

    void setSize(Size size);

}
