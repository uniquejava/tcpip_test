package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {
	private static final int BUFSIZE = 1024;

	public static void main(String[] args) throws Exception {

		if (args.length != 3) {
			throw new IllegalArgumentException(
					"Parameter(s):<Server> <Port> <LogFile>");
		}

		String server = args[0];
		int port = Integer.parseInt(args[1]);
		Client c = new Client();

		c.new MonitorThread(server, port, args[2]).run();

		// socket.close();
	}

	private class MonitorThread implements Runnable {
		private String server;
		private int port;
		private String file;

		public MonitorThread(String server, int port, String file) {
			this.server = server;
			this.port = port;
			this.file = file;
		}

		public void run() {

			Socket socket;
			try {
				socket = new Socket(server, port);
				System.out.println("Connected to server " + server + " on "
						+ port);

				OutputStream out = socket.getOutputStream();

				// "C:/w/servers/tomcat55/logs/catalina.2013-07-30.log"
				File f = new File(file);

				long mark = f.length();
				RandomAccessFile rf = new RandomAccessFile(f, "r");
				byte[] buf = new byte[BUFSIZE];
				int totalBytesRead = 0;
				while (true) {
					int bytesRead = 0;
					while (totalBytesRead < mark
							&& ((bytesRead = rf.read(buf, 0, buf.length)) != -1)) {
						out.write(buf, 0, bytesRead);
						totalBytesRead += bytesRead;
					}
					mark = rf.length();
					Thread.sleep(1000);
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
