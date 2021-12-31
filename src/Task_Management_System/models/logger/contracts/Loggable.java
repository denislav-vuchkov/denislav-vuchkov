package Task_Management_System.models.logger.contracts;

import Task_Management_System.models.logger.EventImpl;

import java.util.List;

public interface Loggable {

    List<EventImpl> getLog();

}
