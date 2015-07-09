package com.magine.spy.spyserver.receiver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.magine.spy.spyserver.config.ConfigManager;

/**
 * Receiver thread for transferring collected log achieve file from client via
 * TCP socket.
 */
public class Receiver implements Runnable {

	private Socket connection;

	/**
	 * Constructor
	 * 
	 * @param connection
	 *            Socket TCP socket connecting to client
	 */
	public Receiver(Socket connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void run() {

		DataInputStream in = null;
		BufferedOutputStream out = null;

		try {

			in = new DataInputStream(new BufferedInputStream(connection.getInputStream()));

			String filename = in.readUTF();

			System.out.println("Start transferring file " + filename);

			out = new BufferedOutputStream(new FileOutputStream(this.getOutputFile(filename)));

			byte[] bytes = new byte[1024];

			int count;
			while ((count = in.read(bytes)) > 0) {
				out.write(bytes, 0, count);
			}

			out.flush();

			System.out.println("End transferring file " + filename);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	private File getOutputFile(final String filename) {
		File outputFile = new File(ConfigManager.getInstance().getWorkspace() + File.separator + filename);
		return outputFile;
	}

}
