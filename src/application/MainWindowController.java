package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
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
	private TextField recipientName = new TextField();
	
	@FXML
	private TextField userName = new TextField();
	
	@FXML
	private Button sendButton = new Button();
	
	@FXML
	private Button broadcastMesg = new Button();
	
	@FXML
	private Button statusButton = new Button();
	
	@FXML
	private Button getUserList = new Button();
	
	@FXML
	private Button quitButton = new Button();
	
	@FXML
	private Button idenButton = new Button();
	
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		//disable other fields until user logs in
		messageBox.setEditable(false);
		
		recipientName.setDisable(true);
		sendButton.setDisable(true);
		sendMessageBox.setDisable(true);
		
		
		sendButton.setOnAction(value -> {
			canPrintFlag = true;
			out.println("MESG "+recipientName.getText()+" "+sendMessageBox.getText());
			
		});
		
		idenButton.setOnAction(value -> {
			canPrintFlag = true;
			out.println("IDEN "+userName.getText());
			
			idenButton.setDisable(true);
			userName.setDisable(true);			
			
			recipientName.setDisable(false);
			sendButton.setDisable(false);
			sendMessageBox.setDisable(false);
			
		});
		
		broadcastMesg.setOnAction(value -> {
			canPrintFlag = true;
			out.println("HAIL "+sendMessageBox.getText());			
		});
		
		statusButton.setOnAction(value -> {
			canPrintFlag = true;
			out.println("STAT");			
		});
		
		getUserList.setOnAction(value -> {
			canPrintFlag = true;
			out.println("LIST");			
		});
		
		quitButton.setOnAction(value -> {
			canPrintFlag = true;
			out.println("QUIT");			
			
			idenButton.setDisable(false);
			userName.setDisable(false);
			
			try {
				myClient.close();				
				System.exit(0);
				Platform.exit();
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
				if(!answer.equals("")){
					messageBox.appendText(answer+"\n\n");
					canPrintFlag = false;
				}
				
			}
        }
		}, 0, 1000);
		
	}
	
}
