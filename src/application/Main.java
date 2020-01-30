
package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	public static Window publicStage;

	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root, 350, 214);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage publicStage = primaryStage;
			publicStage.setScene(scene);
			publicStage.setResizable(false);
			publicStage.setTitle("Music Player by Kalvin Kao");
			publicStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
