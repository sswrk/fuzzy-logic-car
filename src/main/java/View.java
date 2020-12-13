import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

public class View {

    private final int TIME_DIFF_MILLISECONDS = 1000 / 60;
    private final int METER = 20;

    private final String userCarTexturePath = "src/main/resources/user_car.png";
    private final String aiCarTexturePath = "src/main/resources/ai_car.png";
    private final Stage stage;
    private Group root;

    private ImageView userCarImageView;
    private ImageView aiCarImageView;
    private Text currentSlipperiness;
    private Text currentUserCarSpeed;
    private Text currentAICarSpeed;
    private Text currentDistance;

    private final Simulation simulation;

    public View(Simulation simulation, Stage stage) throws FileNotFoundException {
        this.simulation = simulation;
        this.stage = stage;
        initializeSimulation();
        animateSimulation();
    }

    private void initializeSimulation() throws FileNotFoundException {
        root = new Group();
        stage.setTitle("Fuzzy Logic Car");

        VBox simulationView = new VBox();
        Canvas roadView = new Canvas(1000, 250);

        Image userCarImage = new Image(new FileInputStream(userCarTexturePath));
        userCarImageView = new ImageView(userCarImage);
        double originalUserCarImageWidth = userCarImage.getWidth();
        userCarImageView.setFitWidth(simulation.getUserCarLength() * METER);
        userCarImageView.setFitHeight(userCarImage.getHeight() * userCarImageView.getFitWidth()/originalUserCarImageWidth);
        userCarImageView.setY(250 - userCarImageView.getFitHeight());
        userCarImageView.setX(1000 - userCarImageView.getFitWidth());

        Image aiCarImage = new Image(new FileInputStream(aiCarTexturePath));
        aiCarImageView = new ImageView(aiCarImage);
        double originalAICarImageWidth = aiCarImage.getWidth();
        aiCarImageView.setFitWidth(simulation.getAICarLength() * METER);
        aiCarImageView.setFitHeight(aiCarImage.getHeight() * aiCarImageView.getFitWidth()/originalAICarImageWidth);
        aiCarImageView.setY(250 - aiCarImageView.getFitHeight());
        aiCarImageView.setX(1000 - aiCarImageView.getFitWidth() - userCarImageView.getFitWidth() - simulation.getCarDistance() * METER);

        HBox bottomPanel = new HBox();
        bottomPanel.setPrefHeight(200);
        bottomPanel.setBackground(new Background(new BackgroundFill(Color.DIMGRAY,
                CornerRadii.EMPTY,
                Insets.EMPTY)));

        VBox slipperinessView = new VBox();
        HBox slipperinessControl = new HBox();
        currentSlipperiness = new Text(Double.toString(simulation.getSlipperiness()));
        Button decreaseSlipperiness = new Button("-");
        decreaseSlipperiness.setOnAction(actionEvent -> simulation.decreaseSlipperiness(0.1d));
        Button increaseSlipperiness = new Button("+");
        increaseSlipperiness.setOnAction(actionEvent -> simulation.increaseSlipperiness(0.1d));
        slipperinessControl.getChildren().addAll(decreaseSlipperiness, currentSlipperiness, increaseSlipperiness);
        slipperinessView.getChildren().addAll(new Text("Slipperiness"), slipperinessControl);

        VBox aiCarView = new VBox();
        HBox aiCarSpeedControl = new HBox();
        currentAICarSpeed = new Text(Double.toString(simulation.getAICarSpeed()));
        aiCarSpeedControl.getChildren().addAll(currentAICarSpeed);
        aiCarView.getChildren().addAll(new Text("Fuzzy Logic Car"), aiCarSpeedControl);

        VBox userCarView = new VBox();
        HBox userCarSpeedControl = new HBox();
        currentUserCarSpeed = new Text(Double.toString(simulation.getUserCarSpeed()));
        Button decreaseSpeed = new Button("-");
        decreaseSpeed.setOnAction(actionEvent -> simulation.decreaseUserCarSpeed(1));
        Button increaseSpeed = new Button("+");
        increaseSpeed.setOnAction(actionEvent -> simulation.increaseUserCarSpeed(1));
        userCarSpeedControl.getChildren().addAll(decreaseSpeed, currentUserCarSpeed, increaseSpeed);
        userCarView.getChildren().addAll(new Text("User Car"), userCarSpeedControl);

        VBox distanceView = new VBox();
        currentDistance = new Text(Double.toString(simulation.getCarDistance()));
        distanceView.getChildren().addAll(new Text("Distance"), currentDistance);

        bottomPanel.getChildren().addAll(slipperinessView, aiCarView, userCarView, distanceView);
        simulationView.getChildren().addAll(roadView, bottomPanel);
        root.getChildren().addAll(userCarImageView, aiCarImageView, simulationView);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void animateSimulation(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(TIME_DIFF_MILLISECONDS), event -> {
            simulation.simulate(TIME_DIFF_MILLISECONDS);
            updateSimulationView();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateSimulationView(){
        updateCarPositions();
        updateBottomPanel();
    }

    private void updateCarPositions(){
        aiCarImageView.setX(1000 - aiCarImageView.getFitWidth() - userCarImageView.getFitWidth() - simulation.getCarDistance() * METER);
    }

    private void updateBottomPanel(){
        DecimalFormat df = new DecimalFormat("#.##");
        currentSlipperiness.setText(df.format(simulation.getSlipperiness()));
        currentUserCarSpeed.setText(Integer.toString(simulation.getUserCarSpeed()));
        currentAICarSpeed.setText(Integer.toString(simulation.getAICarSpeed()));
        currentDistance.setText(df.format(simulation.getCarDistance()));
    }

}
