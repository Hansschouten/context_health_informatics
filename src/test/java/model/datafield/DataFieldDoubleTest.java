package model.datafield;

import static org.junit.Assert.*;
import model.ColumnType;
import model.UnsupportedFormatException;
import model.datafield.DataFieldDouble;
import model.datafield.DataFieldString;

import org.junit.Before;
import org.junit.Test;

public class DataFieldDoubleTest {
    DataFieldDouble test;
    
    @Before
    public void setUp() throws Exception {
        test = new DataFieldDouble(2.0);
    }

    /**
     * Test the toString method of DataFieldDouble
     */
    @Test
    public void toStringTest() {
        
        assertEquals("2.0", test.toString());
        
    }
    
    /**
     * Test if getIntegerValue throws an exception on DataFieldDouble
     * @throws UnsupportedFormatException 
     */
    @Test(expected=UnsupportedFormatException.class)
    public void getIntValueTest1() throws UnsupportedFormatException {
        
        test.getIntegerValue();
        
    }
    
    /**
     * Test if getBooleanValue throws an exception on DataFieldDouble
     * @throws UnsupportedFormatException 
     */
    @Test(expected=UnsupportedFormatException.class)
    public void getBooleanValueTest1() throws UnsupportedFormatException {
        
        test.getBooleanValue();
        
    }

    /**
     * Test if equals method returns true on two equal DataFieldDoubles
     */
    @Test
    public void equalsTest() throws UnsupportedFormatException {
        DataFieldDouble other = new DataFieldDouble(2.0);

        assertTrue(test.equals(other));

    }

    /**
     * Test if equals method returns false on two different DataFieldDoubles
     */
    @Test
    public void notEqualTest() throws UnsupportedFormatException {
        DataFieldDouble other = new DataFieldDouble(4.0);
        
        assertEquals(false, test.equals(other));
        
    }
    
    /**
     * Test if equals returns false on two different datafields
     */
    @Test
    public void notEqualFieldsTest() throws UnsupportedFormatException {
        DataFieldString other = new DataFieldString("string");
        
        assertEquals(false, test.equals(other));
        
    }
    
    /**
     * Test if the right hashcode is returned
     */
    @Test
    public void hashCodeTest() throws UnsupportedFormatException {
        
        assertEquals(2, test.hashCode());   
    }
    
    @Test
    public void getTypeTest() {
        assertEquals(test.getType(), ColumnType.DOUBLE);
    }
}
