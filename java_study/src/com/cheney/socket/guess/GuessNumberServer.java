package com.cheney.socket.guess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GuessNumberServer {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		int ran = (int) (Math.random() * 100);
		boolean flag=false;

		try {
			serverSocket = new ServerSocket(30000);
			while (true) {
				socket = serverSocket.accept();
				new GuessThread(socket,ran).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class GuessThread extends Thread {
	private Socket socket;
	private int ran;

	public GuessThread(Socket socket,int ran) {
		this.socket = socket;
		this.ran=ran;
	}

	@Override
	public void run() {
		InputStream in = null;
		OutputStream out = null;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		

		try {
			System.out.println("Server: 随机数为" + ran);
			
			out = socket.getOutputStream();
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
			bufferedWriter.write(ran+"");
			
			bufferedWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedWriter != null) {
					bufferedWriter.close();
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
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}