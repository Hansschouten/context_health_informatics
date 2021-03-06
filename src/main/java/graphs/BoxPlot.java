package graphs;

import java.util.ArrayList;

import model.ColumnType;

/**
 * This class represents a boxplot, that can be drawn into a webview.
 * @author Matthijs
 *
 */
public class BoxPlot extends Graph {

    /**
     * Construct a boxplot.
     */
    public BoxPlot() {
        super("Boxplot", "/graphs/boxplot.html", true, false);

        ArrayList<ColumnType> types = new ArrayList<ColumnType>();
        types.add(ColumnType.INT);
        types.add(ColumnType.DOUBLE);

        inputs.add(new InputType("x", types, false, false));
    }

    @Override
    public String getScript(String name, String data) {
        return "drawBoxPlot('" + name + "', '" + data + "')";
    }
}
