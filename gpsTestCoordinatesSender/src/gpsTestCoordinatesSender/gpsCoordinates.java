package gpsTestCoordinatesSender;

import java.io.Serializable;

@SuppressWarnings("serial")
public class gpsCoordinates implements Serializable{
	
	private double latitude;
	private double longitude;
	
	gpsCoordinates(){
		
	}
	
	gpsCoordinates(Double _latitude, Double _longitude){
		this.latitude = _latitude;
		this.longitude = _longitude;
	}
	
	public double getLatitude(){
		return this.latitude;
	}
	
	public void setLatitude(Double _latitude){
		this.latitude = _latitude;
	}
	
	public double getLongitude(){
		return this.longitude;
	}
	
	public void setLongitude(Double _longitude){
		this.longitude = _longitude;
	}
}
