package com.cheney.socket.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

public class GuessNumberServer {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		Reader reader = null;
		BufferedReader bufferedReader = null;
		//String ip = null;
		String port = null;

		try {
			reader = new FileReader("ip.properties");
			bufferedReader = new BufferedReader(reader);
			String ip1 = bufferedReader.readLine();
			//ip = ip1.split("=")[1];
			String port1 = bufferedReader.readLine();
			port = port1.split("=")[1];

			serverSocket = new ServerSocket(Integer.parseInt(port));
			while (true) {
				socket = serverSocket.accept();
				new GuessThread(socket).start();
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
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class GuessThread extends Thread {
	private Socket socket;

	public GuessThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InputStream in = null;
		OutputStream out = null;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		int ran = (int) (Math.random() * 100);
		boolean flag = false;

		try {
			System.out.println("Server: 随机数为"+ran);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(in));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
				
				bufferedWriter.write("hhh");
			
				String guessNumber = bufferedReader.readLine();
				int number = Integer.parseInt(guessNumber);
				if (number > ran) {
					bufferedWriter.write("太大了");
				} else if (number < ran) {
					bufferedWriter.write("太小了");
				} else {
					bufferedWriter.write("true");
					flag = true;
				}
			
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