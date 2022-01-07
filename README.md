# Task Management System


All commands in this system are accepted in the following format:

CommandName {Parameter 1} {Parameter 2} .....

[Scroll to the bottom for a list of all commands and their possible parameters.]

#Additional functionality on top of requirements

New commands:
- ShowTaskDetails
- ShowTaskActivity
- ShowTaskComments
- AssignTaskToUser - has extra functionality
- FilterAssignableTasks - made to filter by Title and Assignee instead of Status and Assignee
- ShowTeamAcitivity - made to show a user activity specifically in that team


##For all use cases start with:

CreateTeam {Telerik}

CreateUser {Pesho}

AddUserToTeam {Pesho} {Telerik}

CreateUser {Denis}

AddUserToTeam {Denis} {Telerik}

CreateUser {Tisho}

AddUserToTeam {Tisho} {Telerik}

CreateBoard {Alpha} {Telerik}


##Use case 1

Description: One of the developers has noticed a bug in the company's product. He starts the application and goes on to create a new Task for it. He creates a new Bug and gives it the title "The program freezes when the Log In button is clicked." For the description he adds "This needs to be fixed quickly!", he marks the Bug as High priority and gives it Critical severity. Since it is a new bug, it gets the Active status. The developer also assigns it to the senior developer in the team. To be able to fix the bug, the senior developer needs to know how to reproduce it, so the developer who logged the bug adds a list of steps to reproduce: "1. Open the application; 2. Click "Log In"; 3. The application freezes!" The bug is saved to the application and is ready to be fixed.

Commands:

CreateBug {Pesho} {Telerik} {Alpha} {The program freezes when the Log In is clicked!} {This needs to be fixed quickly!} {Open the application; Click "Log In"; The application freezes!} {High} {Critical} {Denis}

ShowTaskDetails {1}

##Use case 3 (continues case 1)

Description: One of the developers has fixed a bug that was assigned to him. He adds a comment to that bug, saying "This one took me a while, but it is fixed now!", and then changes the status of the bug to Done. Just to be sure, he checks the changes history list of the bug and sees that the last entry in the list says, "The status of item with ID 42 was changed from Active to Done."

Commands:

AddCommentToTask {Denis} {1} {This one took me a while, but it is fixed now!}

ShowTaskComments {1}

ChangeBug {Denis} {1} {status} {fixed}

ShowTaskActivity {1}

ShowUserActivity {Denis}

##Use case 2 (continues case 3)

Description: A new developer has joined the team. One of the other developers starts the application and creates a new team member. After that, he adds the new team member to one of the existing teams and assigns all Critical bugs to him.

Commands:

CreateUser {Toshko}

AddUserToTeam {Toshko} {Telerik}

CreateBug {Pesho} {Telerik} {Alpha} {Test Bug - Important} {This needs to be fixed quickly!} {1. Open the application;2. Click "Log In";3. The application freezes!} {High} {Critical} {Tisho}

CreateBug {Pesho} {Telerik} {Alpha} {Test Bug - So-So} {Fix it at some point.} {1. Open the application;2. Click "Log In";3. The application freezes!} {Low} {Major} {Denis}

CreateBug {Pesho} {Telerik} {Alpha} {Test Bug - Important} {This needs to be fixed quickly!} {1. Open the application;2. Click "Log In";3. The application freezes!} {Medium} {Critical} {Denis}

CreateBug {Pesho} {Telerik} {Alpha} {Test Bug - Meh} {This needs to be fixed last!} {1. Open the application;2. Click "Log In";3. The application freezes!} {Low} {Minor} {Denis}

CreateBug {Pesho} {Telerik} {Alpha} {Test Bug - Important} {This needs to be fixed quickly!} {1. Open the application;2. Click "Log In";3. The application freezes!} {High} {Critical} {Tisho}

ShowAllTasks

SortBugs {Severity}

AssignTask {Pesho} {1;2;4;6} {Toshko}

FilterBugs {Assignee:Tosh}

ShowUserActivity {Pesho}

ShowUserActivity {Toshko}

ShowTaskActivity {1}



----------------------------------------------------------------------------------------------------
COMMANDS LISTS:
----------------------------------------------------------------------------------------------------

ShowAllTeams

ShowAllUsers

ShowAllTasks

ShowTeamUsers		{TEAM_NAME}

ShowTeamBoards		{TEAM_NAME}

ShowTaskComments	{TASK_ID}

ShowTaskDetails		{TASK_ID}

ShowTeamActivity	{TEAM_NAME}

ShowUserActivity	{USER_NAME}

ShowTaskActivity	{TASK_ID}

ShowBoardActivity	{TEAM_NAME}		{BOARD_NAME}

CreateTeam			{TEAM_NAME}

CreateUser			{USER_NAME}

CreateBoard			{BOARD_NAME}	{TEAM_NAME}

AddUserToTeam		{USER_NAME}		{TEAM_NAME}

AssignTask			{AUTHOR} 		{TASK_ID}	{ASSIGNEE}

CreateBug			{AUTHOR}	{TEAM_NAME}	{BOARD_NAME}	{TITLE}	{DESCRIPTION}	{STEPS;SPLIT;LIKE;SO}	{PRIORITY} {SEVERITY} {ASSIGNEE}

CreateFeedback		{AUTHOR}	{TEAM_NAME}	{BOARD_NAME}	{TITLE}	{DESCRIPTION}	{RATING}

CreateStory			{AUTHOR}	{TEAM_NAME}	{BOARD_NAME}	{TITLE}	{DESCRIPTION}	{PRIORITY}					{SIZE} 		{ASSIGNEE}

ChangeBug			{AUTHOR}	{TASK_ID}	{PRIORITY/SEVERITY/STATUS}				{NEW_VALUE}

ChangeStory         {AUTHOR}	{TASK_ID}	{RATING/STATUS}							{NEW_VALUE}

ChangeFeedback      {AUTHOR}	{TASK_ID}	{PRIORITY/SIZE/STATUS}					{NEW_VALUE}

AddCommentToTask	{AUTHOR}	{TASK_ID}	{COMMENT_CONTENT}

FilterAllTasks						{TITLE:VALUE}

FilterAssignableTasks				{TITLE:VALUE}				{ASSIGNEE:VALUE}

FilterBugs							{STATUS:VALUE}				{ASSIGNEE:VALUE}

FilterFeedbacks						{STATUS:VALUE}

FilterStories						{STATUS:VALUE}				{ASSIGNEE:VALUE}

SortAllTasks

SortAssignableTasks

SortBugs							{TITLE/PRIORITY/SEVERITY}

SortFeedbacks						{TITLE/RATING}

SortStories							{TITLE/PRIORITY/SIZE}
