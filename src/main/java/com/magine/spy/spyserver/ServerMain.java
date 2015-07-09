package com.magine.spy.spyserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.magine.spy.spyserver.config.ConfigManager;
import com.magine.spy.spyserver.receiver.ReceiverManager;

/**
 * Main method for server
 */
public class ServerMain {

	public static void main(String[] args) {
		
		System.out.println("ServerMain started.");
		
		ServerSocket service = null;
		Socket connection = null;
		
		try {
			
			int port = ConfigManager.getInstance().getServerPort();
			
			service = new ServerSocket(port);
			
			// receiver server socket will listen on the port forever
			service.setSoTimeout(0);

			System.out.println("Start listening on port " + port);

			while (true) {
				connection = service.accept();

				System.out.println("Connection received from host " + connection.getInetAddress() + " port "
						+ connection.getPort());

				ReceiverManager.getInstance().startReceiver(connection);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			if (service != null) {
				try {
					service.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
	

}
