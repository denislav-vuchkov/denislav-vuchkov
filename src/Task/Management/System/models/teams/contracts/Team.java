package Task.Management.System.models.teams.contracts;

import java.util.List;

public interface Team extends Loggable,Nameable {

    List<Board> getBoards();

    void addBoard(Board board);

    void removeBoard(Board board);

    List<Member> getMembers();

    void addMember(Member member);

    void removeMember(Member member);

}
