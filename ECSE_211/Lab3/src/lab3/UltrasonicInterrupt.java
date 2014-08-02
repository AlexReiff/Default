/* UltrasonicInterrupt.java
 * 
 * Alex Reiff and Henry Wang
 * ECSE 211 - DPM - Group 46
 * February 4, 2014
 * 
 * Reads the distance from the ultrasonic sensor and informs the driving thread
 */
package lab3;
import lejos.nxt.*;

public class UltrasonicInterrupt extends Thread {
	private static final SensorPort usPort = SensorPort.S2;	
	private UltrasonicSensor usSensor;
	private Driver driver;
	
	//needs to know which driver to talk to
	public UltrasonicInterrupt(Driver driver) {
		usSensor = new UltrasonicSensor(usPort);
		this.driver = driver;
	}
	
	//passes the ultrasonic distance to the driver thread
	public void run() {
		while (true) {
			int distance = usSensor.getDistance();
			driver.setObs(distance);
		}
	}
}
