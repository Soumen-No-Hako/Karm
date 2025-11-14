package artim.nemuos.karm;

import artim.nemuos.karm.model.Project;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@SpringBootApplication
public class KarmApp {

	static List<Project> projects;
	public static void main(String[] args) throws FileNotFoundException {
		loadProjects();
		SpringApplication.run(KarmApp.class, args);
	}
	public static void loadProjects() throws FileNotFoundException {
		Gson objectMapper = new Gson();
		if(!new File("projects.json").exists()){
			new File("projects.json");
		}
		FileInputStream projectFile = new FileInputStream("projects.json");
		projects = List.of( objectMapper.fromJson(  projectFile.toString(), Project[].class) );
	}
	public void saveProjects() {
		Gson objectMapper = new Gson();
		File projectFile = new File("projects.json");
		String jsonString = objectMapper.toJson(projects);
		// Write jsonString to projectFile
	}
}
