import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class SDPMain {
	
	public static String slowDirectory = "C:\\Users\\talex\\Downloads\\SDPtest";
	public static String fastDirectory = "C:\\Users\\talex\\Desktop\\SDP";
	public static boolean debugPrintOuts = true;
	
	public static void checkOut(String directory) throws IOException {
		Path sourceDirectory = Paths.get(directory);
		Path targetDirectory = Paths.get(fastDirectory);
		Files.copy(sourceDirectory, targetDirectory);
	}
	
	public void listFiles(String directoryName) {
		File directory = new File(directoryName);
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				System.out.println(file.getName());
			}
		}
	}

	public void listFilesAndFilesSubDirectories(String directoryName) throws IOException {
		File directory = new File(directoryName);
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				String pathWithoutRoot = file.getAbsolutePath().replace(slowDirectory, "");
				Path sourceDirectory = Paths.get(file.getAbsolutePath());
				Path targetDirectory = Paths.get(fastDirectory + pathWithoutRoot);
				if (debugPrintOuts) {
					System.out.println("COPY " + file.getAbsolutePath() + " -> " + fastDirectory + pathWithoutRoot);
				}
				Files.createDirectories(targetDirectory.getParent());
				Files.copy(sourceDirectory, targetDirectory, StandardCopyOption.REPLACE_EXISTING);
			}
			else if (file.isDirectory()) {
				listFilesAndFilesSubDirectories(file.getAbsolutePath());
			}
		}
	}

	public static void main (String[] args) {
		System.out.println("The SpeedDraft SuperDiskPerformante Application");
		System.out.println("Build 002 - 29 Oct 2018");
		System.out.println("A Component of the SpeedDraft Drawing Automation System");
		System.out.println("----- ----- ----- ----- -----");
		System.out.println("Input directory to check out: ");
		Scanner kbd = new Scanner(System.in);
		slowDirectory = kbd.nextLine();
		kbd.close();
		if (slowDirectory.charAt(slowDirectory.length() - 1) == '\\') {
			System.out.println("Directory must not end in a \'\\\' character.");
			return;
		}
		System.out.println("Checking out files from \"" + slowDirectory + "...");
		long startCopyToFast = System.currentTimeMillis();
		SDPMain listFilesUtil = new SDPMain();
		try {
			listFilesUtil.listFilesAndFilesSubDirectories(slowDirectory);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Checkout finished in " + ((int) Math.floor((System.currentTimeMillis() - startCopyToFast) / 1000) + 1) + " sec.");
	}
}