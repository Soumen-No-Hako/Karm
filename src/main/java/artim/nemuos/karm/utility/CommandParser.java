package artim.nemuos.karm.utility;

import artim.nemuos.karm.model.Project;
import artim.nemuos.karm.model.WorkItem;
import org.springframework.boot.SpringApplication;
import artim.nemuos.karm.KarmApp;

import java.util.List;

public class CommandParser {
    public void processCommand(String[] args, List<Project> projects, List<WorkItem> workItems) {
        for (int i = 0; i < args.length; i++) {
            if(args[i].equals("--help") || args[i].equals("-h")){
                System.out.println("\n\nKarm Application Command Line Options:\n");
                System.out.println("================================================\n");
                printHelpCommandDetails();
            } else if(args[i].equals("run")) {
                System.out.println("Karm Application Starting...");
                // Here you would add code to start the application
                SpringApplication.run(KarmApp.class, args);
            }
            else if (args[i].equals("projects") || args[i].equals("-p")){
                System.out.println("Listing Projects:");
                System.out.println("===================");
                System.out.println("Total Projects: " + projects.size());
                System.out.println("\n\tProject Id\t|\t\tTitle\t\t|\tDescription\n");
                projects.stream().forEach(project -> {
                    System.out.println(project.getProjectId() + "\t|\t" + project.getProjectName() + "\t|\t" + project.getProjectDescription());
                });
            }
            else if( args[i].equals("workitems") || args[i].equals("-w")){
                System.out.println("Listing Work Items:");
                System.out.println("===================");
                System.out.println("Total Work Items: " + workItems.size());
                System.out.println("\n\tWork Item Id\t|\t\tTitle\t\t|\tDescription\n");
                workItems.stream().forEach(workItem -> {
                    System.out.println(workItem.getWorkItemId() + "\t|\t" + workItem.getWorkItemTitle() + "\t|\t" + workItem.getWorkItemDescription());
                });
            }
            else
                System.out.println("Unknown Argument: " + args[i]);
        }
    }

    private void printHelpCommandDetails() {
        System.out.println("--help, -h\t\tShow help information about Karm Application commands\n" +
                "===============================================================================\n" +
                "For specific command details, use: [command] --help or [command] -h\n");
        System.out.println("run, -r\t\t\t\tStart the Karm Application as a Springboot application\n");
        System.out.println("projects, -p\t\tManage projects (add, list, delete, update) via command line\n");
        System.out.println("workitems, -w\t\tManage work items (add, list, delete, update) via command line\n");
        System.out.println("There will be more soon..........");

    }
}
