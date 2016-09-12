package gpsServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import gpsTestCoordinatesSender.gpsCoordinates;

@SuppressWarnings("serial")
public class gpsSockets implements Runnable, Serializable {

	Socket socket;
	String name, input, output;
	int id;
	ObjectInputStream in;
	ObjectOutputStream out;
	gpsCoordinates coordinates;

	
	
	public gpsSockets(Socket _socket, int socketsCnt) {
		socket = _socket;
		id = socketsCnt;
		name = "Client " + id;
	}

	@Override
	public void run() {

		while (true) {
			getCoordinates();
		}
	}

	public void getCoordinates() {

		try {
			in = new ObjectInputStream(socket.getInputStream());
			coordinates = (gpsCoordinates) in.readObject();

			System.out.println("Dobivene koordinate klijenta " + name + " su: " + coordinates.getLatitude() + " i "
					+ coordinates.getLongitude());
		} catch (IOException e) {
			System.out.println(name + " se odspojio!");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}
