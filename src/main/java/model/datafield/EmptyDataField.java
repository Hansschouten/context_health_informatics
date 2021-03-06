package model.datafield;

import model.ColumnType;
import model.UnsupportedFormatException;

/**
 * This class represents a datafield, that does not have a value.
 * @author Matthijs
 *
 */
public class EmptyDataField implements DataField {

    /**
     * This method constructs an EmptyDatafield, that does not contain anything.
     */
    public EmptyDataField() { }

    @Override
    public int getIntegerValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Empty datafield does not contain anything.");
    }

    @Override
    public double getDoubleValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Empty datafield does not contain anything.");
    }

    @Override
    public boolean getBooleanValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Empty datafield does not contain anything.");
    }

    @Override
    public String toString() {
        return "Empty datafield";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof EmptyDataField) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public ColumnType getType() {
        return ColumnType.STRING;
    }
}
