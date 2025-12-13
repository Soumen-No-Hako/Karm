package artim.nemuos.karm.controller;

import artim.nemuos.karm.model.WorkItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
public class UserInputController {

    @GetMapping("/workitem/{workItemId}/comment/new")
    public String newCommentForm(@PathVariable String workItemId, Model model) {
        model.addAttribute("workItemId", workItemId);
        model.addAttribute("comment", "");
        return "newComment";
    }

    @PostMapping("/workitem/comment/new")
    public RedirectView postComment(@RequestParam String workItemId, @RequestParam String comment, Model model) {
        try {
            WorkItem w = KarmController.workItems.stream().filter(wi -> wi.getWorkItemId().equals(workItemId)).findFirst().orElse(null);
            if (w != null) {
                w.addComment(comment);
            }
            KarmController.saveProjects();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new RedirectView("/workitem/" + workItemId);
    }
}
