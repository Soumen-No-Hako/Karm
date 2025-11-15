package artim.nemuos.karm;

import artim.nemuos.karm.model.Project;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
public class KarmApp {

	public static List<Project> projects;
	public static void main(String[] args) throws IOException {
//		loadProjects();
		SpringApplication.run(KarmApp.class, args);
	}
	public static void loadProjects() throws IOException {
		Gson objectMapper = new Gson();
		if(!new File("projects.json").exists()){
			new File("projects.json").createNewFile();
		}
		FileInputStream projectFile = new FileInputStream("projects.json");
		String json = Files.readString(Path.of("projects.json"));
		System.out.println("Loaded JSON: " + json.length());
		if(json.length()!=0)projects = List.of( objectMapper.fromJson(json, Project[].class) );
	}
	public void saveProjects() {
		Gson objectMapper = new Gson();
		File projectFile = new File("projects.json");
		String jsonString = objectMapper.toJson(projects);
		// Write jsonString to projectFile
	}
}
