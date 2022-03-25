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
import static main.commands.filter.FilterStories.MAX_ARGUMENTS;
import static main.commands.filter.FilterStories.MIN_ARGUMENTS;

public class FilterStories_Tests extends FilteringTests_Base {

    Command filter;

    public FilterStories_Tests() {
    }

    @BeforeEach
    public void setup() {
        filter = commandFactory.createCommand("FilterStories", repository);
    }

    @ParameterizedTest(name = "with length {0}")
    @ValueSource(ints = {MIN_ARGUMENTS-1, MAX_ARGUMENTS+1})
    public void filterStories_Should_throwException_WhenInvalidArgumentsCounts(int parametersCount) {
        List<String> parameters = new ArrayList<>();

        for (int i = 1; i <= parametersCount; i++) {
            parameters.add("1");
        }

        Assertions.assertThrows(InvalidUserInput.class, () -> filter.execute(parameters));
    }

    @Test
    public void filterStories_Should_ReturnCollection_WithStatusFilter() {
        List<String> parameters = List.of("Status:Not done");

        Story firstStory = repository.findStory(2);
        Story secondStory = repository.findStory(6);
        Story thirdStory = repository.findStory(7);
        Story fourthStory = repository.findStory(12);
        Story fifthStory = repository.findStory(13);

        StringBuilder output = new StringBuilder();
        output.append(String.format("%s ID: %d - Title: %s - Priority: %s - Size: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                firstStory.getClass().getSimpleName().replace("Impl", ""),
                firstStory.getID(), firstStory.getTitle(), firstStory.getPriority(), firstStory.getSize(),
                firstStory.getStatus(), firstStory.getAssignee(),
                firstStory.getComments().size())).append(System.lineSeparator());

        output.append(String.format("%s ID: %d - Title: %s - Priority: %s - Size: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                secondStory.getClass().getSimpleName().replace("Impl", ""),
                secondStory.getID(), secondStory.getTitle(), secondStory.getPriority(), secondStory.getSize(),
                secondStory.getStatus(), secondStory.getAssignee(),
                secondStory.getComments().size())).append(System.lineSeparator());

        output.append(String.format("%s ID: %d - Title: %s - Priority: %s - Size: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                thirdStory.getClass().getSimpleName().replace("Impl", ""),
                thirdStory.getID(), thirdStory.getTitle(), thirdStory.getPriority(), thirdStory.getSize(),
                thirdStory.getStatus(), thirdStory.getAssignee(),
                thirdStory.getComments().size())).append(System.lineSeparator());

        output.append(String.format("%s ID: %d - Title: %s - Priority: %s - Size: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                fourthStory.getClass().getSimpleName().replace("Impl", ""),
                fourthStory.getID(), fourthStory.getTitle(), fourthStory.getPriority(), fourthStory.getSize(),
                fourthStory.getStatus(), fourthStory.getAssignee(),
                fourthStory.getComments().size())).append(System.lineSeparator());

        output.append(String.format("%s ID: %d - Title: %s - Priority: %s - Size: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                fifthStory.getClass().getSimpleName().replace("Impl", ""),
                fifthStory.getID(), fifthStory.getTitle(), fifthStory.getPriority(), fifthStory.getSize(),
                fifthStory.getStatus(), fifthStory.getAssignee(),
                fifthStory.getComments().size()));

        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output.toString(), filter.execute(parameters));
    }

    @Test
    public void filterStories_Should_ReturnCollection_WithStatusAndAssigneeFilter() {
        List<String> parameters = List.of("Status:Not done", "Assignee:Plamenna");

        Story firstStory = repository.findStory(12);

        StringBuilder output = new StringBuilder();
        output.append(String.format("%s ID: %d - Title: %s - Priority: %s - Size: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                firstStory.getClass().getSimpleName().replace("Impl", ""),
                firstStory.getID(), firstStory.getTitle(), firstStory.getPriority(), firstStory.getSize(),
                firstStory.getStatus(), firstStory.getAssignee(),
                firstStory.getComments().size()));

        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output.toString(), filter.execute(parameters));
    }

    @Test
    public void filterStories_Should_ReturnCollection_WithStatusAndAssigneeFilterReversed() {
        List<String> parameters = List.of("Assignee:Plamenna", "Status:Not done");

        Story firstStory = repository.findStory(12);

        StringBuilder output = new StringBuilder();
        output.append(String.format("%s ID: %d - Title: %s - Priority: %s - Size: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                firstStory.getClass().getSimpleName().replace("Impl", ""),
                firstStory.getID(), firstStory.getTitle(), firstStory.getPriority(), firstStory.getSize(),
                firstStory.getStatus(), firstStory.getAssignee(),
                firstStory.getComments().size()));

        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output.toString(), filter.execute(parameters));
    }

    @Test
    public void filterStories_Should_ReturnCollection_WithAssigneeFilter() {
        List<String> parameters = List.of("Assignee:Plamenna");

        Story firstStory = repository.findStory(12);

        StringBuilder output = new StringBuilder();
        output.append(String.format("%s ID: %d - Title: %s - Priority: %s - Size: %s - " +
                        "Status: %s - Assignee: %s - Comments: %d",
                firstStory.getClass().getSimpleName().replace("Impl", ""),
                firstStory.getID(), firstStory.getTitle(), firstStory.getPriority(), firstStory.getSize(),
                firstStory.getStatus(), firstStory.getAssignee(),
                firstStory.getComments().size()));

        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output.toString(), filter.execute(parameters));
    }

    @Test
    public void filterStories_Should_ReturnEmptyCollection_WhenNoResults() {
        List<String> parameters = List.of("Status:Done");

        String output = String.format(NO_ITEMS_TO_DISPLAY, "stories");

        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output, filter.execute(parameters));
    }


    @Test
    public void filterStories_Should_ThrowException_WhenInvalidFilter() {
        List<String> parameters = List.of("FakeCriteria:Adsadasdsa");

        Assertions.assertThrows(InvalidUserInput.class, () -> filter.execute(parameters));
    }

    @Test
    public void filterStories_Should_ThrowException_WhenInvalidValue() {
        List<String> parameters = List.of("Status:Alabalala");

        Assertions.assertThrows(InvalidUserInput.class, () -> filter.execute(parameters));
    }

}
