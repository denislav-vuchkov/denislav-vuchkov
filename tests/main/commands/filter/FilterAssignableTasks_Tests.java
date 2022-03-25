package main.commands.filter;

import main.commands.contracts.Command;
import main.exceptions.InvalidUserInput;
import main.models.tasks.contracts.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static main.commands.BaseCommand.NO_ITEMS_TO_DISPLAY;
import static main.commands.filter.FilterAssignableTasks.*;

public class FilterAssignableTasks_Tests extends FilteringTests_Base{

    Command filter;

    public FilterAssignableTasks_Tests() {
    }

    @BeforeEach
    public void setup() {
        filter = commandFactory.createCommand("FilterAssignableTasks", repository);
    }

    @ParameterizedTest(name = "with length {0}")
    @ValueSource(ints = {MIN_ARGUMENTS-1, MAX_ARGUMENTS+1})
    public void filterAssignableTasks_Should_throwException_WhenInvalidArgumentsCounts(int parametersCount) {
        List<String> parameters = new ArrayList<>();

        for (int i = 1; i <= parametersCount; i++) {
            parameters.add("1");
        }

        Assertions.assertThrows(InvalidUserInput.class, () -> filter.execute(parameters));
    }

    @Test
    public void filterAssignableTasks_Should_ReturnCollection_WithTitleFilter() {
        List<String> parameters = List.of("Title:COVID");

        Story firstTask = repository.findStory(6);
        Story secondTask = repository.findStory(12);

        StringBuilder output = new StringBuilder();
        output.append(String.format("%s ID: %d - Title: %s - Priority: %s - Size: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                firstTask.getClass().getSimpleName().replace("Impl", ""),
                firstTask.getID(), firstTask.getTitle(), firstTask.getPriority(), firstTask.getSize(),
                firstTask.getStatus(), firstTask.getAssignee(),
                firstTask.getComments().size())).append(System.lineSeparator());

        output.append(String.format("%s ID: %d - Title: %s - Priority: %s - Size: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                secondTask.getClass().getSimpleName().replace("Impl", ""),
                secondTask.getID(), secondTask.getTitle(), secondTask.getPriority(), secondTask.getSize(),
                secondTask.getStatus(), secondTask.getAssignee(), secondTask.getComments().size()));

        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output.toString(), filter.execute(parameters));
    }

    @Test
    public void filterAssignableTasks_Should_ReturnCollection_WithTitleAndAssigneeFilter() {
        List<String> parameters = List.of("Title:COVID", "Assignee:Plam");

        Story firstTask = repository.findStory(12);

        StringBuilder output = new StringBuilder();
        output.append(String.format("%s ID: %d - Title: %s - Priority: %s - Size: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                firstTask.getClass().getSimpleName().replace("Impl", ""),
                firstTask.getID(), firstTask.getTitle(), firstTask.getPriority(), firstTask.getSize(),
                firstTask.getStatus(), firstTask.getAssignee(),
                firstTask.getComments().size()));

        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output.toString(), filter.execute(parameters));
    }

    @Test
    public void filterAssignableTasks_Should_ReturnCollection_WithTitleAndAssigneeFilterInReverseOrder() {
        List<String> parameters = List.of("Assignee:Tiho", "Title:COVID");

        Story firstTask = repository.findStory(6);

        StringBuilder output = new StringBuilder();
        output.append(String.format("%s ID: %d - Title: %s - Priority: %s - Size: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                firstTask.getClass().getSimpleName().replace("Impl", ""),
                firstTask.getID(), firstTask.getTitle(), firstTask.getPriority(), firstTask.getSize(),
                firstTask.getStatus(), firstTask.getAssignee(),
                firstTask.getComments().size()));

        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output.toString(), filter.execute(parameters));
    }

    @Test
    public void filterAssignableTasks_Should_ReturnEmptyCollection_WhenNoResults() {
        List<String> parameters = List.of("Title:COVID", "Assignee:Denis");

        String output = String.format(NO_ITEMS_TO_DISPLAY, "assignable tasks");

        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output, filter.execute(parameters));
    }


    @Test
    public void filterAssignableTasks_Should_ThrowException_WhenInvalidFilter() {
        List<String> parameters = List.of("FakeCriteria:Adsadasdsa");

        Assertions.assertThrows(InvalidUserInput.class, () -> filter.execute(parameters));
    }

}
