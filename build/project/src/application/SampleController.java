package application;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

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

	int currentTrackNumber = 0;
	int totalSongs = 0;

	boolean isPlaying = false;
	boolean isPaused = false;
	boolean loadedSong = false;
	private Duration duration;
	List<File> file;
	String t;
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
			// File file = fileChooser.showOpenDialog(Main.publicStage);
			file = fileChooser.showOpenMultipleDialog(Main.publicStage);
			{
				if (file != null) {
					for (File f : file) {
						// open files
						totalSongs++;
					}
				}
			}
			// System.out.println(file);
			textField.setText(file.get(currentTrackNumber).getName());
			URI fpath = file.get(currentTrackNumber).toURI();
			// System.out.println(fpath);
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
		seekBar.setDisable(false);
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		isPlaying = true;
		loadedSong = true;
		changeVolume();
		playButton.setText("⏸");

		// display total duration
		mediaPlayer.setOnReady(new Runnable() {

			@Override
			public void run() {
				System.out.println("Duration: " + mediaPlayer.getTotalDuration());
				totalDuration.setText(formatDuration(mediaPlayer.getTotalDuration()));
				getTotalDuration();
				seekBar();

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
		if (loadedSong) {
			if (mediaPlayer.getCurrentTime() == mediaPlayer.getStartTime()) {

				mediaPlayer.seek(duration.ZERO);

				// System.out.println("totalSongs:" + totalSongs);
				if (currentTrackNumber == 0) {
					currentTrackNumber = totalSongs - 1;
				} else {
					currentTrackNumber--;
				}
				textField.setText(file.get(currentTrackNumber).getName());
				playSong(file.get(currentTrackNumber).toURI());
				System.out.println("currentTrackNumber:" + currentTrackNumber);
				System.out.println("===================================");

			} else if (!isPaused) {
				mediaPlayer.stop();
				mediaPlayer.play();
				seekBar();
			} else if (isPaused) {
				mediaPlayer.stop();
			}

		}
	}

	@FXML
	public void nextTrack() {
		if (loadedSong) {
			mediaPlayer.stop();
			isPlaying = false;
			mediaPlayer.seek(duration.ZERO);

			// System.out.println("totalSongs:" + totalSongs);
			if (currentTrackNumber == totalSongs - 1) {
				currentTrackNumber = 0;
			} else {
				currentTrackNumber++;
			}
			textField.setText(file.get(currentTrackNumber).getName());
			playSong(file.get(currentTrackNumber).toURI());
			System.out.println("currentTrackNumber:" + currentTrackNumber);
			System.out.println("===================================");
		}
	}

	@FXML
	public void seekBar() {

		// Listen to the slider. When it changes, seek with the player.
		// MediaPlayer.seek does nothing when the player is stopped, so the
		// play/pause button's handler
		// always sets the start time to the slider's current value, and then
		// resets it to 0 after starting the player.
		InvalidationListener sliderChangeListener = o -> {
			Duration seekTo = Duration.seconds(seekBar.getValue());
			mediaPlayer.seek(seekTo);
		};
		seekBar.valueProperty().addListener(sliderChangeListener);

		// Link the player's time to the slider
		mediaPlayer.currentTimeProperty().addListener(l -> {
			// Temporarily remove the listener on the slider, so it doesn't
			// respond to the change in playback time
			// I thought timeSlider.isValueChanging() would be useful for this,
			// but it seems to get stuck at true
			// if the user slides the slider instead of just clicking a position
			// on it.
			seekBar.valueProperty().removeListener(sliderChangeListener);

			// Keep timeText's text up to date with the slider position.
			Duration currentTime = mediaPlayer.getCurrentTime();
			int value = (int) currentTime.toSeconds();
			seekBar.setValue(value);

			// Re-add the slider listener
			seekBar.valueProperty().addListener(sliderChangeListener);

			duration = mediaPlayer.getCurrentTime();
			currentDuration.setText(formatDuration(duration));

			mediaPlayer.setOnEndOfMedia(() -> {
				nextTrack();
				/*
				 *
				 * mediaPlayer.seek(Duration.ZERO); playButton.setText("⏵");
				 * isPlaying = false; mediaPlayer.stop();
				 */

			});

		});

	}

	public void getTotalDuration() {
		Double totalDuration = mediaPlayer.getTotalDuration().toSeconds();
		seekBar.setMax(totalDuration);
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
			isPaused = true;
		} else if (!isPlaying && loadedSong) {
			playButton.setText("⏸");
			mediaPlayer.play();
			isPlaying = true;
			isPaused = false;

		}

	}

}
