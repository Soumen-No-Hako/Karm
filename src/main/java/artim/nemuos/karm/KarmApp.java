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
	public static String karmDataLoc;

	public static void main(String[] args) throws IOException {
		bootKarmApp();
		CommandParser cmdPrsr = new CommandParser();
//		AtomicInteger i= new AtomicInteger(1);
		loadProjects();
		if(args.length>0) cmdPrsr.processCommand(args, projects, workItems, karmDataLoc);
		else SpringApplication.run(KarmApp.class, args);
	}

	private static void bootKarmApp() {
		var sysEnv = System.getenv();
		System.out.println("Booting Karm Application...");
		karmDataLoc = sysEnv.get("LOCALAPPDATA")+"\\Karm\\data\\";
		System.out.println("Karm Data Location Will be initialised at : "+karmDataLoc);
		if(!new File(karmDataLoc).exists()){
			new File(karmDataLoc).mkdirs();
			System.out.println("Karm Data Directory created at : "+karmDataLoc);
		}
	}

	public static void loadProjects() throws IOException {
		Gson objectMapper = new Gson();
		if(!new File(karmDataLoc+"projects.json").exists()){
			try {
				new File(karmDataLoc+"projects.json").createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		String json = null;
		try {
			json = Files.readString(Path.of(karmDataLoc+"projects.json"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if(json.length()!=0) projects = objectMapper.fromJson(json, new TypeToken<ArrayList<Project>>() {}.getType());

		if(!new File(karmDataLoc+"workitems.json").exists()){
			try {
				new File(karmDataLoc+"workitems.json").createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		json = null;
		try {
			json = Files.readString(Path.of(karmDataLoc+"workitems.json"));
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
