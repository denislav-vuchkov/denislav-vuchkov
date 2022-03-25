package main.models.tasks.contracts;

import main.models.tasks.enums.Size;

public interface Story extends AssignableTask {

    Size getSize();

    void setSize(Size size);

}
