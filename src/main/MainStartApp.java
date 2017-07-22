package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainStartApp extends Application {

	static Stage stage;

	private static StackPane pane = new StackPane();

	@Override
	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
		
		pane.getChildren().add(root);

		Scene scene = new Scene(pane);
		// scene.getStylesheets().add(getClass().getResource("/stylesheet.css").toExternalForm());
		stage.setTitle("Sudo Timer");
		stage.setScene(scene);
		stage.show();

	}
	
	public static void main(String args[]){
		launch(args);
	}


}
