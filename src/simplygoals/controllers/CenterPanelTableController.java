package simplygoals.controllers;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import simplygoals.modelComponents.Goal;
public class CenterPanelTableController implements Initializable {

	    private MainPanelController mainControl = new MainPanelController();
	    @FXML
	    private TableColumn<Goal, String> plannedEndColumn;

	    @FXML
	    private TableColumn<Goal, String> notesColumn;

	    public TableColumn<Goal, String> getPlannedEndColumn() {
			return plannedEndColumn;
		}
		public void setPlannedEndColumn(TableColumn<Goal, String> plannedEndColumn) {
			this.plannedEndColumn = plannedEndColumn;
		}
		public TableColumn<Goal, String> getNotesColumn() {
			return notesColumn;
		}
		public void setNotesColumn(TableColumn<Goal, String> notesColumn) {
			this.notesColumn = notesColumn;
		}
		public TableColumn<Goal, String> getExecutedColumn() {
			return executedColumn;
		}
		public void setExecutedColumn(TableColumn<Goal, String> executedColumn) {
			this.executedColumn = executedColumn;
		}
		public TableColumn<Goal, String> getRealEndColumn() {
			return realEndColumn;
		}
		public void setRealEndColumn(TableColumn<Goal, String> realEndColumn) {
			this.realEndColumn = realEndColumn;
		}
		public TableColumn<Goal, String> getCategoryColumn() {
			return categoryColumn;
		}
		public void setCategoryColumn(TableColumn<Goal, String> categoryColumn) {
			this.categoryColumn = categoryColumn;
		}
		public TableColumn<Goal, String> getTypeColumn() {
			return typeColumn;
		}
		public void setTypeColumn(TableColumn<Goal, String> typeColumn) {
			this.typeColumn = typeColumn;
		}
		@FXML
	    private TableColumn<Goal, String> nameColumn;

	    @FXML
	    private TableColumn<Goal, String> executedColumn;

	    @FXML
	    private TableColumn<Goal, String> realEndColumn;

	    @FXML
	    private TableColumn<Goal, String> categoryColumn;

	    @FXML
	    private TableColumn<Goal, String> typeColumn;

	    @FXML
	    private TableView<Goal> CenterPanelTableView;
		public TableView<Goal> getCenterPanelTableView() {
			return CenterPanelTableView;
		}
		public void setCenterPanelTableView(TableView<Goal> centerPanelTableView) {
			CenterPanelTableView = centerPanelTableView;
		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
	    	getNameColumn().setCellValueFactory(cellData -> cellData.getValue().nameProperty());
			getPlannedEndColumn().setCellValueFactory(cellData -> cellData.getValue().plannedDateOfEndProperty().asString());
			getRealEndColumn().setCellValueFactory(cellData -> cellData.getValue().realEndDateProperty().asString());
			getCategoryColumn().setCellValueFactory(cellData -> cellData.getValue().categoryProperty().asString());
			getTypeColumn().setCellValueFactory(cellData -> cellData.getValue().typeProperty().asString());
			getExecutedColumn().setCellValueFactory(cellData -> cellData.getValue().executedProperty().asString());
			getNotesColumn().setCellValueFactory(cellData -> cellData.getValue().notesProperty());
			getNameColumn().setCellFactory(column -> {
			    return new TableCell<Goal, String>() {
			        @Override
			        protected void updateItem(String item, boolean empty) {
			            super.updateItem(item, empty);
			            if (item == null || empty) {
			                setText(null);
			                setStyle("");
			            } else {
			            	String name =item;
			            	name=name.substring(0, 1).toUpperCase() + name.substring(1);
			                setText(name);
			            }
			        }
			    };
			});
		}
		public void setMainControl(MainPanelController mainPanel){

		}
		public TableColumn<Goal, String> getNameColumn() {
			return nameColumn;
		}
		public void setNameColumn(TableColumn<Goal, String> nameColumn) {
			this.nameColumn = nameColumn;
		}

	

}
