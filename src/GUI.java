import exceptions.CodeFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GUI {

	public static void main(String... args) {
		if (args.length != 1) {
			System.out.println("Invalid use");
		} else {
			Parser parser;
			try {
				parser = new Parser(new File(args[0]));
			} catch (FileNotFoundException fnfe) {
				System.out.println("Input file not found");
				return;
			} catch (IOException ioe) {
				System.out.println("Error reading file");
				return;
			}

			ParsedCode parsed;
			try {
				parsed = parser.parse();
			} catch (CodeFormatException cfe) {
				System.out.println("Input error on line " + cfe.getLineNumber() + (cfe.getIie() != null ? " (invalid instruction: '" + cfe.getIie().getInstructionValue() + "')" : ""));
			}


		}
	}

}
