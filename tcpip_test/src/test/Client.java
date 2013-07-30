package test;

import java.io.File;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

public class Client {
	private static final int BUFSIZE = 1024;

	public static void main(String[] args) throws Exception {
		
		if(args.length!=3){
			throw new IllegalArgumentException("Parameter(s):<Server> <Port> <LogFile>");
		}
		
		String server = args[0];
		int port = Integer.parseInt(args[1]);
		
		Socket socket = new Socket(server, port);
		System.out.println("Connected to server " + server + " on " + port);

		OutputStream out = socket.getOutputStream();

		//"C:/w/servers/tomcat55/logs/catalina.2013-07-30.log"
		File f = new File(args[2]);
		
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

			rf.seek(mark);
			mark = rf.length();
			Thread.sleep(1000);
		}

		// socket.close();
	}
}
