public class Car {

    private int speed;
    private final double length;
    private double distanceFromStart;

    public Car(double distanceFromStart){
        this.speed = 80;
        this.length = 4.5d;
        this.distanceFromStart = distanceFromStart;
    }

    public double getLength() { return this.length; }

    public int getSpeed() { return this.speed; }

    public double getDistanceFromStart() { return this.distanceFromStart; }

    public void setSpeed(int speed) { this.speed = speed; }

    public void move(double distance) { this.distanceFromStart += distance; }
}
