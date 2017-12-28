package UnitTesting;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import application.Main;
import chatServer.Server;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GUI_Testing extends ApplicationTest{
	final String status = "OK There are currently 1 user(s) on the server You are logged im and have sent 0 message(s)";
    final String loginMessage = "OK Welcome to the chat server DURAI";
    final String listMessage = "OK DURAI, ";
    final String broadcastMessage = "Broadcast from DURAI: Hi everyone!";
    
    @Before
    public void setup() throws Exception{
    	Thread serverThread = new Thread(()->{
			Server server = new Server(9000);
		});
		serverThread.start();
		
		ApplicationTest.launch(Main.class);    	
    }
    
	@Test
	public void test() throws InterruptedException {
		//test login
		clickOn("#userName").write("DURAI");
		clickOn("#idenButton");
		Thread.sleep(1000);
		assertTrue(((TextArea) find("#messageBox")).getText().contains(loginMessage));
		
		//test Status button
		clickOn("#statusButton");
		Thread.sleep(1000);
		assertTrue(((TextArea) find("#messageBox")).getText().contains(status));
		
		//test List button
		clickOn("#getUserList");
		Thread.sleep(1000);
		assertTrue(((TextArea) find("#messageBox")).getText().contains(listMessage));
		
		//test List button
		clickOn("#sendMessageBox").write("Hi everyone!");
		clickOn("#broadcastMesg");
		Thread.sleep(1000);
		assertTrue(((TextArea) find("#messageBox")).getText().contains(broadcastMessage));
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.show();
		
	}
	
	public <T extends Node> T find(final String query) {
        return lookup(query).query();
    }

}
