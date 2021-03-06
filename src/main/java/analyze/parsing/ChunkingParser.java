package analyze.parsing;

import analyze.AnalyzeException;
import analyze.chunking.ChunkOnHour;
import analyze.chunking.ChunkOnPeriod;
import analyze.chunking.ChunkOnValue;
import analyze.chunking.ChunkOnWeekday;
import analyze.chunking.ChunkType;
import analyze.chunking.Chunker;
import analyze.chunking.ChunkingException;
import analyze.pattern.PatternMatcher;
import analyze.pattern.PatternMatcherException;
import model.datafield.DataField;
import model.ChunkedSequentialData;
import model.Column;
import model.ColumnType;
import model.SequentialData;

/**
 * This class represents an object that will parse chunking operations.
 * @author Hans Schouten
 *
 */
public class ChunkingParser implements SubParser {

    @Override
    public SequentialData parseOperation(String operation, SequentialData data)
            throws ChunkingException {
        Chunker chunker = new Chunker();
        ChunkType chunkType;

        String[] arguments = operation.split(" ", 2);
        String operator = arguments[0];

        switch (operator) {
            case "FLATTEN":
                return chunker.flatten(data);

            case "REMOVE":
                return chunker.remove(data);

            case "PATTERN":
                if (arguments.length < 2) {
                    throw new ChunkingException("No pattern provided.");
                }
                try {
                    PatternMatcher p = new PatternMatcher();
                    int i = 0;
                    ChunkedSequentialData result = new ChunkedSequentialData();
                    for (SequentialData chunkedData : p.match(arguments[1], data)) {
                        result.add("Chunk " + i, chunkedData);
                        i++;
                    }
                    return result;
                } catch (PatternMatcherException ex) {
                    throw new ChunkingException(ex.getMessage());
                }

            case "ON":
                if (arguments.length < 2 || !arguments[1].contains("COL(")) {
                    throw new ChunkingException("No column provided.");
                }
                String[] parts = arguments[1].split("COL\\(");
                String columnName = parts[1].split("\\)", 2)[0];
                chunkType = new ChunkOnValue(columnName);
                data.refreshColumns();
                Column column = data.getColumn(columnName);
                if (arguments[1].contains("PER WEEKDAY")) {
                    chunkType = new ChunkOnWeekday(columnName);
                    if (!(column.getType() == ColumnType.DATEandTIME || column.getType() == ColumnType.DATE)) {
                        throw new ChunkingException("Cannot chunk per weekday on a column of type: "
                                + column.getType());
                    }
                } else if (arguments[1].contains("PER HOUR")) {
                    chunkType = new ChunkOnHour(columnName);
                    if (!(column.getType() == ColumnType.DATEandTIME || column.getType() == ColumnType.TIME)) {
                        throw new ChunkingException("Cannot chunk per hour on a column of type: " + column.getType());
                    }
                }
                break;

            case "PER":
                if (arguments.length < 2) {
                    throw new ChunkingException("No period length provided.");
                }
                parts = arguments[1].split(" ");
                int length = Integer.parseInt(parts[0]);
                chunkType = new ChunkOnPeriod(data, length);
                break;

            default:
                throw new ChunkingException("Use CHUNK FLATTEN, CHUNK REMOVE, CHUNK ON or CHUNK PER.");
        }

        SequentialData chunkedData = chunker.chunk(data, chunkType);
        return chunkedData;
    }

    @Override
    public ParseResult parseOperation(String operation, DataField data)
            throws AnalyzeException {
        throw new ChunkingException("Chunking on a single value is not possible");
    }

}
