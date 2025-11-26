package artim.nemuos.karm.model;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String projectId;
    private String projectName;
    private String projectDescription;
    private String createdOn;
    private String lastModifiedOn;
    private String owner;
    private List<WorkItem> workItems;

    public Project() {

    }

    public List<String> getWorkItemIds() {
        return workItemIds;
    }

    public void setWorkItemIds(List<String> workItemIds) {
        this.workItemIds = workItemIds;
    }

    private List<String> workItemIds;
    private int workItemCount;
    private String projectStatus;

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<WorkItem> getWorkItems() {
        return workItems;
    }

    public void setWorkItems(List<WorkItem> workItems) {
        this.workItems = workItems;
    }

    public int getWorkItemCount() {
        return workItemCount;
    }

    public void setWorkItemCount(int workItemCount) {
        this.workItemCount = workItemCount;
    }

    public Project(String projectId, String projectName, String projectDescription) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.createdOn = java.time.Instant.now().toString();
        this.lastModifiedOn = java.time.Instant.now().toString();
        this.owner = "Nemuos";
        this.workItemCount = 1;
        this.projectStatus = "NEW";
        this.workItems = new ArrayList<>();
        this.workItemIds = new ArrayList<>();
    }
    private void addWorkItem(WorkItem workItem){
        this.workItems.add(workItem);
        this.workItemCount = this.workItems.size();
    }
    private void createWorkItem(String workItemId, String workItemTitle, String workItemDescription){
        WorkItem workItem = new WorkItem(workItemId, workItemTitle, workItemDescription);
        this.workItems.add(workItem);
        this.workItemCount += 1;
    }

    @Override
    public String toString() {
        return "Project\n\t{" +
                "projectId='" + projectId + '\'' +
                ",\n \tprojectName='" + projectName + '\'' +
                ",\n \tprojectDescription='" + projectDescription + '\'' +
                ",\n \tcreatedOn='" + createdOn + '\'' +
                ",\n \tlastModifiedOn='" + lastModifiedOn + '\'' +
                ",\n \towner='" + owner + '\'' +
                ",\n \tworkItems=" + workItems +
                ",\n \tworkItemCount=" + workItemCount +
                ",\n \tprojectStatus='" + projectStatus + '\'' +
                "\n}";
    }
}
