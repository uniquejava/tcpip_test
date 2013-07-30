package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class Server {
	private static final int BUFSIZE = 1024; //size of receive buffer
	public static void main(String[] args) throws IOException {
		
		if (args.length!=1) {
			throw new IllegalArgumentException("Paramter(s):<Port>");
		}
		int port = Integer.parseInt(args[0]);
		System.out.println("Listening on port " + port);
		
		ServerSocket servSock = new ServerSocket(port);
		byte[] buf = new byte[BUFSIZE];
		
		while (true) {
			Socket clientSock = servSock.accept();
			
			SocketAddress clientAddress = clientSock.getRemoteSocketAddress();
			
			InputStream in = clientSock.getInputStream();
			OutputStream out = clientSock.getOutputStream();
			int bytesRead = 0;
			while ((bytesRead = in.read(buf))!=-1) {
				System.out.print(new String(buf,0,bytesRead));
			}
			
			clientSock.close();
		}
		
	}
}
