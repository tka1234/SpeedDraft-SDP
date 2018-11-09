import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MiniCadIntegrity {

	public static void main(String[] args) {
		System.out.print("Input file to verify: ");
		Scanner kbd = new Scanner(System.in);
		String filePath = kbd.nextLine();
		kbd.close();
		Scanner input;
		try {
			input = new Scanner(new File(filePath));
			String readingBuffer = "";
		//This part scans through the other drawing settings & parameters
			while (input.hasNextLine()) {
				readingBuffer = input.nextLine();
				if (readingBuffer.contains("?")) break;
			}
			while (input.hasNextLine()) {
				readingBuffer = input.nextLine();
				if (!readingBuffer.contains("?")) break;
			}
		//This part scans through the drawings
			String[] triBuffer = new String[3];
			if (input.hasNextLine()) triBuffer[0] = input.nextLine();
			else return;
			if (input.hasNextLine()) triBuffer[1] = input.nextLine();
			else return;
			if (input.hasNextLine()) triBuffer[2] = input.nextLine();
			else return;
			while (true) {
				if (triBuffer[0].contains("===") && triBuffer[1].contains(".dwg")) {
					System.out.println("Good Drawing - " + triBuffer[1]);
					triBuffer[0] = triBuffer[2];
					if (input.hasNextLine()) triBuffer[1] = input.nextLine();
					else triBuffer[1] = "";
					if (input.hasNextLine()) triBuffer[2] = input.nextLine();
					else triBuffer[2] = "";
				}
				else if (triBuffer[0].contains("===") && triBuffer[1].contains("===") && triBuffer[2].contains(".dwg")) {
					System.out.println("Good Drawing - " + triBuffer[2]);
					if (input.hasNextLine()) triBuffer[0] = input.nextLine();
					else triBuffer[0] = "";
					if (input.hasNextLine()) triBuffer[1] = input.nextLine();
					else triBuffer[1] = "";
					if (input.hasNextLine()) triBuffer[2] = input.nextLine();
					else triBuffer[2] = "";
				}
				else if (triBuffer[0].equals("") && triBuffer[1].equals("") && triBuffer[2].equals("")) break;
				else {
					System.out.print("Bad Drawing - ");
					if (triBuffer[0].contains(".dwg")) {
						System.out.println(triBuffer[0]);
						triBuffer[0] = triBuffer[1];
						triBuffer[1] = triBuffer[2];
						if (input.hasNextLine()) triBuffer[2] = input.nextLine();
						else triBuffer[2] = "";
					}
					else if (triBuffer[1].contains(".dwg")) {
						System.out.println(triBuffer[1]);
						triBuffer[0] = triBuffer[2];
						if (input.hasNextLine()) triBuffer[1] = input.nextLine();
						else triBuffer[1] = "";
						if (input.hasNextLine()) triBuffer[2] = input.nextLine();
						else triBuffer[2] = "";
					}
					else if (triBuffer[2].contains(".dwg")) {
						System.out.println(triBuffer[2]);
						if (input.hasNextLine()) triBuffer[0] = input.nextLine();
						else triBuffer[0] = "";
						if (input.hasNextLine()) triBuffer[1] = input.nextLine();
						else triBuffer[1] = "";
						if (input.hasNextLine()) triBuffer[2] = input.nextLine();
						else triBuffer[2] = "";
					}
					else {
						System.out.println("<No name>");
						triBuffer[0] = triBuffer[1];
						triBuffer[1] = triBuffer[2];
						if (input.hasNextLine()) triBuffer[2] = input.nextLine();
						else triBuffer[2] = "";
					}
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}