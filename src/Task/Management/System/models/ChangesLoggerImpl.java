package Task.Management.System.models;

import Task.Management.System.models.contracts.ChangesLogger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChangesLoggerImpl implements ChangesLogger {

    private final List<String> changes;

    public ChangesLoggerImpl() {
        changes = new ArrayList<>();
    }

    @Override
    public boolean addChange(String description) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        String timeStampedChange = String.format("Description: %s,%nChange made at: %s%n",
                description, dateTimeFormatter.format(currentTime));

        if (!changes.contains(timeStampedChange)) {
            changes.add(timeStampedChange);
            return true;
        } else {
            return false;
        }
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
