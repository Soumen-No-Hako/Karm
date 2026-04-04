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
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		loadData();
		if(args.length>0) cmdPrsr.processCommand(args, projects, workItems, karmDataLoc);
		else SpringApplication.run(KarmApp.class, args);
	}

	private static void bootKarmApp() {
		String os = System.getProperty("os.name").toLowerCase();
		Path basePath;

		if (os.contains("win")) {
			String localAppData = System.getenv("LOCALAPPDATA");
			basePath = Paths.get(localAppData, "Karm", "data");
		} else {
			// Unix/Linux approach: /home/user/.karm/data
			basePath = Paths.get(System.getProperty("user.home"), ".karm", "data");
		}

		karmDataLoc = basePath.toString() + FileSystems.getDefault().getSeparator();

		try {
			Files.createDirectories(basePath);
			System.out.println("Karm Data Location: " + karmDataLoc);
		} catch (IOException e) {
			System.err.println("Failed to create directory: " + e.getMessage());
		}
	}

	public static void loadData() throws IOException {
		Gson gson = new Gson();
		// Generalized loading logic
		projects = loadFile(gson, "projects.json", new TypeToken<ArrayList<Project>>(){});
		workItems = loadFile(gson, "workitems.json", new TypeToken<ArrayList<WorkItem>>(){});
	}

	private static <T> T loadFile(Gson gson, String fileName, TypeToken<T> typeToken) throws IOException {
		Path filePath = Paths.get(karmDataLoc, fileName);
		if (Files.notExists(filePath)) {
			Files.createFile(filePath);
			return null;
		}
		String content = Files.readString(filePath);
		return content.isEmpty() ? null : gson.fromJson(content, typeToken.getType());
	}
	public void saveProjects() {
		Gson objectMapper = new Gson();
		File projectFile = new File("projects.json");
		String jsonString = objectMapper.toJson(projects);
		// Write jsonString to projectFile
	}
}
