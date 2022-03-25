package main.core;

import main.commands.contracts.Command;
import main.core.contracts.CommandFactory;
import main.core.contracts.TaskManagementSystemEngine;
import main.core.contracts.TaskManagementSystemRepository;
import main.exceptions.InvalidNumberOfArguments;
import main.exceptions.InvalidUserInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskManagementSystemEngineImpl implements TaskManagementSystemEngine {

    private static final String TERMINATION_COMMAND = "Exit";
    private static final String EMPTY_COMMAND_ERROR = "Command cannot be empty.";
    private static final String COMMAND_AND_PARAMETERS_SPLIT_SYMBOL = " ";
    private static final String REPORT_SEPARATOR = "####################";

    private final CommandFactory commandFactory;
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public TaskManagementSystemEngineImpl() {
        this.commandFactory = new CommandFactoryImpl();
        this.taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String inputLine = scanner.nextLine();
                if (inputLine.isBlank()) {
                    print(EMPTY_COMMAND_ERROR);
                    continue;
                }
                if (inputLine.equalsIgnoreCase(TERMINATION_COMMAND)) {
                    break;
                }
                processCommand(inputLine);
            } catch (InvalidUserInput | InvalidNumberOfArguments ex) {
                if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
                    print(ex.getMessage());
                } else {
                    print(ex.toString());
                }
            }
        }
    }

    private void processCommand(String inputLine) {
        String commandName = extractCommandName(inputLine);
        List<String> parameters = extractParameters(inputLine);
        Command command = commandFactory.createCommand(commandName, taskManagementSystemRepository);
        String executionResult = command.execute(parameters);
        print(executionResult);
    }

    /**
     * Receives a full line and extracts the command to be executed from it.
     * For example, if the input line is "FilterBy Assignee John", the method will return "FilterBy".
     *
     * @param inputLine A complete input line
     * @return The name of the command to be executed
     */
    private String extractCommandName(String inputLine) {
        return inputLine.split(" ")[0];
    }

    /**
     * Receives a full line and extracts the parameters that are needed for the command to execute.
     * For example, if the input line is "AddUserToTeam {John} {BestTeam}",
     * the method will return a list of ["John", "BestTeam"].
     *
     * @param inputLine A complete input line
     * @return A list of the parameters needed to execute the command
     */
    private List<String> extractParameters(String inputLine) {
        if (!inputLine.contains(COMMAND_AND_PARAMETERS_SPLIT_SYMBOL)) {
            return new ArrayList<>();
        }

        String regex = "\\{[^}]*}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputLine.substring(inputLine.indexOf(COMMAND_AND_PARAMETERS_SPLIT_SYMBOL)));

        List<String> commandParameters = new ArrayList<>();
        while (matcher.find()) {
            String parameter = matcher.group();
            commandParameters.add(parameter.substring(1, parameter.length() - 1));
        }

        return commandParameters;
    }

    private void print(String result) {
        System.out.println(result);
        System.out.println(REPORT_SEPARATOR);
    }
}
