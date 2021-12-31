package Task_Management_System.models.tasks.contracts;

import Task_Management_System.models.tasks.enums.Size;

public interface Story extends AssignableTask {

    Size getSize();

    void setSize(Size size);

}
