import exceptions.CodeFormatException;
import exceptions.InvalidInstructionException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Parser {

	private ArrayList<String> rawLines = new ArrayList<String>();

	// create a parser for an input file
	public Parser(File inputFile) throws IOException {
		// does the file exist?
		if (!inputFile.exists()) {
			throw new FileNotFoundException();
		}

		// open a buffered reader for the input file
		BufferedReader br = new BufferedReader(new FileReader(inputFile));

		// read the file line by line
		String line = br.readLine();
		while (line != null) {
			rawLines.add(line);

			// read the next line
			line = br.readLine();
		}
	}

	// create a parser for an input string
	public Parser(String inputCode) {
		// split by lines
		String[] lines = inputCode.split("\n");

		// put them all into the raw lines away
		Collections.addAll(rawLines, lines);
	}

	// parse the code, and return two things:
	// an ArrayList of Instruction objects, and a HashMap of labels -> line numbers
	public ParsedCode parse() throws CodeFormatException {
		// prepare output values
		ArrayList<Instruction> instructions = new ArrayList<Instruction>();
		HashMap<String, Integer> labels = new HashMap<String, Integer>();

		// iterate through rawLines and parse into instructions, labels, etc
		String line;
		for (int i = 0; i < rawLines.size(); ++i) {
			// trim whitespace
			line = rawLines.get(i).trim();

			// is this a comment or a blank line?
			if (line.length() == 0 || line.startsWith("//")) {
				// SKIP!
				continue;
			}

			// does the line contain a label?
			if (line.contains(":")) {
				// split the line into label and instruction
				String[] labelChunks = line.split(":");

				// check we have the right amount of segments
				if (labelChunks.length != 2) {
					throw new CodeFormatException(i);
				}

				// put this label into the label map
				labels.put(labelChunks[0].trim(), instructions.size());

				// restore the line as the rest of the instruction
				line = labelChunks[1].trim();
			}

			// parse the instruction and (maybe) argument
			Instruction instruction;
			if (line.contains(" ")) {
				// split the line into instruction and argument
				String[] instructionChunks = line.split(" ");

				// check we have the right amount of segments
				if (instructionChunks.length != 2) {
					throw new CodeFormatException(i);
				}

				// is it a number argument or string?
				try {
					int intArg = Integer.parseInt(instructionChunks[1].trim());
					try {
						instruction = new Instruction(instructionChunks[0].trim(), intArg);
					} catch (InvalidInstructionException iie) {
						throw new CodeFormatException(i, iie);
					}
				} catch (NumberFormatException nfe) {
					try {
						instruction = new Instruction(instructionChunks[0].trim(), instructionChunks[1].trim());
					} catch (InvalidInstructionException iie) {
						throw new CodeFormatException(i, iie);
					}
				}
			} else {
				// parse a single instruction
				try {
					instruction = new Instruction(line.trim());
				} catch (InvalidInstructionException iie) {
					throw new CodeFormatException(i, iie);
				}
			}

			// add to the instruction list
			instructions.add(instruction);
		}

		// return the pair
		return new ParsedCode(instructions, labels);
	}

}
