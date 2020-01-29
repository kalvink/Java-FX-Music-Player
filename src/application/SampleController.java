package application;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URI;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class SampleController extends Thread {
	MediaPlayer mediaPlayer;
	@FXML
	Slider volumeSlider;
	@FXML
	Text textField;
	@FXML
	Button playButton;
	@FXML
	Text currentDuration;
	@FXML
	Text totalDuration;

	boolean isPlaying = false;
	boolean loadedSong = false;
	private Duration duration;
	Duration startDuration;

	// Add time slider
	@FXML
	Slider seekBar;

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
			textField.setText(file.getName());
			URI fpath = file.toURI();
			System.out.println(fpath);
			// stops current song so it doesn't play two songs at same time
			if (isPlaying) {
				mediaPlayer.stop();
			}
			playSong(fpath);

		} catch (Exception ex) {
			System.out.println("Something went wrong.");
		} finally {
			System.out.println("The 'try catch' is finished.");

		}
	}

	// play song
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
		isPlaying = true;
		loadedSong = true;
		changeVolume();
		changePlayBTN();

		// display total duration
		mediaPlayer.setOnReady(new Runnable() {

			@Override
			public void run() {
				System.out.println("Duration: " + mediaPlayer.getTotalDuration());
				totalDuration.setText(formatDuration(mediaPlayer.getTotalDuration()));

			}
		});
	}

	public void changeVolume() {
		try {

			volumeSlider.setValue(mediaPlayer.getVolume() * 100);
			volumeSlider.valueProperty().addListener(new InvalidationListener() {

				public void invalidated(Observable observable) {
					mediaPlayer.setVolume(volumeSlider.getValue() / 100);
				}

			});
		} catch (Exception ex) {
		}

	}

	public void changePlayBTN() {
		playButton.setText("⏸");
	}

	@FXML
	public void exitApp(ActionEvent e) {
		Platform.exit();
	}

	@FXML
	public void openAbout(ActionEvent e) {
		Hyperlink linkedin = new Hyperlink("https://www.linkedin.com/in/kalvinkao/");
		Hyperlink github = new Hyperlink("https://github.com/kalvink");

		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		Window primaryStage = null;
		dialog.initOwner(primaryStage);
		VBox dialogVbox = new VBox(20);
		dialogVbox.getChildren().add(new Text(" Music Player by Kalvin Kao"));
		dialogVbox.getChildren().add(linkedin);
		dialogVbox.getChildren().add(github);
		Scene dialogScene = new Scene(dialogVbox, 300, 100);
		dialog.setResizable(false);
		dialog.setTitle("About");
		dialog.setScene(dialogScene);
		dialog.show();

		linkedin.setOnAction((ActionEvent ex) -> {

		});

		github.setOnAction((ActionEvent ex) -> {
			System.out.println("This link is clicked");
		});
	}

	@FXML
	public void prevTrack() {
		try {
			mediaPlayer.stop();
			mediaPlayer.play();
		} catch (Exception ex) {
		}
	}

	@FXML
	public void nextTrack(ActionEvent e) {
	}

	@FXML
	public void seekBar() {

		duration = mediaPlayer.getCurrentTime();
		String t = formatDuration(duration);
		System.out.println(t);
		currentDuration.setText(t);

	}

	// Format duration into HH:MM:SS format
	public static String formatDuration(Duration duration) {
		long seconds = (long) duration.toSeconds();
		long absSeconds = Math.abs(seconds);
		String positive = String.format("%d:%02d:%02d", absSeconds / 3600, (absSeconds % 3600) / 60, absSeconds % 60);
		return seconds < 0 ? "-" + positive : positive;
	}

	@FXML
	public void playPause(ActionEvent e) {

		if (isPlaying) {
			playButton.setText("⏵");
			mediaPlayer.pause();
			isPlaying = false;
		} else if (!isPlaying && loadedSong) {
			playButton.setText("⏸");
			mediaPlayer.play();
			isPlaying = true;

		}

	}

}
