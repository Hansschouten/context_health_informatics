package analyze.computation;

import java.util.HashMap;

import model.UnsupportedFormatException;
import model.datafield.DataField;
import model.datafield.DataFieldDouble;

/**
 * This class calculates the sum of squares of a list of column values.
 * @author Elvan
 *
 */
public abstract class SQUARED {

    /**
     * Run the computation.
     * @param columnValues                    the values to run the computation on
     * @return                                the result of the computation
     * @throws UnsupportedFormatException    format is not supported
     */
    public static DataField run(HashMap<String, DataField> columnValues) throws UnsupportedFormatException {
        double sum = 0;
        double sumSquared = 0;
        int count = columnValues.size();

        for (DataField value : columnValues.values()) {
            sum = sum + value.getDoubleValue();
            sumSquared = sumSquared + Math.pow(value.getDoubleValue(), 2);
        }

        double result = sumSquared - Math.pow(sum, 2) / count;

        return new DataFieldDouble(result);
    }

}
