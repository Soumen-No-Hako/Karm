package artim.nemuos.karm.controller;

import artim.nemuos.karm.model.Project;
import artim.nemuos.karm.model.WorkItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.IntStream;

@Controller
public class KarmController {

    static ArrayList<Project> projects;
    static ArrayList<WorkItem> workItems;

    static {
        Gson objectMapper = new Gson();
        if(!new File("projects.json").exists()){
            try {
                new File("projects.json").createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String json = null;
        try {
            json = Files.readString(Path.of("projects.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(json.length()!=0) projects = objectMapper.fromJson(json, new TypeToken<ArrayList<Project>>() {}.getType());
    }

    public void saveProjects() throws IOException {
        Gson objectMapper = new Gson();
        File projectFile = new File("projects.json");
        String jsonString = objectMapper.toJson(projects);
        // Write jsonString to projectFile
        Files.writeString(Path.of("projects.json"), jsonString);
    }
    @GetMapping("/")
    public String homepage(Model model) throws IOException {
        model.addAttribute("projects", projects);
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
        // Show the desired project based on projectId and redirect to project page
        model.addAttribute("project", projects.stream().filter(p->p.getProjectId().equals(projectId)).findFirst().get());
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
        projects.get(projectIndex).setWorkItemCount(projects.get(projectIndex).getWorkItemCount()+1);
        ArrayList<WorkItem> wList = (ArrayList<WorkItem>) projects.get(projectIndex).getWorkItems();
        wList.add(w);
        projects.get(projectIndex).setWorkItems(wList);
        projects.get(projectIndex).setLastModifiedOn(java.time.Instant.now().toString());
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
    Project p = projects.stream().filter(prjt -> prjt.getProjectId().equals(workItemId.split("-")[0])).findFirst().get();
    WorkItem w = p.getWorkItems().stream().filter(wit -> wit.getWorkItemId().equals(workItemId)).findFirst().get();
    model.addAttribute("workitem", w);
    return "workItemView";
    }
    public void dltWorkitem(String workItemId){

    }
    public void updProjectStatus(String projectId, String projectStatus){

    }
    public void updWorkitemStatus(String workItemId, String workItemStatus){

    }
}
