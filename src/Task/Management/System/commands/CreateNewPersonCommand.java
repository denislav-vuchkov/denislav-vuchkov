package Task.Management.System.commands;

import Task.Management.System.core.contracts.TaskManagementSystemRepository;
import Task.Management.System.models.teams.MemberImpl;
import Task.Management.System.models.teams.contracts.Member;
import Task.Management.System.utils.ValidationHelpers;

import java.util.List;

public class CreateNewPersonCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String MEMBER_ADDED_SUCCESSFULLY = "New member %s created successfully.";

    public CreateNewPersonCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters,EXPECTED_NUMBER_OF_ARGUMENTS);
        String name = parameters.get(0);
        Member member = new MemberImpl(name);
        getTaskManagementSystemRepository().addNewMember(member);
        return String.format(MEMBER_ADDED_SUCCESSFULLY, member.getName());
    }
}
