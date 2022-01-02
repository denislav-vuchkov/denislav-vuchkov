package Task_Management_System.models.logger.contracts;

public interface Event {

    long getOccurrence();

    public String getTeam();

    String toString();

}