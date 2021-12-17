package Task.Management.System.models.tasks;

import Task.Management.System.models.tasks.contracts.ChangesLogger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChangesLoggerImpl implements ChangesLogger {

    private final List<String> changes;

    public ChangesLoggerImpl() {
        changes = new ArrayList<>();
    }

    @Override
    public void addChange(String description) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String timeStampedChange = String.format("Description: %s,%nChange made at: %s%n",
                description, dateTimeFormatter.format(currentTime));
        changes.add(timeStampedChange);
    }

    @Override
    public int size() {
        return changes.size();
    }

    @Override
    public String getCompleteHistory() {
        StringBuilder completeHistory = new StringBuilder();

        changes.forEach(e -> completeHistory.append(e));

        return changes.toString();
    }

    @Override
    public String getChangeAt(int index) {
        return changes.get(index);
    }
}
