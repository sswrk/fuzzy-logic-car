import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class FuzzyCarApp extends Application {

    @Override
    public void start(Stage primaryStage) throws InterruptedException, FileNotFoundException {
        String fuzzyDriverPath = "src/main/resources/fuzzyDriver.fcl";
        Simulation simulation = new Simulation(fuzzyDriverPath, primaryStage);
    }

    public static void runApplication(String[] args) {
        launch(args);
    }
}
