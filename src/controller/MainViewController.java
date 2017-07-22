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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class MainViewController implements Initializable{

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	
		start.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
			
			
			Timer timer1 = new Timer();

		    TimerTask task = new TimerTask(){
		    	private int b = 01;
		        private int i = 9;
		        private int c = 1;
		        public void run(){
		            if ( i > 0 ) {
		            
		            	Platform.runLater(new Runnable() {
		            		
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
							
								timer.setText(0+String.valueOf(b) +":"+String.valueOf(c)+String.valueOf(i--));
								
							}
						});
		            	
		            }
		            else{
		            	
		            	if(c==0 ){
		            		
		            		Platform.runLater(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									System.out.println("Hello");
									timer.setText(0+String.valueOf(b--)+":"+String.valueOf(c)+String.valueOf(i));
									
									if(b==0){
					            		System.out.println("yes");
					            		timer1.cancel();
					            	}
								}
							});
		            		
		            	}
		            	
		            	Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
							
								
								timer.setText(0+String.valueOf(b)+":"+String.valueOf(c--)+String.valueOf(i));
								
								i=9;
								System.out.println(b+" "+c+" "+i);
								
							}
							
						});
		            	
		            }
		          
		            
		        }
		    };
		    timer1.scheduleAtFixedRate(task, 0, 1000); 
		});
		
		stop.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
			System.out.println("Yes");
		});
		
		reset.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
			System.out.println("Yes");
		});
		
		pomodoro.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
			System.out.println("Yes");
		});
		
		shortBreak.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
			System.out.println("Yes");
		});
		
		longBreak.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
			System.out.println("Yes");
		});
		
	
	}
	

}
		
		
		
		
		
		
		
		
		