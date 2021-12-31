package Task.Management.System.utils;

import Task.Management.System.models.logger.EventImpl;
import Task.Management.System.models.logger.contracts.Event;
import Task.Management.System.models.logger.contracts.Loggable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FormatHelpers {

    public static String getType(Object object) {
        return object.getClass().getSimpleName().replace("Impl", "");
    }

    public static <B extends Loggable, U extends Loggable> List<EventImpl> combineLogs
            (List<EventImpl> history, List<B> boards, List<U> users) {
        history.addAll(getEvents(boards));
        history.addAll(getEvents(users));

        history.sort(Comparator.comparing(Event::getOccurrence));

        return history;
    }

    public static <U extends Loggable> List<EventImpl> combineLogs
            (List<EventImpl> history, List<U> users) {
        history.addAll(getEvents(users));

        history.sort(Comparator.comparing(Event::getOccurrence));

        return history;
    }

    public static List<EventImpl> combineLogs
            (List<EventImpl> history) {
        return history.stream().sorted(Comparator.comparing(EventImpl::getOccurrence)).collect(Collectors.toList());
    }

    private static <T extends Loggable> List<EventImpl> getEvents
            (List<T> elements) {
        return elements.stream().flatMap(element -> element.getLog().stream()).collect(Collectors.toList());
    }


}
