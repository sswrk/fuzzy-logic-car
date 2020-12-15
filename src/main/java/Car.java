public class Car {

    private int speed;
    private final double length;
    private double distanceFromStart;
    private int maxSpeed;

    public Car(double distanceFromStart, int speed, int maxSpeed){
        this.maxSpeed = maxSpeed;
        if(speed <= maxSpeed && speed >= 0)
            this.speed = speed;
        else
            this.speed = this.maxSpeed;
        this.length = 4.5d;
        this.distanceFromStart = distanceFromStart;
    }

    public double getLength() { return this.length; }

    public int getSpeed() { return this.speed; }

    public double getDistanceFromStart() { return this.distanceFromStart; }

    public void setSpeed(int speed) {
        if(speed>=0 && speed<=maxSpeed)
            this.speed = speed;
    }

    public void move(double distance) {
        this.distanceFromStart += distance;
        if(distanceFromStart<0.0d)
            distanceFromStart = 0.0d;
    }
}
