package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MainViewController implements Initializable {

	@FXML
	private BorderPane borderPane;

	@FXML
	private Label timer;

	@FXML
	private JFXButton start;

	@FXML
	private JFXButton stop;

	@FXML
	private JFXButton reset;

	@FXML
	private JFXButton pomodoro;

	@FXML
	private JFXButton shortBreak;

	@FXML
	private JFXButton longBreak;

	@FXML
	private Label imageSlide;

	@FXML
	private Label volume;

	private int currentMinutes = 10;
	private int currentSeconds = 00;

	private int selectedMinutes = 10;

	String path = MainViewController.class.getResource("/sound1.mp3").toString();
	private Media media;
	private MediaPlayer mp;

	public Timer timer1;
	public TimerTask task;

	private int picChaneCounter=1;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		timer.setText("00:00");
		
		Timer timerForPic = new Timer();
		TimerTask taskOfPic = new TimerTask() {

			public void run() {

				Platform.runLater(new Runnable() {

					@Override
					public void run() {

					
							imageSlide.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/pic" + ++picChaneCounter+".png"))));
							imageSlide.setOpacity(0.8);
							if(picChaneCounter==8){
								picChaneCounter=0;
							}
					}
				});

			}
		};
		timerForPic.scheduleAtFixedRate(taskOfPic, 0, 10000);

		volume.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/volumeImute.png"))));

		volume.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

			if (mp.isMute() == false) {

				volume.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/volumeMute.png"))));
				mp.setMute(true);
			} else {

				volume.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/volumeImute.png"))));
				mp.setMute(false);
			}
		});

		reset.setDisable(true);
		stop.setDisable(true);
		pomodoro.setDisable(true);
		shortBreak.setDisable(true);
		longBreak.setDisable(true);

		start.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

			startAction();

			start.setDisable(true);

		});

		// -----------------------------------------------------------------------
		stop.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

			stopAction();

			start.setDisable(false);
		});

		// ----------------------------------------------------------------------------
		reset.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

			start.setDisable(false);
			resetAction();
		});

		// ----------------------------------------------------------------------------
		pomodoro.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

			pomodoroAction();
		});

		// ---------------------------------------------------------------------------

		shortBreak.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

			shortBreakAction();
		});

		// --------------------------------------------------------------------------
		longBreak.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

			longBreakAction();
		});

	}

	public void setTimer(int minutes, int seconds) {

		timer1 = new Timer();
		task = new TimerTask() {

			private int b = minutes;
			private int i = seconds;

			public void run() {

				Platform.runLater(new Runnable() {

					@Override
					public void run() {

						if (i == 0) {

							// TODO Auto-generated method stub
							if ((i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8
									|| i == 9)
									&& (b == 0 || b == 1 || b == 2 || b == 3 || b == 4 || b == 5 || b == 6 || b == 7
											|| b == 8 || b == 9)) {

								timer.setText(0+"" + String.valueOf(b--) + ":0" + String.valueOf(i));
							} else if ((i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7
									|| i == 8 || i == 9)) {

								timer.setText(String.valueOf(b--) + ":0" + String.valueOf(i));
							} else if (b == 0 || b == 1 || b == 2 || b == 3 || b == 4 || b == 5 || b == 6 || b == 7
									|| b == 8 || b == 9) {

								timer.setText(0 + "" + String.valueOf(--b) + ":" + String.valueOf(i));
							} else {

								timer.setText(String.valueOf(b--) + ":" + String.valueOf(i));
							}

							currentMinutes = b;
							currentSeconds = i;

							i = 60;

						}

						if (i > 0) {

							if ((i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8
									|| i == 9)
									&& (b == 0 || b == 1 || b == 2 || b == 3 || b == 4 || b == 5 || b == 6 || b == 7
											|| b == 8 || b == 9)) {

								timer.setText(0 + "" + String.valueOf(b) + ":0" + String.valueOf(--i));
							} else if ((i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7
									|| i == 8 || i == 9)) {

								timer.setText(String.valueOf(b) + ":0" + String.valueOf(--i));
							} else if (b == 0 || b == 1 || b == 2 || b == 3 || b == 4 || b == 5 || b == 6 || b == 7
									|| b == 8 || b == 9) {

								timer.setText(0 + "" + String.valueOf(b) + ":" + String.valueOf(--i));
							} else {

								timer.setText(String.valueOf(b) + ":" + String.valueOf(--i));
							}

							currentMinutes = b;
							currentSeconds = i;

							if (b == 0 && i == 0) {
								timer.setText(0 + "" + String.valueOf(0) + ":0" + String.valueOf(0));
								timer1.cancel();

								mp.play();

								start.setDisable(false);


							}

							if (b < 0 || i < 0) {

								timer1.cancel();
								task.cancel();
								timer.setText(0 + "" + String.valueOf(0) + ":0" + String.valueOf(0));
							}
						}
					}
				});

			}
		};
		timer1.scheduleAtFixedRate(task, 0, 1000);
	}

	public void startAction() {

		timer1 = null;
		task = null;

		if (currentMinutes <= 0 && currentSeconds <= 0) {
			currentMinutes = 10;
			currentSeconds = 0;

		}
		setTimer(currentMinutes, currentSeconds);

		media = new Media(path);
		mp = new MediaPlayer(media);

		reset.setDisable(false);
		stop.setDisable(false);
		pomodoro.setDisable(false);
		shortBreak.setDisable(false);
		longBreak.setDisable(false);
	}

	public void stopAction() {

		timer1.cancel();
		task.cancel();

		mp.stop();

	}

	public void resetAction() {

		
		timer1.cancel();
		task.cancel();

		timer.setText("00:00");
		
		if(selectedMinutes<10){
			timer.setText(0+""+String.valueOf(selectedMinutes) + ":00");
		}else{
			timer.setText(String.valueOf(selectedMinutes) + ":00");
		}
		currentMinutes = selectedMinutes;
		currentSeconds = 0;

		mp.stop();
	}

	public void pomodoroAction() {

		timer1.cancel();
		task.cancel();
		setTimer(25, 00);

		selectedMinutes = 25;

		mp.stop();
	}

	public void shortBreakAction() {

		timer1.cancel();
		task.cancel();
		setTimer(05, 00);

		selectedMinutes = 5;

		mp.stop();

	}

	public void longBreakAction() {

		timer1.cancel();
		task.cancel();
		setTimer(10, 00);

		selectedMinutes = 10;

		mp.stop();
	}

}
