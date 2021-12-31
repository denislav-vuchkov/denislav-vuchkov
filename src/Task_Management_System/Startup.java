package Task_Management_System;

import Task_Management_System.core.TaskManagementSystemEngineImpl;

public class Startup {

    public static void main(String[] args) {
        TaskManagementSystemEngineImpl engine = new TaskManagementSystemEngineImpl();
        engine.start();
    }

}
