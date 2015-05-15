package analyze;

import java.util.HashMap;

/**
 * This class represents a labelfactory that creates labels.
 * A sigleton is used to ensure that only one factory is active.
 * @author Matthijs
 *
 */
public final class LabelFactory {

    /**
     * This variable stores the number that is given to the next label.
     */
    protected int labelCounter;

    /**
     * This variables stores the numbers of the labels already in use.
     */
    protected HashMap<String, Integer> existingLabels;

    /**
     * This variable stores the singleton labelfactory.
     */
    private static LabelFactory lf = new LabelFactory();

    /**
     * Construct a new labelfactory.
     */
    private LabelFactory() {
        labelCounter = 0;
        existingLabels = new HashMap<String, Integer>();
    }

    /**
     * This function returns a new Label, with unique number.
     * @param name      - Name of the label
     * @return          - New Label with the name.
     */
    public Label getNewLabel(String name) {

        Label result;
        if (existingLabels.containsKey(name))
            result = new Label(name, existingLabels.get(name));
        else {
            result = new Label(name, labelCounter);
            existingLabels.put(name, labelCounter);
            labelCounter++;
        }
        return result;
    }

    /**
     * THis function returns the only instance of the labelFacotory.
     * @return      - Labelfactory that is running.
     */
    public static LabelFactory getInstance() {
        return lf;
    }

    /**
     * This function gets the number that is associated with a label.
     * @param name  - Name of the label.
     * @return      - Number that is associated with the label, -1 if not found.
     */
    public int getNumberOfLabel(String name) {
        if (existingLabels.containsKey(name))
            return existingLabels.get(name);
        else
            return -1;
    }
}