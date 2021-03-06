package analyze.labeling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import analyze.AnalyzeException;
import analyze.labeling.Label;
import analyze.labeling.LabelFactory;

public class LabelFactoryTest {

    @Test
    public void getInstanceTest() {
        assertTrue(LabelFactory.getInstance() == LabelFactory.getInstance());
    }
    
    @Test
    public void getNewLabelTest() {
        Label l = LabelFactory.getInstance().getNewLabel("hoi");
        assertEquals(l.getName(),  "hoi");
    }
    
    @Test
    public void getNewLabel2Test() {
        Label l = LabelFactory.getInstance().getNewLabel("hoi");
        assertEquals(l.getName(),  "hoi");
    }
    
    @Test
    public void getLabelAscending2Test() {
        Label l = LabelFactory.getInstance().getNewLabel("testacending");
        Label l2 = LabelFactory.getInstance().getNewLabel("testacending1");
        Label l3 = LabelFactory.getInstance().getNewLabel("testacending2");
        assertTrue(l.number + 1 == l2.number);
        assertTrue(l2.number + 1 == l3.number);
    }

    @Test
    public void getLabelWithSameNameTest() {
        Label l = LabelFactory.getInstance().getNewLabel("hoi");
        Label l2 = LabelFactory.getInstance().getNewLabel("hoi");
        Label l3 = LabelFactory.getInstance().getNewLabel("hoi");
        assertTrue(l.number == l2.number);
        assertTrue(l2.number == l3.number);
    }
    
    @Test
    public void getNumberOfLabelTest(){
        Label l = LabelFactory.getInstance().getNewLabel("string");
        int number = LabelFactory.getInstance().getNumberOfLabel("string");
        assertTrue(l.number == number);
        LabelFactory.getInstance().getNewLabel("string");
        assertTrue(l.number == number);
    }
    
    @Test
    public void getNumberOfLabelNotTest(){
        
        assertTrue(LabelFactory.getInstance().getNumberOfLabel("notused") == -1);
    }
    
    @Test
    public void getLabelOfNumberTest(){
        Label l = LabelFactory.getInstance().getNewLabel("glontest");
        assertTrue(LabelFactory.getInstance().getLabelofNumber(l.number) == l);
    }
    
    @Test
    public void LabelingExceptionTest(){
        try {
            throw new LabelingException("hoi");
        } catch (AnalyzeException e) {
            assertEquals("LabelingException: hoi", e.getMessage());
        }
    }
}
