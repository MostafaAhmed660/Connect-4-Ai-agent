package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SampleController {

	private static int kLevelNumber = 0;
	private static String playerRedName;
	private static int rowValue;
	private static int columnValue;
    private Stage primaryStage;

	@FXML
    private ToggleGroup MinimaxType;

    @FXML
    private TextField kLevel;

    @FXML
    private TextField playerName;

    @FXML
    private RadioButton withAlphBeta;

	@FXML
	private Slider rowInput;

	@FXML
	private Slider columnInput;

    @FXML
    private RadioButton withoutAlphBeta;

    @FXML
    void startMatch(MouseEvent event) {

		playerRedName = playerName.getText();
		columnValue = (int) columnInput.getValue();
		rowValue = (int) rowInput.getValue();
		Main.changeDimension(rowValue, columnValue);

		kLevelNumber = Integer.parseInt(kLevel.getText());
		Board.change_Branching_depth_k(kLevelNumber);


		if (withAlphBeta.isSelected() || withoutAlphBeta.isSelected()) {

    		//SplitPane split_pane = new SplitPane();
	        //split_pane.getItems().add(Main.createContent());
    		//Scene scene1 = new Scene(split_pane, 1280, 560);
 	        // primaryStage.setScene(scene1);
    		Board.with_bruning(withAlphBeta.isSelected());
    		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		primaryStage.setScene(new Scene(Main.createContent()));
	        primaryStage.setTitle("Connect 4 Game");
	        primaryStage.centerOnScreen();
	        primaryStage.show();
    	}
    	else {
            Alert a = new Alert(AlertType.NONE);
            a.setAlertType(AlertType.ERROR);
            a.setContentText("You Must Choose Getting Minimax with or without alpha_Beta Pruning !");
            a.show();
    	}
    }

	public static int getkLevelNumber() {
		return kLevelNumber;
	}

	public static String getPlayerRedName() {
		return playerRedName;
	}

	public static int getRowValue() {
		return rowValue;
	}

	public static int getColumnValue() {
		return columnValue;
	}

}
