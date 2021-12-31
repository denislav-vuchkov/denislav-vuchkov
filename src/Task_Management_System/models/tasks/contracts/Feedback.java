package Task_Management_System.models.tasks.contracts;

public interface Feedback extends Task {

    Integer getRating();

    void setRating(int rating);

}
