package main.commands.filter;

import main.commands.contracts.Command;
import main.exceptions.InvalidNumberOfArguments;
import main.exceptions.InvalidUserInput;
import main.models.tasks.contracts.Bug;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static main.commands.BaseCommand.NO_ITEMS_TO_DISPLAY;
import static main.commands.filter.FilterAllTasks.EXPECTED_NUMBER_OF_ARGUMENTS;

public class FilterAllTasks_Tests extends FilteringTests_Base{

    Command filter;

    public FilterAllTasks_Tests() {
    }

    @BeforeEach
    public void setup() {
        filter = commandFactory.createCommand("FilterAllTasks", repository);
    }

    @ParameterizedTest(name = "with length {0}")
    @ValueSource(ints = {EXPECTED_NUMBER_OF_ARGUMENTS-1, EXPECTED_NUMBER_OF_ARGUMENTS+1})
    public void filterAllTasks_Should_throwException_WhenInvalidArgumentsCounts(int parametersCount) {
        List<String> parameters = new ArrayList<>();

        for (int i = 1; i <= parametersCount; i++) {
            parameters.add("1");
        }

        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> filter.execute(parameters));
    }

    /*Bug toString return String.format("%s ID: %d - Title: %s - Steps: %d - Priority: %s - Severity: %s - " +
            "Status: %s - Assignee: %s - Comments: %d",
            this.getClass().getSimpleName().replace("Impl", ""),
    getID(), getTitle(), getStepsToReproduce().size(), getPriority(), getSeverity(),
    getStatus(), getAssignee(), getComments().size());*/

    /*Story toString return String.format("%s ID: %d - Title: %s - Priority: %s - Size: %s - " +
            "Status: %s - Assignee: %s - Comments: %d",
            this.getClass().getSimpleName().replace("Impl", ""),
    getID(), getTitle(), getPriority(), getSize(),
    getStatus(), getAssignee(), getComments().size());*/

    /*Feedback toString  public String toString() {
        return String.format("%s ID: %d - Title: %s - Rating: %s - Status: %s - Comments: %d",
                this.getClass().getSimpleName().replace("Impl", ""),
                getID(), getTitle(), getRating(), getStatus(), getComments().size());*/

    @Test
    public void filterAllTasks_Should_ReturnCollection_WhenValidFilter() {
        List<String> parameters = List.of("Title:Finance");

        Bug firstBug = repository.findBug(4);
        Bug secondBug = repository.findBug(10);
        StringBuilder output = new StringBuilder();
        output.append(String.format("%s ID: %d - Title: %s - Steps: %d - Priority: %s - Severity: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                firstBug.getClass().getSimpleName().replace("Impl", ""),
                firstBug.getID(), firstBug.getTitle(), firstBug.getStepsToReproduce().size(),
                firstBug.getPriority(), firstBug.getSeverity(), firstBug.getStatus(),
                firstBug.getAssignee(), firstBug.getComments().size())).append(System.lineSeparator());

        output.append(String.format("%s ID: %d - Title: %s - Steps: %d - Priority: %s - Severity: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                secondBug.getClass().getSimpleName().replace("Impl", ""),
                secondBug.getID(), secondBug.getTitle(), secondBug.getStepsToReproduce().size(),
                secondBug.getPriority(), secondBug.getSeverity(), secondBug.getStatus(),
                secondBug.getAssignee(), secondBug.getComments().size()));

        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output.toString(), filter.execute(parameters));
    }

    @Test
    public void filterAllTasks_Should_ReturnEmptyCollection_WhenValidFilter() {
        List<String> parameters = List.of("Title:Adsadasdsa");

        String output = String.format(NO_ITEMS_TO_DISPLAY, "tasks");

        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output, filter.execute(parameters));
    }

    @Test
    public void filterAllTasks_Should_ThrowException_WhenInvalidFilter() {
        List<String> parameters = List.of("FakeCriteria:Adsadasdsa");

        Assertions.assertThrows(InvalidUserInput.class, () -> filter.execute(parameters));
    }

}
