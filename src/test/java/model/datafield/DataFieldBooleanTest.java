package model.datafield;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import model.ColumnType;
import model.UnsupportedFormatException;
import model.datafield.DataFieldBoolean;
import model.datafield.DataFieldInt;

import org.junit.Before;
import org.junit.Test;

public class DataFieldBooleanTest {
    
    private DataFieldBoolean test;
    
    @Before
    public void setUp() throws Exception {
        test = new DataFieldBoolean(true);
    }

    /** Test the toString method of DataFieldBoolean.
     */
    @Test
    public void toStringTest() {
        assertEquals("true", test.toString());
    }
    
    /** Test if getIntegerValue throws an exception on DataFieldBoolean.
     * @throws UnsupportedFormatException 
     */
    @Test(expected=UnsupportedFormatException.class)
    public void getIntValueTest1() throws UnsupportedFormatException {
        
        test.getIntegerValue();
        
    }
    
    /** Test if getBooleanValue throws an exception on DataFieldBoolean.
     * @throws UnsupportedFormatException 
     */
    @Test
    public void getBooleanValueTest1() throws UnsupportedFormatException {
        
        assertEquals(true, test.getBooleanValue());
        
    }

    /** Test if equals method returns true on two equal DataFieldBooleans.
     */
    @Test
    public void equalsTest() throws UnsupportedFormatException {
        DataFieldBoolean other = new DataFieldBoolean(true);

        assertTrue(test.equals(other));
        
    }
    
    /** Test if equals method returns false on two different DataFieldBooleans.
     */
    @Test
    public void notEqualTest() throws UnsupportedFormatException {
        DataFieldBoolean other = new DataFieldBoolean(false);
        assertEquals(false, test.equals(other));
    }
    
    /**
     * Test if equals returns false on two different datafields
     */
    @Test
    public void notEqualFieldsTest() throws UnsupportedFormatException {
        DataFieldInt other = new DataFieldInt(2);
        
        assertEquals(false, test.equals(other));
        
    }
    
    /**
     * Test if the right hashcode is returned
     */
    @Test
    public void hashCodeTest() throws UnsupportedFormatException {
        assertEquals(1, test.hashCode());
    }

    @Test
    public void hashCodeFalseTest() throws UnsupportedFormatException {
        DataFieldBoolean b1 = new DataFieldBoolean(false);
        assertEquals(0, b1.hashCode());
    }
    
    @Test
    public void getTypeTest() {
        assertEquals(test.getType(), ColumnType.STRING);
    }
}
