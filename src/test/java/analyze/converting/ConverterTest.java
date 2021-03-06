package analyze.converting;

import static org.junit.Assert.*;


import java.io.IOException;
import java.text.ParseException;

import model.ChunkedSequentialData;
import model.Column;
import model.ColumnType;
import model.datafield.DataFieldInt;
import model.DateColumn;
import model.Reader;
import model.RecordList;
import model.SequentialData;
import model.UnsupportedFormatException;

import org.junit.Before;
import org.junit.Test;

import analyze.AnalyzeException;
import analyze.chunking.ChunkOnPeriod;
import analyze.chunking.ChunkType;
import analyze.chunking.Chunker;

public class ConverterTest {
    
    private Column[] columns = 
        {new Column("column1", ColumnType.STRING), new Column("column2", ColumnType.STRING), new Column("userID", ColumnType.INT), new Column("value", ColumnType.INT)};
    private String delimiter = ",";
    private SequentialData userData; 
    private Reader reader;

    @Before
    public void setup() throws IOException, UnsupportedFormatException {

        columns[0] = new DateColumn("datum", ColumnType.DATE, "yyMMdd", true);
        columns[0].setType(ColumnType.DATE);
        columns[1] = new DateColumn("tijd", ColumnType.TIME, "HHmm", true);
        columns[1].setType(ColumnType.TIME);
        columns[2].setType(ColumnType.INT);
        columns[3].setType(ColumnType.INT);

        userData = new SequentialData();

        reader = new Reader(columns, delimiter);
        RecordList recordList = reader.read("src/main/resources/testfiles/test_input_convert.txt", false);
        userData.addRecordList(recordList);

    }

    /** Test the generation of feedback
     * @throws ParseException 
     * @throws AnalyzeException 
     */
    @Test
    public void convertFeedbackTest() throws ParseException, AnalyzeException {

        Converter converter = new Converter(userData, "value");
        SequentialData result = converter.convert();
        assertEquals("{datum=2012-02-26, feedback=meting morgen herhalen, grensgebied=3, tijd=14:05, kreatinine status=3, userID=97, value=160}", result.last().toString());

    }

    /** Test the generation of feedback
     * @throws ParseException 
     * @throws AnalyzeException 
     */
    @Test
    public void convertFeedback2Test() throws ParseException, AnalyzeException {

        Converter converter = new Converter(userData, "value");
        ChunkType chunkType = new ChunkOnPeriod(userData, 1);
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(userData, chunkType);

        chunks.get("2012-02-23").first().put("kreatinine status", new DataFieldInt(2));
        chunks.get("2012-02-24").first().put("kreatinine status", new DataFieldInt(4));
        SequentialData result = converter.convert();

        System.out.println(result.toString());

        assertEquals("meting morgen herhalen", result.last().get("feedback").toString());

    }

    /** Test the generation of feedback
     * @throws ParseException 
     * @throws AnalyzeException 
     */
    @Test
    public void convertFeedback3Test() throws ParseException, AnalyzeException {

        Converter converter = new Converter(userData, "value");

        SequentialData result = converter.convert();
        ChunkType chunkType = new ChunkOnPeriod(result, 1);
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(userData, chunkType);

        assertEquals("niets doen", chunks.get("2012-02-23").first().get("feedback").toString());

    }

    /** Test the generation of feedback
     * @throws ParseException 
     * @throws AnalyzeException 
     * @throws IOException 
     */
    @Test
    public void convertFeedback4Test() throws ParseException, AnalyzeException, IOException {

        userData = new SequentialData();
        RecordList recordList2 = reader.read("src/main/resources/testfiles/test_input_convert2.txt", false);
        userData.addRecordList(recordList2);
        Converter converter = new Converter(userData, "value");
        SequentialData result = converter.convert();
        System.out.println(result.toString());

        assertEquals("volg advies arts", result.last().get("feedback").toString());

    }

    /** Test the generation of feedback
     * @throws ParseException 
     * @throws AnalyzeException 
     * @throws IOException 
     */
    @Test
    public void convertFeedback5Test() throws ParseException, AnalyzeException, IOException {

        userData = new SequentialData();
        RecordList recordList2 = reader.read("src/main/resources/testfiles/test_input_convert2.txt", false);
        userData.addRecordList(recordList2);
        Converter converter = new Converter(userData, "value");
        SequentialData result = converter.convert();

        ChunkType chunkType = new ChunkOnPeriod(result, 1);
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(userData, chunkType);

        assertEquals("neem contact met het ziekenhuis", chunks.get("2012-02-23").first().get("feedback").toString());

    }

    /** Test the generation of feedback
     * @throws ParseException 
     * @throws AnalyzeException 
     */
    @Test
    public void convertBorderTest() throws ParseException, AnalyzeException {

        Converter converter = new Converter(userData, "value");
        SequentialData result = converter.convert();

        assertEquals(-1, result.first().get("grensgebied").getIntegerValue());
        assertEquals(3, result.last().get("grensgebied").getIntegerValue());

    }

    /** Test the generation of feedback.
     * @throws ParseException 
     * @throws AnalyzeException 
     */
    @Test
    public void convertDailyStatusTest() throws ParseException, AnalyzeException {

        Converter converter = new Converter(userData, "value");
        SequentialData result = converter.convert();

        assertEquals(-1, result.first().get("kreatinine status").getIntegerValue());
        assertEquals(3, result.last().get("kreatinine status").getIntegerValue());
    }

}
