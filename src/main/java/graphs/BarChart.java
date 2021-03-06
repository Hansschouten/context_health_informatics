package graphs;

import java.util.ArrayList;

import model.ColumnType;

/**
 * This class represents a barchart that can be viewed using a webview.
 * @author Matthijs
 *
 */
public class BarChart extends Graph {

    /** This variable stores the inputnumber of the line. */
    protected int inputNumber;

    /** This variable stores accepted inputtypes. */
    protected ArrayList<ColumnType> bartypes;

    /**
     * Constructs a new barchart object, that can be drawn in a webview.
     */
    public BarChart() {
        super("Bar Chart", "/graphs/barchart.html", false, false);
        inputNumber = 0;

        bartypes = new ArrayList<ColumnType>();
        bartypes.add(ColumnType.INT);
        bartypes.add(ColumnType.DOUBLE);

        ArrayList<ColumnType> xtypes = new ArrayList<ColumnType>();
        xtypes.add(ColumnType.DATE);
        xtypes.add(ColumnType.STRING);
        xtypes.add(ColumnType.INT);
        xtypes.add(ColumnType.DOUBLE);

        inputs.add(new InputType("x", xtypes, true, true));
        inputs.add(new InputType("bar " + inputNumber, bartypes, false, false));
        inputNumber++;
    }

    @Override
    public InputType getAddableItem() {
        InputType result =  new InputType("bar " + inputNumber, bartypes, false, false);
        inputNumber++;
        return result;
    }

    @Override
    public String getScript(String name, String data) {
        return "drawBarChart('" + name + "', '" + data + "')";
    }
}
