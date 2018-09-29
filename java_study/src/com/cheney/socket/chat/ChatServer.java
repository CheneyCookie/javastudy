package com.cheney.socket.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	static List<Socket> listSockets = new ArrayList<Socket>();

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket(12345);
			while (true) {
				socket = serverSocket.accept();
				listSockets.add(socket);
				new ChatServerThread(socket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

class ChatServerThread extends Thread {
	private Socket socket;

	public ChatServerThread(Socket socket) {
		this.socket=socket;
	}

	@Override
	public void run() {
		InputStream in = null;
		OutputStream out = null;
		BufferedReader bufferedReader = null;
		PrintWriter printWriter =null;
		
		try {
			in = socket.getInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(in));
				String msg = null;
			while ((msg=bufferedReader.readLine())!=null) {

				for (Socket socket : ChatServer.listSockets) {
					if(socket!=this.socket){
						
						out = socket.getOutputStream();
						printWriter= new PrintWriter(new OutputStreamWriter(
								out));
						printWriter.println(msg);
						printWriter.flush();
					}
				}
				System.out.println(msg);
			}

		} catch (IOException e) {
			ChatServer.listSockets.remove(socket);
			e.printStackTrace();
		} finally {
			try {
				if (printWriter != null) {
					printWriter.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

/*
 * class ChatServerSendThread extends Thread {
	private List<Socket> listSockets;
	private String message;

	public ChatServerSendThread(List<Socket> listSockets,String message) {
		this.listSockets = listSockets;
		this.message=message;
	}

	@Override
	public void run() {
		OutputStream out = null;
		BufferedWriter bufferedWriter = null;

		try {
			while (true) {
				for (Socket socket : listSockets) {
					out = socket.getOutputStream();
					bufferedWriter = new BufferedWriter(new OutputStreamWriter(
							out));
					bufferedWriter.write(message);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedWriter != null) {
					bufferedWriter.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
 * */
