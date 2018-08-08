package application.presentation;

import java.io.IOException;

import application.Main;
import application.objects.hardware.Device.DeviceType;
import application.presentation.logic.RootLayoutController;
import application.presentation.logic.SettingsLayoutController;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PresentationLayer {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private Main main;

	public PresentationLayer(Main main) {
		this.main = main;
	}

	public void showMain(Stage stage) {
		try {
			primaryStage = stage;
			primaryStage.setTitle("L3 Asset Manager: Prototype");
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("presentation/view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			RootLayoutController controller = loader.getController();
			controller.setMain(main);
			controller.setupEnvironmentTable();
			controller.setUpCheckpointsTable();
			for (DeviceType type : DeviceType.values())
				controller.setupDeviceTable(type);
			controller.initMenuBindings();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showSettings() {
		try {
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Preferences");
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("presentation/view/SettingsLayout.fxml"));
			AnchorPane settingsLayout = (AnchorPane) loader.load();
			
			Scene scene = new Scene(settingsLayout);
			
			dialogStage.setScene(scene);
			dialogStage.show();
			
			SettingsLayoutController controller = loader.getController();
			controller.setMain(main);
			controller.setupTabs();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showError(String errorTitle, String errorMsg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(errorTitle);
		alert.setContentText(errorMsg);
		
		alert.showAndWait();
	}
	
	public ReadOnlyObjectProperty<Node> focusedProperty() {
		return primaryStage.getScene().focusOwnerProperty();
	}
	
	public static class NonEditingCell<T> extends TableCell<T, String> {
		 @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty()) {
           	 this.setStyle("-fx-background-color: gray ;");
                setText(item);
            }
        }

	}
	
	public static class EditingCell<T> extends TableCell<T, String> {

       private TextField textField;

       public EditingCell() {
       }

       @Override
       public void startEdit() {
           if (!isEmpty()) {
               super.startEdit();
               createTextField();
               setText(null);
               setGraphic(textField);
               textField.selectAll();
               textField.requestFocus();
           }
       }

       @Override
       public void cancelEdit() {
           super.cancelEdit();

           setText(getItem());
           setGraphic(null);
       }

       @Override
       public void updateItem(String item, boolean empty) {
           super.updateItem(item, empty);

           if (empty) {
               setText(item);
               setGraphic(null);
           } else {
               if (isEditing()) {
                   if (textField != null) {
                       textField.setText(getString());
//                       setGraphic(null);
                   }
                   setText(null);
                   setGraphic(textField);
               } else {
                   setText(getString());
                   setGraphic(null);
               }
           }
       }
       
       @Override
       public void commitEdit(String item) {
           // This block is necessary to support commit on losing focus
           if (!isEditing() && !item.equals(getItem())) {
               TableView<T> table = getTableView();
               if (table != null) {
                   TableColumn<T, String> column = getTableColumn();
                   CellEditEvent<T, String> event = new CellEditEvent<T, String>(
                       table, new TablePosition<T,String>(table, getIndex(), column), 
                       TableColumn.editCommitEvent(), item
                   );
                   Event.fireEvent(column, event);
               }
           }

           super.commitEdit(item);
       }

       private void createTextField() {
           textField = new TextField(getString());
           textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
           textField.setOnAction((e) -> commitEdit(textField.getText()));
           textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
               if (!newValue) {
                   commitEdit(textField.getText());
               }
           });
       }

       private String getString() {
           return getItem() == null ? "" : getItem();
       }
   }
}
