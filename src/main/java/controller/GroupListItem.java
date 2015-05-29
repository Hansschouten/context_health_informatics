package controller;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


/**
 * The list item for the list of groups.
 * @author Remi
 *
 */
public class GroupListItem extends CustomListItem {
	/**
	 * The textfield for entering the name of the group list item.
	 */
	protected TextField txtField;

	/**
	 * The combobox for choosing the delimiter.
	 */
	protected ComboBox<String> box = new ComboBox<String>();

	/**
	 * The primary key name for this group.
	 */
	protected String primKey = "File name";

	/**
	 * The list of columns for this group.
	 */
	protected ObservableList<ColumnListItem> columnList = FXCollections.observableArrayList();
	/**
	 * The list of files for this group.
	 */
	protected ObservableList<FileListItem> fileList = FXCollections.observableArrayList();

	/**
	 * The listviews containing the data and information for this group.
	 */
	protected ListView<? extends CustomListItem> columnListView, fileListView;
	/**
	 * A list item for the group list view.
	 * @param par The list view where the group list items are shown.
	 * @param files The list view where the file list items are shown.
	 * @param columns The list view where the column list items are shown.
	 * @param cboxOptions The list of delimiters
	 */
	public GroupListItem(ListView<? extends CustomListItem> par, ListView<? extends CustomListItem> files,
			ListView<? extends CustomListItem> columns, ObservableList<String> cboxOptions) {
		super(par);

		this.setPadding(new Insets(8));

		fileListView = files;
		columnListView = columns;

		txtField = createTextField("Name");

		box.setItems(cboxOptions);
		box.setValue(cboxOptions.get(0));

		setupRemove(true);
		this.getChildren().addAll(txtField, box, remove);
	}

	/**
	 * Returns a list of column names.
	 * @return The list of column names.
	 */
	public List<String> getColumnNames() {
		return columnList.stream().map(x -> x.txtField.getText()).collect(Collectors.toList());
	}

	@Override
	public void select() {
		parent.getSelectionModel().select(parent.getItems().indexOf(this));
		txtField.requestFocus();
	}

	@Override
	public void selectNext() {
		int idx = parent.getItems().indexOf(this);
		if (parent.getItems().size() - 1 <= idx) {
			// If this is the last item, add a new one.
			@SuppressWarnings("unchecked")
			ObservableList<GroupListItem> list = (ObservableList<GroupListItem>) parent.getItems();
			list.add(new GroupListItem(parent, fileListView, columnListView, box.getItems()));
		}
		parent.getItems().get(idx + 1).select();
	}
}
