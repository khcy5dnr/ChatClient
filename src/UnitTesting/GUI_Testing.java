package UnitTesting;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import application.Main;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GUI_Testing extends ApplicationTest{
	
    @Before
    public void setup() throws Exception{
    	ApplicationTest.launch(Main.class);
    }
    
	@Test
	public void test() throws InterruptedException {
		//test login
		clickOn("#userName").write("DURAI");
		clickOn("#idenButton");
		Thread.sleep(3000);
		System.out.println(((TextField) find("#userName")).getText());
		
		//test Status button
		clickOn("#statusButton");
		Thread.sleep(3000);
		System.out.println(((TextArea) find("#messageBox")).getText());
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.show();
		
	}
	
	public <T extends Node> T find(final String query) {
        return lookup(query).query();
    }

}
