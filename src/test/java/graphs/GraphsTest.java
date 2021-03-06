package graphs;

import static org.junit.Assert.*;

import org.junit.Test;

public class GraphsTest {

    @Test
    public void stemandLeaveScriptTest() {
        Graph gf = new StemAndLeavePlot();
        assertEquals("drawStemAndLeave('hoi', 'dit is data in JSON')"
                ,gf.getScript("hoi", "dit is data in JSON"));
    }
    
    @Test
    public void LineChartTest() {
        Graph gf = new LineChart();
        assertEquals("drawLineGraph('hoi', 'dit is data in JSON')"
                ,gf.getScript("hoi", "dit is data in JSON"));
    }
    
    @Test
    public void histogramTest() {
        Graph gf = new Histogram();
        assertEquals("drawHistogram('hoi', 'dit is data in JSON')"
                ,gf.getScript("hoi", "dit is data in JSON"));
    }

    @Test
    public void frequencyBarTest() {
        Graph gf = new FrequencyBar();
        assertEquals("drawFrequencyBar('hoi', 'dit is data in JSON')"
                ,gf.getScript("hoi", "dit is data in JSON"));
    }
    
    @Test
    public void boxPlotTest() {
        Graph gf = new BoxPlot();
        assertEquals("drawBoxPlot('hoi', 'dit is data in JSON')"
                ,gf.getScript("hoi", "dit is data in JSON"));
    }
    
    @Test
    public void barChartTest() {
        Graph gf = new BarChart();
        assertEquals("drawBarChart('hoi', 'dit is data in JSON')"
                ,gf.getScript("hoi", "dit is data in JSON"));
    }
    
    @Test
    public void StateTransitionDiagramTest() {
        Graph gf = new StateTransitionMatrix();
        assertEquals("drawTransitionMatrix('hoi', 'dit is data in JSON')"
                ,gf.getScript("hoi", "dit is data in JSON"));
    }

    @Test
    public void getURLTest() {
        Graph gf = new BarChart();
        assertEquals("/graphs/barchart.html"
                ,gf.getURL());
    }
    
    @Test (expected = GraphException.class)
    public void getAddableItemTest() throws GraphException {
        Graph gf = new BoxPlot();
        gf.getAddableItem();
    }
    
    @Test 
    public void getAddableItemLineChartTest() throws GraphException {
        Graph gf = new LineChart();
        InputType ty = gf.getAddableItem();
        assertEquals("line 1", ty.inputName);
        assertEquals(false, ty.timestampAllowed);
        assertEquals(false, ty.labelAllowed);
    }
    
    @Test 
    public void getAddableItemBarChartTest() throws GraphException {
        Graph gf = new BarChart();
        InputType ty = gf.getAddableItem();
        assertEquals("bar 1", ty.inputName);
        assertEquals(false, ty.timestampAllowed);
        assertEquals(false, ty.labelAllowed);
    }
    
    @Test
    public void graphExceptionTest() {
        try {
            throw new GraphException("hoi");
        } catch (GraphException e) {
            assertEquals("hoi", e.getMessage());
        }
    }
}
