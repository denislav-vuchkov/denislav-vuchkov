package Task.Management.System.models.logger.contracts;

import Task.Management.System.models.logger.EventImpl;

import java.util.List;

public interface Loggable {

    List<EventImpl> getLog();

}
