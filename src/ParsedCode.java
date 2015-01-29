import java.util.ArrayList;
import java.util.HashMap;

public class ParsedCode {

	private ArrayList<Instruction> instructions;
	private HashMap<String, Integer> labels;

	public ParsedCode(ArrayList<Instruction> instructions, HashMap<String, Integer> labels) {
		this.instructions = instructions;
		this.labels = labels;
	}

	public Instruction getInstruction(int i) {
		if (i >= 0 && i < instructions.size()) {
			return instructions.get(i);
		} else {
			return null;
		}
	}

	public int getPositionForLabel(String label) {
		if (labels.containsKey(label)) {
			return labels.get(label);
		} else {
			return -1;
		}
	}
}
