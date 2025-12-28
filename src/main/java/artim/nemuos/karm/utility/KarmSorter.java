package artim.nemuos.karm.utility;

import artim.nemuos.karm.model.WorkItem;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class KarmSorter {
    HashMap<String, Integer> karmStatusMap;
    public KarmSorter() {
        karmStatusMap = new HashMap<>();
        karmStatusMap.put("TO-DO", 5);
        karmStatusMap.put("NEW", 5);
        karmStatusMap.put("IN-PROGRESS", 4);
        karmStatusMap.put("DONE", 3);
        karmStatusMap.put("CANCELLED", 2);
        karmStatusMap.put("CLOSED", 1);
        karmStatusMap.put("URGENT", 10);
        System.out.println("karmStatusMap initialized: " + karmStatusMap);
    }
    //Sorting logic
    public int compareStatus(String status1, String status2) {
        Integer val1 = karmStatusMap.getOrDefault(status1.toUpperCase(), 0);
        Integer val2 = karmStatusMap.getOrDefault(status2.toUpperCase(), 0);
        return val2 - val1; //descending order
    }
    //method to take 2 workitems in input and returns which one is updated recently
    public int compareWorkItemDates(WorkItem w1, WorkItem w2) {
        //assuming date format is YYYY-MM-DD
        return w2.getLastModifiedOn().compareTo(w1.getLastModifiedOn()); //descending order
    }
    //method to comapre 2 projects by their work item count
    public int compareProjectWorkItemCount(int count1, int count2) {
        return count2 - count1; //descending order
    }

    public int compareProjectUpdatedDates(String lastModifiedOn, String lastModifiedOn1) {
        return lastModifiedOn1.compareTo(lastModifiedOn); //descending order
    }
    //method to compare workitems by their status
    public int compareWorkItemsByStatus(WorkItem w1, WorkItem w2) {
        return compareStatus(w1.getStatus(), w2.getStatus());
    }
    //method to compare workitems by their number of notes
    public int compareWorkItemsByNotes(WorkItem w1, WorkItem w2) {
        int notes1 = w1.getComments() != null ? w1.getComments().size() : 0;
        int notes2 = w2.getComments() != null ? w2.getComments().size() : 0;
        return notes2 - notes1; //descending order
    }
}
