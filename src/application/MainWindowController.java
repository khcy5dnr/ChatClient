package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainWindowController extends Main implements Initializable{
	
	@FXML
	private TextArea messageBox = new TextArea();
	
	@FXML
	private TextField sendMessageBox = new TextField();
	
	@FXML
	private Button sendButton = new Button();
	
	Socket myClient = null;
	BufferedReader input = null;
	PrintWriter out = null;
	String answer = null;
	
	private boolean canPrintFlag = true;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			myClient = new Socket("DURAI23", 9000);
			input = new BufferedReader(new InputStreamReader(myClient.getInputStream()));
			out = new PrintWriter(myClient.getOutputStream(), true);
			answer = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		messageBox.setEditable(false);
		
		sendButton.setOnAction(value -> {
			canPrintFlag = true;
			out.println(sendMessageBox.getText());
			try {
				answer = input.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});
		
		new Timer().schedule(new TimerTask() {

        @Override
        public void run() {
        	try {
        		if(input.ready()){
        			answer = input.readLine();
        			canPrintFlag = true;
        			
        		}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(answer != null && canPrintFlag){
				System.out.println("test");
				messageBox.appendText(answer+"\n\n");
				canPrintFlag = false;
			}
        }
    }, 0, 1000);
		
	}
	
}
