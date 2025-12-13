package artim.nemuos.karm;

import artim.nemuos.karm.model.Project;
import artim.nemuos.karm.model.WorkItem;
import artim.nemuos.karm.utility.CommandParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class KarmApp {

	public static List<Project> projects;
	public static List<WorkItem> workItems;

	public static void main(String[] args) throws IOException {

		CommandParser cmdPrsr = new CommandParser();
//		AtomicInteger i= new AtomicInteger(1);
		loadProjects();
		if(args.length>0) cmdPrsr.processCommand(args, projects, workItems);
		else SpringApplication.run(KarmApp.class, args);
	}
	public static void loadProjects() throws IOException {
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

		if(!new File("workitems.json").exists()){
			try {
				new File("workitems.json").createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		json = null;
		try {
			json = Files.readString(Path.of("workitems.json"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if(json.length()!=0) workItems = objectMapper.fromJson(json, new TypeToken<ArrayList<WorkItem>>() {}.getType());
	}
	public void saveProjects() {
		Gson objectMapper = new Gson();
		File projectFile = new File("projects.json");
		String jsonString = objectMapper.toJson(projects);
		// Write jsonString to projectFile
	}
}
