package lab3;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public abstract class Main extends Application {
	
	public static void start() throws Exception {
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Lab4 - Wolfram Alpha");
		WebView webView = new WebView();
		WebEngine engine = webView.getEngine(); 
		engine.load("https://www.wolframalpha.com/input/?i=plot+sin(sqrt(x%5E2%2By%5E2))+for+x+between+-20+and+20+for+y+between+-20+and+20");
		Scene scene = new Scene(webView,1366,700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
