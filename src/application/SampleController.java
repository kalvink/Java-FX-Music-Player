package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

public class SampleController {

	@FXML
	public void openFile(ActionEvent e) {

		try {
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Audio files", "*.mp3", "*.wav",
					"*.aac");
			fileChooser.getExtensionFilters().add(extFilter);
			fileChooser.setTitle("Open Audio File");
			File file = fileChooser.showOpenDialog(Main.publicStage);
			System.out.println(file);
			String fpath = file.toString();
			System.out.println(fpath);
		} catch (Exception ex) {
			System.out.println("Something went wrong.");
			// playSong(fpath);
		} finally {
			System.out.println("The 'try catch' is finished.");
		}
	}

	public void playSong(String fpath) {

		Media media = new Media(fpath);
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);

	}

	@FXML
	public void exitApp(ActionEvent e) {
		System.out.println("Menu clicked");
	}

	@FXML
	public void openAbout(ActionEvent e) {
		System.out.println("Menu clicked");
	}

	@FXML
	public void prevTrack(ActionEvent e) {
	}

	@FXML
	public void nextTrack(ActionEvent e) {
	}

	@FXML
	public void playPause(ActionEvent e) {

	}

}
