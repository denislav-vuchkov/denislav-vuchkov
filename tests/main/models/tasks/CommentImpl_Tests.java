package main.models.tasks;

import main.exceptions.InvalidUserInput;
import main.models.tasks.contracts.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommentImpl_Tests {

    Comment myComment;

    @BeforeEach
    public void setup() {

        myComment = new CommentImpl(
                "This test better work.",
                "Denis");
    }

    @Test
    public void constructor_initiatesObjectCorrectly_withValidParameters() {

        Assertions.assertEquals("This test better work.", myComment.getContent());
        Assertions.assertEquals("Denis", myComment.getAuthor());

    }

    @Test
    public void constructor_throwsException_whenContentIsInvalid() {

        Assertions.assertThrows(InvalidUserInput.class, () -> new CommentImpl(
                "No",
                "Rick"));

        Assertions.assertThrows(InvalidUserInput.class, () -> new CommentImpl(
                "Jeez".repeat(100),
                "Morty"));
    }

}
