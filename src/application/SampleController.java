package application;

import java.applet.Applet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Paths;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

public class SampleController {
	MediaPlayer mediaPlayer;

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
			URI fpath = file.toURI();
			System.out.println(fpath);
			playSong(fpath);

		} catch (Exception ex) {
			System.out.println("Something went wrong.");
		} finally {
			System.out.println("The 'try catch' is finished.");

		}
	}

	public void playSong(URI fpath) {
		Media media = null;
		try {
			media = new Media(fpath.toURL().toExternalForm());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mediaPlayer = new MediaPlayer(media);
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
