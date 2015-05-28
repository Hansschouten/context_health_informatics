package analyze.parsing;

import java.util.Scanner;

import analyze.AnalyzeException;
import model.ChunkedSequentialData;
import model.SequentialData;

/**
 * This class represents an object that will parse the typed language.
 * @author Hans Schouten
 *
 */
public class Parser {

	/**
	 * Parser constructor.
	 */
	public Parser() {
	}

	/**
	 * Parse the given script.
	 * @param script			the script that needs to be parsed
	 * @param input				the inputdata
	 * @return 					the result of parsing the script
	 * @throws AnalyzeException exception thrown if script can't be parsed correctly
	 */
	public SequentialData parse(String script, SequentialData input) throws AnalyzeException {
		SequentialData result = input;

		Scanner scanner = new Scanner(script);
		while (scanner.hasNextLine()) {
		  String line = scanner.nextLine();
		  result = parseLine(line, result);
		}
		scanner.close();

		return result;
	}

	/**
	 * Parse one line of the script.
	 * @param line				the line that needs to be parsed
	 * @param data				the data to perform this operation on
	 * @return 					the result of parsing the line
	 * @throws AnalyzeException exception thrown if script can't be parsed correctly
	 */
	protected SequentialData parseLine(String line, SequentialData data) throws AnalyzeException {
		String[] splitted = line.split(" ", 2);
		String operator = splitted[0];
		String operation = splitted[1];

		SubParser parser = this.getSubParser(operator);
		if (data instanceof ChunkedSequentialData) {
			ChunkedSequentialData chunkedData = ((ChunkedSequentialData) data);
			SequentialData result = new SequentialData();
			for (Object chunk : chunkedData.getChunkedData().keySet()) {
				SequentialData chunkResult = parser.parseOperation(operation, chunkedData.get(chunk));
				result.addAll(chunkResult);
			}
			return result;

		} else {
			return parser.parseOperation(operation, data);
		}
	}

	/**
	 * Get the SubParser that can parse the given operator.
	 * @param operator			the operator for which we want the SubParser
	 * @return					the SubParser for this operation
	 */
	protected SubParser getSubParser(String operator) {
		switch (operator.toLowerCase()) {
		case "chunk":
			return new ChunkingParser();
		case "compute":
			return new ComputingParser();
		case "label":
		    return new CodingParser();
		case "filter":
		    return new ConstrainParser();
		default:
			//TODO
			//unsupported operation exception
			return null;
		}
	}

}
