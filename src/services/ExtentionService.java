package services;

import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class ExtentionService {

	public String getExtention(String input) {
		Yaml yaml = new Yaml();
        try {
        	Map<String, Object> obj = yaml.load(input);
        	obj.clear();
        	return "yaml";
        } catch (Exception e) {
//        	e.printStackTrace();
        	return "properties";
        	
        }
	}
}
