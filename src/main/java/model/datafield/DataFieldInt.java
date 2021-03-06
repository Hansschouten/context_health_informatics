package model.datafield;

import model.ColumnType;
import model.UnsupportedFormatException;

/**
 * This class represent a datafield containing a Integer.
 * @author Matthijs
 *
 */
public class DataFieldInt implements DataField {

    /**
     * This variable stores the value of the datafield.
     */
    protected int value;

    /**
     * Construct a DataFieldInt containing an integer.
     * @param val   - Value of the datafield.
     */
    public DataFieldInt(int val) {
        value = val;
    }

    @Override
    public int getIntegerValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public double getDoubleValue() {
        return value;
    }

    @Override
    public boolean getBooleanValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Integer datafield cannot be converted to a boolean.");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DataFieldInt) {
            return ((DataFieldInt) o).value == this.value;
        } else if (o instanceof DataFieldDouble) {
            return ((DataFieldDouble) o).value == this.value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public ColumnType getType() {
        return ColumnType.INT;
    }
}
