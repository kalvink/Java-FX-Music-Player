package application;

import java.awt.event.ActionEvent;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("asd.fxml"));
			Scene scene = new Scene(root, 350, 214);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void openSoundFile(ActionEvent e) {
		System.out.println("Menu clicked");
	}
	@FXML
	public void exitApp(ActionEvent e) {
		System.out.println("Menu clicked");
	}
	@FXML
	public void openAbout(ActionEvent e) {
		System.out.println("Menu clicked");
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}
