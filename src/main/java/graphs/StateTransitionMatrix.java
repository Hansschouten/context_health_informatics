package graphs;

import java.util.ArrayList;

import model.ColumnType;

/**
 * This class represents a stateTransition matrix that can be drawn in a WebView.
 * @author Matthijs
 *
 */
public class StateTransitionMatrix extends Graph {

    /**
     * Construct a new state transition matrix that can be drawn in a WebView.
     */
    protected StateTransitionMatrix() {
        super("State Transition Matrix", "/graphs/transition_matrix.html", true, false);
        ArrayList<ColumnType> types = new ArrayList<ColumnType>();
        inputs.add(new InputType("x", types, true, false));
    }

    @Override
    public String getScript(String name, String data) {
        return "drawTransitionMatrix('" + name + "', '" + data + "')";
    }

}
