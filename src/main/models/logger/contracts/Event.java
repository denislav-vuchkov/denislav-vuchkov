package main.models.logger.contracts;

public interface Event {

    long getOccurrence();

    public String getTeam();

    String toString();

}