package Task_Management_System.commands.filter;

import Task_Management_System.commands.contracts.Command;
import Task_Management_System.exceptions.InvalidNumberOfArguments;
import Task_Management_System.exceptions.InvalidUserInput;
import Task_Management_System.models.tasks.contracts.Feedback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static Task_Management_System.commands.BaseCommand.NO_ITEMS_TO_DISPLAY;
import static Task_Management_System.commands.filter.FilterFeedbacks.EXPECTED_NUMBER_OF_ARGUMENTS;

public class FilterFeedbacks_Tests extends FilteringTests_Base {

    Command filter;

    public FilterFeedbacks_Tests() {
    }

    @BeforeEach
    public void setup() {
        filter = commandFactory.createCommand("FilterFeedbacks", repository);
    }

    @ParameterizedTest(name = "with length {0}")
    @ValueSource(ints = {EXPECTED_NUMBER_OF_ARGUMENTS-1, EXPECTED_NUMBER_OF_ARGUMENTS+1})
    public void filterFeedbacks_Should_throwException_WhenInvalidArgumentsCounts(int parametersCount) {
        List<String> parameters = new ArrayList<>();

        for (int i = 1; i <= parametersCount; i++) {
            parameters.add("1");
        }

        Assertions.assertThrows(InvalidNumberOfArguments.class, () -> filter.execute(parameters));
    }

    @Test
    public void filterFeedbacks_Should_ReturnCollection_WithStatusFilter() {
        List<String> parameters = List.of("Status:New");

        Feedback firstFeedback = repository.findFeedback(3);
        Feedback secondFeedback = repository.findFeedback(8);
        Feedback thirdFeedback = repository.findFeedback(9);
        Feedback fourthFeedback = repository.findFeedback(14);
        Feedback fifthFeedback = repository.findFeedback(15);

        StringBuilder output = new StringBuilder();
        output.append(String.format("%s ID: %d - Title: %s - Rating: %s - Status: %s - Comments: %d",
                firstFeedback.getClass().getSimpleName().replace("Impl", ""),
                firstFeedback.getID(), firstFeedback.getTitle(), firstFeedback.getRating(),
                firstFeedback.getStatus(), firstFeedback.getComments().size())).append(System.lineSeparator());

        output.append(String.format("%s ID: %d - Title: %s - Rating: %s - Status: %s - Comments: %d",
                secondFeedback.getClass().getSimpleName().replace("Impl", ""),
                secondFeedback.getID(), secondFeedback.getTitle(), secondFeedback.getRating(),
                secondFeedback.getStatus(), secondFeedback.getComments().size())).append(System.lineSeparator());

        output.append(String.format("%s ID: %d - Title: %s - Rating: %s - Status: %s - Comments: %d",
                thirdFeedback.getClass().getSimpleName().replace("Impl", ""),
                thirdFeedback.getID(), thirdFeedback.getTitle(), thirdFeedback.getRating(),
                thirdFeedback.getStatus(), thirdFeedback.getComments().size())).append(System.lineSeparator());

        output.append(String.format("%s ID: %d - Title: %s - Rating: %s - Status: %s - Comments: %d",
                fourthFeedback.getClass().getSimpleName().replace("Impl", ""),
                fourthFeedback.getID(), fourthFeedback.getTitle(), fourthFeedback.getRating(),
                fourthFeedback.getStatus(), fourthFeedback.getComments().size())).append(System.lineSeparator());

        output.append(String.format("%s ID: %d - Title: %s - Rating: %s - Status: %s - Comments: %d",
                fifthFeedback.getClass().getSimpleName().replace("Impl", ""),
                fifthFeedback.getID(), fifthFeedback.getTitle(), fifthFeedback.getRating(),
                fifthFeedback.getStatus(), fifthFeedback.getComments().size()));


        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output.toString(), filter.execute(parameters));
    }

    @Test
    public void filterFeedbacks_Should_ReturnEmptyCollection_WhenNoResults() {
        List<String> parameters = List.of("Status:Done");

        String output = String.format(NO_ITEMS_TO_DISPLAY, "feedbacks");

        Assertions.assertDoesNotThrow(() -> filter.execute(parameters));
        Assertions.assertEquals(output, filter.execute(parameters));
    }


    @Test
    public void filterFeedbacks_Should_ThrowException_WhenInvalidFilter() {
        List<String> parameters = List.of("FakeCriteria:Adsadasdsa");

        Assertions.assertThrows(InvalidUserInput.class, () -> filter.execute(parameters));
    }

    @Test
    public void filterFeedbacks_Should_ThrowException_WhenInvalidValue() {
        List<String> parameters = List.of("Status:Alabalala");

        Assertions.assertThrows(InvalidUserInput.class, () -> filter.execute(parameters));
    }

}
