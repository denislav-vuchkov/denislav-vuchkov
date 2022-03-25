package main.utils;

import main.models.logger.EventImpl;
import main.models.logger.contracts.Event;
import main.models.logger.contracts.Loggable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FormatHelpers {

    public static String getType(Object object) {
        return object.getClass().getSimpleName().replace("Impl", "");
    }

    public static <B extends Loggable, U extends Loggable> List<EventImpl> combineLogs
            (List<EventImpl> history, List<B> boards, List<U> users, String targetTeam) {
        history.addAll(getEvents(boards));
        users.forEach(user -> history.addAll(extractTeamSpecificHistory(user.getLog(), targetTeam)));

        history.sort(Comparator.comparing(Event::getOccurrence));

        return history;
    }

    public static <U extends Loggable> List<EventImpl> combineLogs
            (List<EventImpl> history, List<U> users) {
        history.addAll(getEvents(users));

        history.sort(Comparator.comparing(Event::getOccurrence));

        return history;
    }

    public static List<EventImpl> extractTeamSpecificHistory
            (List<EventImpl> history, String targetTeam) {
        return history.stream()
                .filter(event -> event.getTeam().equals(targetTeam))
                .sorted(Comparator.comparing(Event::getOccurrence)).collect(Collectors.toList());
    }

    private static <T extends Loggable> List<EventImpl> getEvents
            (List<T> elements) {
        return elements.stream().flatMap(element -> element.getLog().stream()).collect(Collectors.toList());
    }


}
