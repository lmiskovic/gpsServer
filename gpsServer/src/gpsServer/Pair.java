package gpsServer;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Pair implements Serializable{
	private String name;
	private double latitude;
	private double longitude;

	
	Pair(String _name, double _latitude, double _longitude){
		name = _name;
		latitude = _latitude;
		longitude = _longitude;
	}
	
	Pair(String _name){
		name = _name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String _name){
		this.name = _name;
	}
	
	public double getLatitude(){
		return this.latitude;
	}
	
	public void setLatitude(double _latitude){
		this.latitude = _latitude;
	}
	
	public double getLongitude(){
		return this.longitude;
	}
	public void setLongitude(double _longitude){
		this.longitude = _longitude;
	}
	
}