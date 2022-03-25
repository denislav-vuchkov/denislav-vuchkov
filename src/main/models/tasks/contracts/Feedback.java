package main.models.tasks.contracts;

public interface Feedback extends Task {

    Integer getRating();

    void setRating(int rating);

}
