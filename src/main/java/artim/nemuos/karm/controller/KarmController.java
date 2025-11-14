package artim.nemuos.karm.controller;

import artim.nemuos.karm.model.Project;
import artim.nemuos.karm.model.WorkItem;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.IntStream;

@Controller
public class KarmController {

    List<Project> projects;

    public void loadProjects() throws FileNotFoundException {
        Gson objectMapper = new Gson();
        FileInputStream projectFile = new FileInputStream("projects.json");
        projects = List.of( objectMapper.fromJson(  projectFile.toString(), Project[].class) );
    }
    public void saveProjects() {
        Gson objectMapper = new Gson();
        File projectFile = new File("projects.json");
        String jsonString = objectMapper.toJson(projects);
        // Write jsonString to projectFile
    }
    @PostMapping("/createProject")
    public String createProject(String projectId, String projectName, String projectDescription, Model model) {
        return "redirect:/index";
    }
    @GetMapping("/home")
    public String homepage(Model model) {
        model.addAttribute("projects", projects);
        return "redirect:/index";
    }
    public void crtProject(String projectId, String projectName, String projectDescription){
        Project p = new Project(projectId, projectName, projectDescription);

    }
    public void updProject(String projectId, String projectName, String projectDescription){

    }
    public String showProject(String projectId, Model model){
        // Show the desired project based on projectId and redirect to project page
        model.addAttribute("project", projects.stream().filter(p->p.getProjectId().equals(projectId)).findFirst().get());
        return "projectView";
    }
    public void dltProject(String projectId, String projectName, String projectDescription){
        //dlt the project based on projectid
    }
    public String crtWorkitem(String projectId, String workItemName, String workItemDesc, Model model){
        int projectIndex = IntStream.range(0, projects.size()).filter((i-> projects.get(i).getProjectId().equals(projectId))).findFirst().getAsInt();
        WorkItem w = new WorkItem(projectId+"-"+projects.get(projectIndex).getWorkItemCount(), workItemName, workItemDesc);
        projects.get(projectIndex).setWorkItemCount(projects.get(projectIndex).getWorkItemCount()+1);
        projects.get(projectIndex).getWorkItems().add(w);
        projects.get(projectIndex).setLastModifiedOn(java.time.Instant.now().toString());
        model.addAttribute("projects", projects);
        return "index";
    }
    public void updWorkitem(String workItemId, String projectName, String projectDescription){

    }
    public void showWorkitem(String workItemId){


    }
    public void dltWorkitem(String workItemId){

    }
    public void updProjectStatus(String projectId, String projectStatus){

    }
    public void updWorkitemStatus(String workItemId, String workItemStatus){

    }
}
