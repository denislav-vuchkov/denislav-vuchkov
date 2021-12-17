package Task.Management.System.core.contracts;


import Task.Management.System.models.contracts.Task;
import Task.Management.System.models.teams.contracts.Board;
import Task.Management.System.models.teams.contracts.Member;
import Task.Management.System.models.teams.contracts.Team;

import java.util.List;

public interface TaskManagementSystemRepository {

    String MEMBER_ALREADY_EXIST = "Member with this name already exists!";

    List<Team> getTeams();

    List<Board> getBoards();

    List<Member> getMembers();

    List<Task> getTasks();

    void addNewMember(Member member);


}
