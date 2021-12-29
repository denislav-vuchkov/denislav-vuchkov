package Task.Management.System.models.contracts;

import Task.Management.System.models.Event;

import java.util.List;

public interface Loggable {


    List<Event> getLog();

}
