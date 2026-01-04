package artim.nemuos.karm.controller;

import artim.nemuos.karm.KarmApp;
import artim.nemuos.karm.model.Project;
import artim.nemuos.karm.model.WorkItem;
import artim.nemuos.karm.utility.KarmSorter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class KarmController {

    static ArrayList<Project> projects;
    static ArrayList<WorkItem> workItems;
    @Autowired
    public KarmSorter karmSorter;

    static {
        projects = (ArrayList<Project>) KarmApp.projects;
        workItems = (ArrayList<WorkItem>) KarmApp.workItems;
    }

    public static void saveProjects() throws IOException {
        Gson objectMapper = new Gson();
        File projectFile = new File(KarmApp.karmDataLoc+"projects.json");
        String jsonString = objectMapper.toJson(projects);
        // Write jsonString to projectFile
        Files.writeString(Path.of(KarmApp.karmDataLoc+"projects.json"), jsonString);

        File workItemFile = new File("workitems.json");
        String jsonStringWI = objectMapper.toJson(workItems);
        // Write json to workItemsFile
        Files.writeString(Path.of(KarmApp.karmDataLoc+"workitems.json"), jsonStringWI);
    }
    @GetMapping("/")
    public String homepage(Model model, @RequestParam(required = false) String sortParam) throws IOException {
        ArrayList<Project> projectsCopy = (ArrayList<Project>) projects.clone();
        if (sortParam == null) model.addAttribute("projects", projects);
         else
        {
            switch (sortParam) {
                case "workitemcount":
                    projectsCopy.sort((p1, p2) -> karmSorter.compareProjectWorkItemCount(p1.getWorkitemContainerSize(), p2.getWorkitemContainerSize()));
                    break;
                case "lastmodified":
                    projectsCopy.sort((p1, p2) -> karmSorter.compareProjectUpdatedDates(p1.getLastModifiedOn(), p2.getLastModifiedOn()));
                    break;
                case "status":
                    projectsCopy.sort((p1, p2) -> karmSorter.compareStatus(p1.getProjectStatus(), p2.getProjectStatus()));
                    break;
                default:
                    // No sorting
                    break;

            }
            model.addAttribute("projects", projectsCopy);
        }

        boolean EmptyProject = false;
        if(projects == null){
            EmptyProject = true;
        }
        model.addAttribute("EmptyProject", EmptyProject);
        return "index";
    }

    @GetMapping("/project/new")
    public String crtProjectPage(@ModelAttribute Project project, Model model){
        model.addAttribute("project", project);
        return "newProject";
    }
    @PostMapping("/project/new")
    public RedirectView crtProject(@ModelAttribute Project project, Model model) throws IOException {
        System.out.println("Creating project: " + project.getProjectName()+", "+project.getProjectDescription()+" ID: "+project.getProjectId());
        Project p = new Project(project.getProjectId(), project.getProjectName(), project.getProjectDescription());
        System.out.println(p.toString());
        if(projects==null) {
            projects = new ArrayList<>();
        }
        projects.add(p);
        saveProjects();
        model.addAttribute("projects", projects);
        return new RedirectView("/");
    }
    @PatchMapping("/project/edit/{id}")
    public void updProject(@PathVariable("id") String projectId, @ModelAttribute Project project){

    }
    @GetMapping("/project/{projectId}")
    public String showProject(String projectId, Model model){

        Project project = projects.stream().filter(p -> p.getProjectId().equals(projectId)).findFirst().get();
        // Show the desired project based on projectId and redirect to project page
        model.addAttribute("project", project);
        return "projectView";
    }


    public void dltProject(String projectId, String projectName, String projectDescription){
        //dlt the project based on projectid
    }
    @GetMapping("/workitem/new/{projectId}")
    public String crtWorkitemPage(@PathVariable String projectId, @ModelAttribute WorkItem workitem, Model model) {
        model.addAttribute("workitem", workitem);
        model.addAttribute("projectId", projectId);
        return "newWorkItem";
    }
    @PostMapping("/workitem/new/{projectId}")
    public RedirectView crtWorkitem(@PathVariable String projectId, @ModelAttribute("workitem") WorkItem workItem, Model model) throws IOException {

        // intStream to simply process int values other was another logic would have been needed to convert object index to int
        int projectIndex = IntStream.range(0, projects.size()).filter((i-> projects.get(i).getProjectId().equals(projectId))).findFirst().getAsInt();


        WorkItem w = new WorkItem(projectId+"-"+projects.get(projectIndex).getWorkItemCount(), workItem.getWorkItemTitle(), workItem.getWorkItemDescription());
        if(workItems==null) {
            workItems = new ArrayList<>();
        }
        workItems.add(w);
        projects.get(projectIndex).setWorkItemCount(projects.get(projectIndex).getWorkItemCount()+1);
        ArrayList<String> prjWorkItemIds = (ArrayList<String>) projects.get(projectIndex).getWorkItemIds();
        prjWorkItemIds.add(w.getWorkItemId());
        projects.get(projectIndex).setWorkItemIds(prjWorkItemIds);
        projects.get(projectIndex).setLastModifiedOn(java.time.Instant.now().toString());
        projects.get(projectIndex).setWorkitemContainerSize(projects.get(projectIndex).getWorkItemIds().size());
        saveProjects();
        model.addAttribute("projects", projects);
        return new RedirectView("/");
    }
    @PatchMapping("/workitem/edit/{id}")
    public void updWorkitem(@PathVariable("id") String workItemId, @ModelAttribute WorkItem workItem){
    int workItemIndex = projects.stream().mapToInt(p -> p.getWorkItems().indexOf(
            p.getWorkItems().stream().filter(wi -> wi.getWorkItemId().equals(workItemId)).findFirst().orElse(null)
    )).filter(index -> index != -1).findFirst().orElse(-1);
    }
    @GetMapping("/workitem/{workItemId}")
    public String showWorkitem(@PathVariable String workItemId, Model model){
    WorkItem w = workItems.stream().filter(wit -> wit.getWorkItemId().equals(workItemId)).findFirst().get();
    model.addAttribute("workitem", w);
    return "workItemView";
    }
    public void dltWorkitem(String workItemId){

    }
    @PostMapping("/project/status")
    public RedirectView updProjectStatus(@ModelAttribute Project p) throws IOException {
        System.out.println("Updating project ID: " + p.getProjectId() + " to status: " + p.getProjectStatus());
        int prjtIndex = projects.stream()
                .mapToInt(pp -> pp.getProjectId().equals(p.getProjectId()) ? projects.indexOf(pp) : -1)
                .filter(index -> index != -1)
                .findFirst()
                .orElse(-1);
        projects.get(prjtIndex).setProjectStatus(p.getProjectStatus().toUpperCase(Locale.ROOT));

        saveProjects(); // Save to JSON
        return new RedirectView("/");
    }
    @PostMapping("/workitem/status")
    public RedirectView updateWorkItemStatus(@ModelAttribute WorkItem item) throws IOException {
            // Find work item inside the project
        System.out.println("Updating work item ID: " + item.getWorkItemId() + " to status: " + item.getStatus());
        int workitemIndex = workItems.stream()
                .mapToInt(wi -> wi.getWorkItemId().equals(item.getWorkItemId()) ? workItems.indexOf(wi) : -1)
                .filter(index -> index != -1)
                .findFirst()
                .orElse(-1);
        workItems.get(workitemIndex).setStatus(item.getStatus());
        workItems.get(workitemIndex).setLastModifiedOn(java.time.Instant.now().toString());
            saveProjects(); // Save to JSON
        return new RedirectView("/");
    }
    @GetMapping("/workitems/{projectId}")
    public String getWorkItemsFragment(@PathVariable String projectId, Model model, @RequestParam(required = false) String sortParam) {
        // 1. Find the project
        Project project = projects.stream()
                .filter(p -> p.getProjectId().equals(projectId))
                .findFirst()
                .orElse(null);

        // 2. Add data to model
        if (project != null) {
            ArrayList<WorkItem> workItemsCopy = (ArrayList<WorkItem>) workItems.stream().filter(wi -> wi.getWorkItemId().startsWith(projectId)).collect(Collectors.toList());
            if (sortParam != null) {
                switch (sortParam) {
                    case "status":
                        workItemsCopy.sort((w1, w2) -> karmSorter.compareWorkItemsByStatus(w1, w2));
                        break;
                    case "lastmodified":
                        workItemsCopy.sort((w1, w2) -> karmSorter.compareWorkItemDates(w1, w2));
                        break;
                    case "notes":
                        workItemsCopy.sort((w1, w2) -> karmSorter.compareWorkItemsByNotes(w1, w2));
                        break;
                    default:
                        // No sorting
                        break;
                }
                model.addAttribute("workItems", workItemsCopy);
            } else
                model.addAttribute("workItems", workItems.stream().filter(wi -> wi.getWorkItemId().startsWith(projectId)).toList());
            model.addAttribute("projectId", projectId); // Needed for the hidden input in form
            model.addAttribute("sortParam", sortParam);
        } else {
            model.addAttribute("workItems", new ArrayList<>());
        }

        // 3. Return specifically the fragment, not the whole file
        // Syntax: "filename :: fragmentName"
        return "fragments/workItemList :: itemList";
    }
}
