package model;

/**
 * This interface defines a datafield,
 * In a class that implement this interface, some data is stored..
 * @author Matthijs
 *
 */
public interface DataField {

// Here we can specief all the operations that need to be done on all the data.

    /**
     * This method should return the Integer of the DataField.
     * @return      - integer representation of this datafield
     * @throws UnsupportedFormatException   - thrown when the datafield cannot be converted to the required format.
     */
    int getIntegerValue() throws UnsupportedFormatException;

    /**
     * This method should return the double value of the DataField.
     * @return      - integer representation of this datafield
     * @throws UnsupportedFormatException   - thrown when the datafield cannot be converted to the required format.
     */
    double getDoubleValue() throws UnsupportedFormatException;

    /**
     * This method should return the double representation of the DataField.
     * @return      - Boolean representing the datafield.
     * @throws UnsupportedFormatException   - thrown when the datafield cannot be converted to the required format.
     */
    boolean getBooleanValue() throws UnsupportedFormatException;

    /**
     * This method should return a string representation of the DataField.
     * @return      - String representation of the datafield
     */
    String getStringValue();
}
