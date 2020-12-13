import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Simulation {

    private final FuzzyDriverController fuzzyDriverController;
    private double slipperiness;
    private final Car userCar;
    private final Car aiCar;
    private final View view;

    public Simulation(String fuzzyDriverPath, Stage primaryStage) throws FileNotFoundException {
        this.fuzzyDriverController = new FuzzyDriverController(fuzzyDriverPath);
        this.slipperiness = 0.5d;
        this.userCar = new Car(8);
        this.aiCar = new Car(0);
        this.view = new View(this, primaryStage);
    }

    public void simulate(int TIME_DIFF_MILLISECONDS){
        int currentAICarSpeed = aiCar.getSpeed();
        double aiCarSpeedChange = fuzzyDriverController.getSpeedChange(currentAICarSpeed, getCarDistance(), slipperiness);
        int aiCarSpeed = (int) (currentAICarSpeed + aiCarSpeedChange);
        aiCar.setSpeed(aiCarSpeed);
        int userCarSpeed = userCar.getSpeed();

        aiCar.move(aiCarSpeed * 1000 * TIME_DIFF_MILLISECONDS/3600000.0d);
        userCar.move(userCarSpeed * 1000 * TIME_DIFF_MILLISECONDS/3600000.0d);
    }

    public Car getUserCar() { return this.userCar; }

    public double getSlipperiness() { return this.slipperiness; }

    public void setSlipperiness(double slipperiness) { this.slipperiness = slipperiness; }

    public double getCarDistance(){
        double distance = this.userCar.getDistanceFromStart() - this.aiCar.getDistanceFromStart()
                - this.userCar.getLength()/2.0d - this.aiCar.getLength()/2.0d;
        return distance;
    }

    public int getUserCarSpeed() {
        return userCar.getSpeed();
    }

    public int getAICarSpeed() {
        return aiCar.getSpeed();
    }

    public void decreaseSlipperiness(double difference){
        this.slipperiness -= difference;
    }

    public void increaseSlipperiness(double difference){
        this.slipperiness += difference;
    }

    public void decreaseUserCarSpeed(int difference){
        int speed = userCar.getSpeed();
        userCar.setSpeed(speed - difference);
    }

    public void increaseUserCarSpeed(int difference){
        decreaseUserCarSpeed(-difference);
    }

    public double getUserCarLength(){
        return userCar.getLength();
    }

    public double getAICarLength(){
        return aiCar.getLength();
    }

    public double getUserCarDistance(){
        return userCar.getDistanceFromStart();
    }

    public double getAICarDistance(){
        return aiCar.getDistanceFromStart();
    }
}
