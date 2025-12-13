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
                System.out.println(wrapInColor("Project ID", 33)+" "+wrapInColor("Project Name", 33)+" "+wrapInColor("Project Description", 33)+" "+wrapCountInColor("Count", 33)+" "+wrapStatusInColor("Status", 33));
                projects.stream().forEach(project -> {
                    System.out.println(wrapInColor(project.getProjectId(), 34) +" "+ wrapInColor(project.getProjectName(), 34) +" "+ wrapInColor(project.getProjectDescription().substring(0, Math.min(project.getProjectDescription().length(), MAX_WIDTH-3))+"...", 34)+" "+
                            wrapCountInColor(project.getWorkItemCount())+" "+wrapStatusInColor(project.getProjectStatus()));
                });
            }
            else if( args[i].equals("workitems") || args[i].equals("-w")){
                System.out.println("Listing Work Items:");
                System.out.println("===================");
                System.out.println("Total Work Items: " + workItems.size());
                System.out.println(wrapInColor("Work Item ID", 33)+" "+wrapInColor("Title", 33)+" "+wrapInColor("Description", 33)+" "+wrapStatusInColor("Status", 33));
                workItems.stream().forEach(workItem -> {
                    System.out.println(wrapInColor(workItem.getWorkItemId(), 34)+" "+wrapInColor(workItem.getWorkItemTitle(), 34)+" "+wrapInColor(workItem.getWorkItemDescription().substring(0, Math.min(workItem.getWorkItemDescription().length(), MAX_WIDTH-3))+"...", 34)+" "+
                            wrapStatusInColor(workItem.getStatus()));
                });

            }
            else
                System.out.println("Unknown Argument: " + args[i]);
        }
    }

    private String wrapStatusInColor(String status, int i) {
        final String ANSI_PREFIX = "\u001B[48;5;";
        final String ANSI_RESET = "\u001B[0m";
        int colorCode = i;
        String data = status;
        int spaces = (MAX_STATUS_FIELD-data.length())/2;
        return ANSI_PREFIX + colorCode + "m"+ " ".repeat(spaces) + data + " ".repeat(MAX_STATUS_FIELD - data.length() - spaces) + ANSI_RESET;
    }

    private String wrapCountInColor(String count, int i) {
        final String ANSI_PREFIX = "\u001B[48;5;";
        final String ANSI_RESET = "\u001B[0m";
        int colorCode = i;
        String data = count;
        int spaces = (MAX_INT_FIELD-data.length())/2;
        return ANSI_PREFIX + colorCode + "m"+ " ".repeat(spaces) + data + " ".repeat(MAX_INT_FIELD - spaces-data.length()) + ANSI_RESET;
    }

    private String wrapCountInColor(String count) {
        final String ANSI_PREFIX = "\u001B[48;5;";
        final String ANSI_RESET = "\u001B[0m";
        int colorCode = count.equals("Count") ? 34 : 196; // Blue for header, Red for 0
        String data = count;
        int spaces = (MAX_INT_FIELD-data.length())/2;
        return ANSI_PREFIX + colorCode + "m"+ " ".repeat(spaces) + data + " ".repeat(MAX_INT_FIELD - spaces-data.length()) + ANSI_RESET;
    }

    private String wrapStatusInColor(String projectStatus) {
        final String ANSI_PREFIX = "\u001B[48;5;";
        final String ANSI_RESET = "\u001B[0m";
        int colorCode;
        switch (projectStatus.toUpperCase()) {
            case "IN-PROGRESS":
                colorCode = 34; // Light green
                break;
            case "DONE":
                colorCode = 19; // Blue
                break;
            case "CANCELED":
                colorCode = 160; // Red
                break;
            case "TO-DO":
                colorCode = 166; // Orange
                break;
            default:
                colorCode = 91; // Violet for unknown status
        }
        String data = projectStatus;
        int spaces = (MAX_STATUS_FIELD-data.length())/2;
        return ANSI_PREFIX + colorCode + "m"+ " ".repeat(spaces) + data + " ".repeat(MAX_STATUS_FIELD - data.length() - spaces) + ANSI_RESET;
    }

    private String wrapCountInColor(int size) {
        final String ANSI_PREFIX = "\u001B[48;5;";
        final String ANSI_RESET = "\u001B[0m";
        int colorCode = size > 0 ? 34 : 196; // Blue for >0, Red for 0
        String data = String.valueOf(size);
        int spaces = (MAX_INT_FIELD-data.length())/2;
        return ANSI_PREFIX + colorCode + "m"+ " ".repeat(spaces) + data + " ".repeat(MAX_INT_FIELD - spaces-data.length()) + ANSI_RESET;
    }

    int MAX_WIDTH = 50;
    int MAX_INT_FIELD = 5;
    int MAX_STATUS_FIELD = 15;
    private String wrapInColor(String data, int i) {
        final String ANSI_PREFIX = "\u001B[48;5;";
        final String ANSI_RESET = "\u001B[0m";
        int colorCode = i;
        int spaces = (MAX_WIDTH-data.length())/2;// Blue background
        return ANSI_PREFIX + colorCode + "m"+ " ".repeat(spaces) + data + " ".repeat(MAX_WIDTH - data.length() - spaces) + ANSI_RESET;
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
