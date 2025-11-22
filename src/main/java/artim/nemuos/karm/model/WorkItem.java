package artim.nemuos.karm.model;

public class WorkItem {
    private String workItemId;
    private String workItemTitle;
    private String workItemDescription;
    private String status;
    private String createdOn;
    private String lastModifiedOn;

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }

    public String getWorkItemTitle() {
        return workItemTitle;
    }

    public void setWorkItemTitle(String workItemTitle) {
        this.workItemTitle = workItemTitle;
    }

    public String getWorkItemDescription() {
        return workItemDescription;
    }

    public void setWorkItemDescription(String workItemDescription) {
        this.workItemDescription = workItemDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public WorkItem(String workItemId, String workItemTitle, String workItemDescription, String status, String createdOn, String lastModifiedOn) {
        this.workItemId = workItemId;
        this.workItemTitle = workItemTitle;
        this.workItemDescription = workItemDescription;
        this.status = status;
        this.createdOn = createdOn;
        this.lastModifiedOn = lastModifiedOn;
    }

    public WorkItem(String workItemId, String workItemTitle, String workItemDescription) {
        this.workItemId = workItemId;
        this.workItemTitle = workItemTitle;
        this.workItemDescription = workItemDescription;
        this.createdOn = java.time.Instant.now().toString();
        this.lastModifiedOn = java.time.Instant.now().toString();
        this.status = "TO-DO";
    }
public WorkItem(){

}
}
