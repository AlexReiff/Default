package lab1;
import lejos.nxt.*;
import lejos.util.Delay;

public class BangBangController implements UltrasonicController{
	private final int bandCenter, bandwith;
	private final int motorLow, motorHigh;
	private final int motorStraight = 300;
	private final NXTRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.C;
	private int distance;
	private int currentLeftSpeed;	
	
	public BangBangController(int bandCenter, int bandwith, int motorLow, int motorHigh) {
		//Default Constructor
		this.bandCenter = bandCenter;
		this.bandwith = bandwith;
		this.motorLow = motorLow;
		this.motorHigh = motorHigh;
		leftMotor.setSpeed(motorStraight);
		rightMotor.setSpeed(motorStraight);
		leftMotor.forward();
		rightMotor.forward();
		currentLeftSpeed = 0;
	}
	
	@Override
	public void processUSData(int distance) {
		//because of the 45 degree angle, the distance read should be converted
		//to distance * cos(45) AKA distance * (.7072)
		this.distance = (int) (distance * .7072);
		//band center has to be increased because of the cosine thing
		int error = distance - 30;
		
		//continue straight
		if (Math.abs(error) <= bandwith) {
			leftMotor.setSpeed(motorStraight);
			rightMotor.setSpeed(motorStraight);
		}
		//correct right
		else if (error < 0) {
			leftMotor.setSpeed(motorStraight + 150);
			rightMotor.setSpeed(motorStraight - 150);
		}
		//correct left
		else {
			leftMotor.setSpeed(motorStraight - 100);
			rightMotor.setSpeed(motorStraight + 100);
		}
	}

	@Override
	public int readUSDistance() {
		return this.distance;
	}
}
