package Task.Management.System.commands.filter;

import Task.Management.System.commands.contracts.Command;
import Task.Management.System.exceptions.InvalidUserInput;
import Task.Management.System.models.tasks.contracts.Bug;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static Task.Management.System.commands.BaseCommand.NO_ITEMS_TO_DISPLAY;
import static Task.Management.System.commands.filter.FilterBugs.MIN_ARGUMENTS;
import static Task.Management.System.commands.filter.FilterBugs.MAX_ARGUMENTS;

public class FilterBugs_Tests extends FilteringTests_Base {

    Command filter;

    public FilterBugs_Tests() {
    }

    @BeforeEach
    public void setup() {
        filter = commandFactory.createCommand("FilterBugs", repository);
    }

    @ParameterizedTest(name = "with length {0}")
    @ValueSource(ints = {MIN_ARGUMENTS-1, MAX_ARGUMENTS+1})
    public void filterBugs_Should_throwException_WhenInvalidArgumentsCounts(int parametersCount) {
        List<String> parameters = new ArrayList<>();

        for (int i = 1; i <= parametersCount; i++) {
            parameters.add("1");
        }

        Assertions.assertThrows(InvalidUserInput.class, () -> filter.execute(parameters));
    }

    @Test
    public void filterBugs_Should_ReturnCollection_WithStatusFilter() {
        List<String> parameters = List.of("Status:Active");

        Bug firstBug = repository.findBug(1);
        Bug secondBug = repository.findBug(4);
        Bug thirdBug = repository.findBug(5);
        Bug fourthBug = repository.findBug(10);
        Bug fifthBug = repository.findBug(11);

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
                secondBug.getAssignee(), secondBug.getComments().size())).append(System.lineSeparator());

        output.append(String.format("%s ID: %d - Title: %s - Steps: %d - Priority: %s - Severity: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                thirdBug.getClass().getSimpleName().replace("Impl", ""),
                thirdBug.getID(), thirdBug.getTitle(), thirdBug.getStepsToReproduce().size(),
                thirdBug.getPriority(), thirdBug.getSeverity(), thirdBug.getStatus(),
                thirdBug.getAssignee(), thirdBug.getComments().size())).append(System.lineSeparator());

        output.append(String.format("%s ID: %d - Title: %s - Steps: %d - Priority: %s - Severity: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                fourthBug.getClass().getSimpleName().replace("Impl", ""),
                fourthBug.getID(), fourthBug.getTitle(), fourthBug.getStepsToReproduce().size(),
                fourthBug.getPriority(), fourthBug.getSeverity(), fourthBug.getStatus(),
                fourthBug.getAssignee(), fourthBug.getComments().size())).append(System.lineSeparator());

        output.append(String.format("%s ID: %d - Title: %s - Steps: %d - Priority: %s - Severity: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                fifthBug.getClass().getSimpleName().replace("Impl", ""),
                fifthBug.getID(), fifthBug.getTitle(), fifthBug.getStepsToReproduce().size(),
                fifthBug.getPriority(), fifthBug.getSeverity(), fifthBug.getStatus(),
                fifthBug.getAssignee(), fifthBug.getComments().size()));

        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output.toString(), filter.execute(parameters));
    }

    @Test
    public void filterBugs_Should_ReturnCollection_WithStatusAndAssigneeFilter() {
        List<String> parameters = List.of("Status:Active", "Assignee:Denis");

        Bug firstBug = repository.findBug(4);

        StringBuilder output = new StringBuilder();
        output.append(String.format("%s ID: %d - Title: %s - Steps: %d - Priority: %s - Severity: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                firstBug.getClass().getSimpleName().replace("Impl", ""),
                firstBug.getID(), firstBug.getTitle(), firstBug.getStepsToReproduce().size(),
                firstBug.getPriority(), firstBug.getSeverity(), firstBug.getStatus(),
                firstBug.getAssignee(), firstBug.getComments().size()));

        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output.toString(), filter.execute(parameters));
    }

    @Test
    public void filterBugs_Should_ReturnCollection_WithStatusAndAssigneeFilterReversed() {
        List<String> parameters = List.of("Assignee:Alexander", "Status:Active");

        Bug firstBug = repository.findBug(10);
        Bug secondBug = repository.findBug(11);

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
    public void filterBugs_Should_ReturnCollection_WithAssigneeFilter() {
        List<String> parameters = List.of("Assignee:Den"); //Using shortened version for Denis

        Bug firstBug = repository.findBug(4);

        StringBuilder output = new StringBuilder();
        output.append(String.format("%s ID: %d - Title: %s - Steps: %d - Priority: %s - Severity: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                firstBug.getClass().getSimpleName().replace("Impl", ""),
                firstBug.getID(), firstBug.getTitle(), firstBug.getStepsToReproduce().size(),
                firstBug.getPriority(), firstBug.getSeverity(), firstBug.getStatus(),
                firstBug.getAssignee(), firstBug.getComments().size()));

        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output.toString(), filter.execute(parameters));
    }

    @Test
    public void filterBugs_Should_ReturnEmptyCollection_WhenNoResults() {
        List<String> parameters = List.of("Status:Fixed");

        String output = String.format(NO_ITEMS_TO_DISPLAY, "bugs");

        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output, filter.execute(parameters));
    }


    @Test
    public void filterBugs_Should_ThrowException_WhenInvalidFilter() {
        List<String> parameters = List.of("FakeCriteria:Adsadasdsa");

        Assertions.assertThrows(InvalidUserInput.class, () -> filter.execute(parameters));
    }

    @Test
    public void filterBugs_Should_ThrowException_WhenInvalidValue() {
        List<String> parameters = List.of("Status:Alabalala");

        Assertions.assertThrows(InvalidUserInput.class, () -> filter.execute(parameters));
    }

}
