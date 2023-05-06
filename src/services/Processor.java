package services;

import java.io.File;
import java.nio.file.Files;
import java.time.Instant;

import org.json.JSONObject;

import com.mulesoft.tools.SecurePropertiesTool;

import pojos.Payload;

public class Processor {
	private Processor() {}


	public static JSONObject startProcess(Payload payload) {
		deleteOldFolders();

		JSONObject result = new JSONObject();

		System.out.print(payload.getOperation() + "ion started for ");
		if (payload.getInputType().equals("input-string")) {
			System.out.println(" String");
			result.put("response", processString(payload.getOperation(), payload.getAlgorithm(),
					payload.getMethod(), payload.getKey(), payload.getInput()));

		} else if (payload.getInputType().equals("input-file")) {
			System.out.println("File");
			result.put("response", processFile(payload.getOperation(), payload.getAlgorithm(), payload.getMethod(),
					payload.getKey(), payload.getInput(), payload.getExt()));

		} else if (payload.getInputType().equals("input-whole-file")) {
			System.out.println("Whole File");
			result.put("response", processWholeFile(payload.getOperation(), payload.getAlgorithm(),
					payload.getMethod(), payload.getKey(), payload.getInput(), payload.getExt()));
		}

		System.out.println(payload.getOperation() + "ion Complete");
		return result;
	}


	private static void deleteOldFolders() {
//		System.out.println("Delete Folders Started");
		long now = Instant.now().getEpochSecond();
		int timeToLapse = 60;
		File processingFolder = new File("processing_space");
		if (processingFolder.exists()) {
			String[] folderNames = processingFolder.list();
			long cutoffTime = now - timeToLapse;
			boolean hadFolders = false;
			for (String folderName : folderNames) {
				
				if (!folderName.equalsIgnoreCase("Readme.md")) {
					File folder = new File(processingFolder, folderName);
					long createdTime = Long.parseLong(folderName);
					if (createdTime < cutoffTime) {
						hadFolders = true;
						deleteFolder(folder);
						System.out.println("Deleted " + folderName);
					}
				}
			}
			if (hadFolders)
				System.out.println("Delete Folders Complete");
			else
				System.out.println("No Folders in System to Delete");
		}else {
			processingFolder.mkdir();
		}
	}

	private static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					deleteFolder(file);
				} else {
					file.delete();
				}
			}
		}
		folder.delete();
	}


	private static String processString(String operation, String algorithm, String method, String key, String input) {
		String result = "";

		try {
			result = SecurePropertiesTool
					.applyOverString(
							(operation.equals("encrypt") ? SecurePropertiesTool.ENCRYPTION_ACTION
									: SecurePropertiesTool.DECRYPTION_ACTION),
							algorithm, method, key, false, input);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause() != null) {
				System.out.println("ERROR : " + e.getMessage() + " " + e.getCause().getMessage());
				return "ERROR : " + e.getMessage() + " " + e.getCause().getMessage();
			}else {
				System.out.println("ERROR : " + e.getMessage() );
				return "ERROR : " + e.getMessage();
			}
		}

		return result;
	}

	private static String processFile(String operation, String algorithm, String method, String key, String input,
			String ext) {

		String result = "";
		String timeStamp = "processing_space" + File.separator + String.valueOf(Instant.now().getEpochSecond());
		File inputFile = new File(timeStamp + File.separator + "input." + ext);
		File outputFile = new File(timeStamp + File.separator + "output." + ext);
		inputFile.getParentFile().mkdir();

		try {

			Files.write(inputFile.toPath(), input.getBytes());

			String inputPath = inputFile.getCanonicalPath();
			String outputPath = outputFile.getCanonicalPath();

			SecurePropertiesTool.applyOverFile(
					(operation.equals("encrypt") ? SecurePropertiesTool.ENCRYPTION_ACTION
							: SecurePropertiesTool.DECRYPTION_ACTION),
					algorithm, method, key, false, inputPath, outputPath);

			result = new String(Files.readAllBytes(outputFile.toPath()));

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause() != null) {
				System.out.println("ERROR : " + e.getMessage() + " " + e.getCause().getMessage());
				return "ERROR : " + e.getMessage() + " " + e.getCause().getMessage();
			}else {
				System.out.println("ERROR : " + e.getMessage() );
				return "ERROR : " + e.getMessage();
			}
		}
		return result;
	}

	private static String processWholeFile(String operation, String algorithm, String method, String key, String input, String ext) {
		String result = "";
		String timeStamp = "processing_space" + File.separator + String.valueOf(Instant.now().getEpochSecond());
		File inputFile = new File(timeStamp + File.separator + "input." + ext);
		File outputFile = new File(timeStamp + File.separator + "output." + ext);
		inputFile.getParentFile().mkdir();

		try {

			Files.write(inputFile.toPath(), input.getBytes());

			String inputPath = inputFile.getCanonicalPath();
			String outputPath = outputFile.getCanonicalPath();

			SecurePropertiesTool.applyHoleFile(
					(operation.equals("encrypt") ? SecurePropertiesTool.ENCRYPTION_ACTION
							: SecurePropertiesTool.DECRYPTION_ACTION),
					algorithm, method, key, false, inputPath, outputPath);

			result = new String(Files.readAllBytes(outputFile.toPath()));

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause() != null) {
				System.out.println("ERROR : " + e.getMessage() + " " + e.getCause().getMessage());
				return "ERROR : " + e.getMessage() + " " + e.getCause().getMessage();
			}else {
				System.out.println("ERROR : " + e.getMessage() );
				return "ERROR : " + e.getMessage();
			}
		}
		return result;
	}

}
