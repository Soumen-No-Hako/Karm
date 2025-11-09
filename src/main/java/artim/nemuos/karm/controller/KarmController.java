package artim.nemuos.karm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class KarmController {
    @PostMapping("/createProject")
    public void createProject(String projectId, String projectName, String projectDescription) {

    }
    @GetMapping("/home")
    public String homepage(Model model) {
        return "index";
    }
}
