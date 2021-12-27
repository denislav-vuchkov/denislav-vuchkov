package Task.Management.System.models.tasks.contracts;

import Task.Management.System.models.tasks.enums.Size;

public interface Story extends AssignableTask {

    Size getSize();

    void setSize(Size size);

}
