package Task_Management_System.commands.contracts;

import java.util.List;

public interface Command {

    String execute(List<String> parameters);

}
