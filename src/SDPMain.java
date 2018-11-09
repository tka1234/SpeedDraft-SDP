import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class SDPMain {
	
	public static String slowDirectory = "C:\\Users\\talex\\Downloads\\SDPtest";
	public static String fastDirectory = "C:\\Users\\talex\\Desktop\\SDP";
	public static boolean debugPrintOuts = true;
	
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
		System.out.println("Build 004 - 8 Nov 2018");
		System.out.println("A Component of the SpeedDraft Drawing Automation System");
		System.out.println("----- ----- ----- ----- -----");
		System.out.print("Input directory to check out: ");
		Scanner kbd = new Scanner(System.in);
		slowDirectory = kbd.nextLine();
		if (slowDirectory.charAt(slowDirectory.length() - 1) == '\\') {
			System.out.println("Directory must not end in a \'\\\' character.");
			kbd.close();
			return;
		}
		SDPMain listFilesUtil = new SDPMain();
		try {
			System.out.print("Backing up files from \"" + slowDirectory + "... ");
			long startCopyToFast = System.currentTimeMillis();
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date date = new Date();
			String currentDateTime = dateFormat.format(date);
			String trueDestDirectory = fastDirectory;
			fastDirectory = "C:\\Users\\talex\\Desktop\\SDP" + "-backup" + currentDateTime;
			listFilesUtil.listFilesAndFilesSubDirectories(slowDirectory);
			System.out.println("finished in " + ((int) Math.floor((System.currentTimeMillis() - startCopyToFast) / 1000) + 1) + " sec.");
			System.out.print("Checking out files from \"" + slowDirectory + "... ");
			startCopyToFast = System.currentTimeMillis();
			fastDirectory = trueDestDirectory;
			listFilesUtil.listFilesAndFilesSubDirectories(slowDirectory);
			System.out.println("finished in " + ((int) Math.floor((System.currentTimeMillis() - startCopyToFast) / 1000) + 1) + " sec.");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print("Press Enter to check back in.");
		kbd.nextLine();
		System.out.println("Checking files back in...");
		try {
			String returnToDirectory = slowDirectory;
			slowDirectory = fastDirectory;
			fastDirectory = returnToDirectory;
			listFilesUtil.listFilesAndFilesSubDirectories(slowDirectory);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		kbd.close();
	}
}