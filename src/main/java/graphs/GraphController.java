package graphs;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import controller.MainApp;
import controller.MainApp.NotificationStyle;
import model.Column;
import model.SequentialData;
import model.Writer;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

/**
 * This class controls the interface for the graphsview of the program.
 * @author Matthijs
 *
 */
public class GraphController {

    /** This variable stores the graphApplication that uses this controller. */
    protected MainApp graphApp;

    /** The web view to create and view graphs. */
    @FXML
    private WebView webView;

    /** This variable stores the combobox that selects the graph. */
    @FXML
    private ComboBox<String> graphSelector;

    /** This variable stores the listview with the required data. */
    @FXML
    private ListView requiredData;

    /** This variable stores the addbutton that adds a new input to the graph. */
    @FXML
    private Button addButton;

    /** This variable stores the textfield that stores the name. */
    @FXML
    private TextField graphName;

    /** This variable stores the viewSelect of the combobox. */
    @FXML
    private ComboBox<String> viewSelect;

    /** This variable stores all the graphs that are available. */
    protected ArrayList<Graph> availableGraphs;

    /** This variable stores the dataholder, that stores the data to draw the graph with. */
    protected GraphDataTransformer dataholder;

    /**
     * This method creates a new graphController.
     */
    public GraphController() {
        availableGraphs = new ArrayList<Graph>();
        dataholder = new GraphDataTransformer();
    }

    /** This method clears the view to the homescreen. */
    @FXML
    public void clear() {
        setupWebView();
    }

    /** This method adds an input field to the required inputs. */
    @FXML
    public void addInput() {

        Graph selectedGraph = availableGraphs.get(graphSelector.getSelectionModel().getSelectedIndex());
        InputType type;
        try {
            type = selectedGraph.getAddableItem();
            requiredData.getItems().add(new InputListItem(requiredData, type, dataholder.getDataColumns(), true));
        } catch (GraphException e) {
            graphApp.showNotification(e.getMessage(), NotificationStyle.WARNING);
            e.printStackTrace();
        }
    }

    /**
     * This method changes the required data based on the selected item.
     */
    @FXML
    public void graphSelected() {
        Graph selectedGraph = availableGraphs.get(graphSelector.getSelectionModel().getSelectedIndex());
        setRequiredInput(selectedGraph);
    }

    /**
     * This method sets the required input for the selected graph.
     * @param selectedGraph     - Graph that is selected.
     */
    protected void setRequiredInput(Graph selectedGraph) {
        ArrayList<InputType> inputTypes = selectedGraph.getRequiredInputs();
        requiredData.getItems().clear();
        Column[] cols = dataholder.getDataColumns();

        for (InputType type: inputTypes) {
            requiredData.getItems().add(new InputListItem(requiredData, type, cols, false));
        }

        if (selectedGraph.hasFixedSize()) {
            addButton.setDisable(true);
        } else {
            addButton.setDisable(false);
        }
    }


    /** This method exports the graph as a SVG image. */
    @FXML
    public void exportSVG() {
        drawGraph();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save SVG");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("SVG File(*.svg)", "*.svg"));
        File file = fileChooser.showSaveDialog(graphApp.getPrimaryStage());
        try {
            Writer.writeFile(file, (String) webView.getEngine().executeScript("export_svg()"));
        } catch (IOException e) {
            graphApp.showNotification("Oops exporting SVG failed", NotificationStyle.WARNING);
        }
    }

    /**This method draws the graph, when the button is pressed. */
    @FXML
    public void drawGraph() {

        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<String> inputNames = new ArrayList<String>();

        for (Object item: requiredData.getItems()) {
            InputListItem listItem = (InputListItem) item;
            if (listItem.getSelectedColumn() == null) {
                graphApp.showNotification("You have not selected an item for each inputfield.",
                        NotificationStyle.WARNING);
                return;
            }
            inputNames.add(listItem.getinputName());
            columns.add(listItem.getSelectedColumn());
        }

        Graph selected = availableGraphs.get(graphSelector.getSelectionModel().getSelectedIndex());
        String data = dataholder.getJSONFromColumn(columns, inputNames,
                viewSelect.getSelectionModel().getSelectedItem(), selected.singleValuesAllowed);

        selected.drawInWebView(webView, data, graphName.getText());
    }

    /** This method initialises the controller linked with the GUI. */
    @FXML
    protected void initialize() {
        setupWebView();

        addGraph(new BoxPlot());
        addGraph(new LineChart());
        addGraph(new BarChart());
        addGraph(new StemAndLeavePlot());
        addGraph(new Histogram());
        addGraph(new FrequencyBar());
        addGraph(new StateTransitionMatrix());

        viewSelect.getItems().add("All Data");
        viewSelect.getItems().add("Per Chunk");
        viewSelect.getSelectionModel().select(1);

        graphSelector.getSelectionModel().select(0);
        graphSelected();
    }

    /**
     * Sets up the graph options, to choose the axis' and graph style.
     */
    protected void setupWebView() {
        String url = this.getClass().getResource("/graphs/index.html").toExternalForm();
        webView.getEngine().load(url);
    }

    /**
     * This method adds a graph to the graphList and to the gui.
     * @param graph     - Graph to Add.
     */
    protected void addGraph(Graph graph) {
        availableGraphs.add(graph);
        graphSelector.getItems().add(graph.toString());
    }

    /**
     * This method updates the data to draw the columns with.
     * @param data          - Data to update.
     */
    public void updateData(SequentialData data) {
        dataholder.setData(data);
        graphSelected();
    }

    /**
     * This method sets the mainApp for the controller.
     * @param ma        - Main app of this controller.
     */
    public void setMainApp(MainApp ma) {
        graphApp = ma;
    }
}
