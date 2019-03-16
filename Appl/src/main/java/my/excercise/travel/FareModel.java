package my.excercise.travel;

import my.excercise.request.Fare;
import my.excercise.request.Location;

public class FareModel {
	Location depPort;
	Location arrPort;
	Fare fare;
	
	public Location getDepPort() {
		return depPort;
	}
	public void setDepPort(Location depPort) {
		this.depPort = depPort;
	}
	public Location getArrPort() {
		return arrPort;
	}
	public void setArrPort(Location arrPort) {
		this.arrPort = arrPort;
	}
	public Fare getFare() {
		return fare;
	}
	public void setFare(Fare fare) {
		this.fare = fare;
	}
	
	
}
