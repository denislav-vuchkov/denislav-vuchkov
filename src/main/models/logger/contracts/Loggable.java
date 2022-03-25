package main.models.logger.contracts;

import main.models.logger.EventImpl;

import java.util.List;

public interface Loggable {

    List<EventImpl> getLog();

}
