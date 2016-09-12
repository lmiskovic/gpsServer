package gpsServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class serverPool {

	private static final int PORT = 20151;
	private static final int PORT1 = 20152;

	static ServerSocket serverSocket = null;
	static ServerSocket serverSocket1 = null;

	public static void main(String args[]) throws InterruptedException {

		ArrayList<Pair> pairs = new ArrayList<Pair>();
		ArrayList<androidSockets> androidSockets = new ArrayList<androidSockets>();
		ArrayList<gpsSockets> sockets = new ArrayList<gpsSockets>();
		//ArrayList<Socket> androidSockets = new ArrayList<Socket>();


		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		ThreadPoolExecutor executorPool = new ThreadPoolExecutor(20, 40, 100, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(5), threadFactory);

		new Thread() {
			public void run() {

				try {
					serverSocket = new ServerSocket(PORT);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("Kreiran server socket na portu: " + PORT);

				Socket socket = null;

				while (true) {
					try {
						socket = serverSocket.accept();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Veza prihvacena.");
					gpsSockets a = new gpsSockets(socket, sockets.size());
					executorPool.execute(a);
					sockets.add(a);
				}
			}
		}.start();

		new Thread() {
			public void run() {
				
				boolean firstpass = true;
				
				while (true) {
					if (sockets.size() != 0) {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(firstpass == true){ //popunjavanje pairs liste na prvom prolazu
							for(int i = 0; i<sockets.size(); i++){
								pairs.add(new Pair(sockets.get(i).name, sockets.get(i).coordinates.getLatitude(), sockets.get(i).coordinates.getLongitude()));
							}
							firstpass = false;
						}else {
							if(sockets.size() == pairs.size()){ //update coordinata elemenata pairs liste ako se nisu spojili novi klijenti
								for(int i = 0; i<sockets.size(); i++){
									pairs.get(i).setLatitude(sockets.get(i).coordinates.getLatitude());
									pairs.get(i).setLongitude(sockets.get(i).coordinates.getLongitude());
								}
							}else{
								for(int i = 0; i<pairs.size(); i++){ // update coordinate i dodavanje novih elemenata na kraj
									pairs.get(i).setLatitude(sockets.get(i).coordinates.getLatitude());
									pairs.get(i).setLongitude(sockets.get(i).coordinates.getLongitude());
								}
								
								for(int i = pairs.size(); i < sockets.size(); i++){
									pairs.add(new Pair(sockets.get(i).name, sockets.get(i).coordinates.getLatitude(), sockets.get(i).coordinates.getLongitude()));
								}
							}
						}
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();

		new Thread() {
			public void run() {
				try {
					serverSocket1 = new ServerSocket(PORT1);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				System.out.println("Kreiran android server socket na portu: " + PORT1);

				Socket socket1 = null;

				while (true) {
					
					try {
						socket1 = serverSocket1.accept();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("Android klijent se spojio!");
					
					androidSockets aSocket = new androidSockets(socket1, androidSockets.size(), pairs);
					executorPool.execute(aSocket);
					androidSockets.add(aSocket);
				}
			}
		}.start();
	}
}