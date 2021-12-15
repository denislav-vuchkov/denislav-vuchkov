package Task.Management.System;

import Task.Management.System.core.TaskManagementSystemEngineImpl;

public class Startup {

    public static void main(String[] args) {
        TaskManagementSystemEngineImpl engine = new TaskManagementSystemEngineImpl();
        engine.start();
    }

}
