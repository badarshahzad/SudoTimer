package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainStartApp extends Application {

	static Stage stage;

	private static StackPane pane = new StackPane();

	@Override
	public void start(Stage stage) throws Exception {

		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		



		pane.getChildren().add(root);

		Scene scene = new Scene(pane);

		stage.setTitle("Sudo Timer");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setFullScreen(false);
		stage.show();

	}

	public static void main(String args[]) {
		launch(args);
		System.exit(1);
	}

}
