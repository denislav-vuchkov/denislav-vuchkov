package Task.Management.System.models.tasks.contracts;

import Task.Management.System.models.tasks.enums.Size;
import Task.Management.System.models.tasks.enums.StoryStatus;

public interface Story extends AssignableTask {

    Size getSize();

    void setSize(Size size);

    void setStatus(StoryStatus status);

}
