package com.cheney.socket.briup.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerImpl implements Server {

	ServerSocket serverSocket = null;
	Socket socket = null;

	@Override
	public void init() {
		try {
			serverSocket = new ServerSocket(11111);
			while (true) {
				socket = serverSocket.accept();

				new GetThread(socket).start();

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void receive() {

	}

	public static void main(String[] args) {
		ServerImpl serverImpl = new ServerImpl();
		serverImpl.init();
	}
}

class GetThread extends Thread {
	private Socket socket;
	private BufferedReader bufferedReader = null;
	// private BufferedWriter bufferedWriter = null;
	private BufferedInputStream bufferedInputStream = null;
	private BufferedOutputStream bufferedOutputStream = null;

	public GetThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String str = null;
			String strRed = null;
			char[] endChar = null;
			String endString = null;

			String name = null;
			String password = null;

			int length = 0;

			str = bufferedReader.readLine();
			System.out.println(str);
			String[] str1 = new String[3];
			str1 = str.split(" ");
			String method = str1[0];
			String url = str1[1].substring(1);
			System.out.println("method:" + method + " url:" + url);
			String fileName = url.split("\\?")[0];
			System.out.println("file:" + fileName);

			File file = new File(fileName);
			System.out.println(file.exists());

			while ((strRed = bufferedReader.readLine()) != null) {
				if (str.split(" ")[0].equals("GET")) {
					if (file.exists()) {
						bufferedInputStream = new BufferedInputStream(
								new FileInputStream(file));
						bufferedOutputStream = new BufferedOutputStream(
								socket.getOutputStream());
						
						//设置响应头
						byte[] contentType="HTTP/1.1 200 OK\r\nContentType: text/html,image/jpeg\r\n\r\n".getBytes();
						bufferedOutputStream.write(contentType);
						
						byte[] buffer = new byte[1024];
						int len = 0;
						while ((len = bufferedInputStream.read(buffer)) != -1) {
							bufferedOutputStream.write(buffer, 0, len);
						}
					} else {
						bufferedInputStream = new BufferedInputStream(
								new FileInputStream("404.jpg"));
						bufferedOutputStream = new BufferedOutputStream(
								socket.getOutputStream());
						
						byte[] contentType="HTTP/1.1 200 OK\r\nContentType: text/html,image/jpeg\r\n\r\n".getBytes();
						bufferedOutputStream.write(contentType);
						
						byte[] buffer = new byte[1024];
						int len = 0;
						while ((len = bufferedInputStream.read(buffer)) != -1) {
							bufferedOutputStream.write(buffer, 0, len);
						}
					}
					bufferedOutputStream.flush();
					break;
					
					
				} else {

					if (strRed.contains("Length")) {
						String lenStr = strRed.split(" ")[1];
						length = Integer.parseInt(lenStr);
					}

					if (strRed.equals("")) {
						endChar = new char[length];
						bufferedReader.read(endChar);
						System.out.println(endChar);

						StringBuffer stringBuffer = new StringBuffer();
						for (int i = 0; i < endChar.length; i++) {
							stringBuffer.append(endChar[i]);
						}
						endString = stringBuffer.toString();
						System.out.println(endString);

						name = endString.split("&")[0].split("=")[1];
						password = endString.split("&")[1].split("=")[1];

						if (name.equals("cheney") && password.equals("121716")) {
							bufferedInputStream = new BufferedInputStream(
									new FileInputStream(file));
							bufferedOutputStream = new BufferedOutputStream(
									socket.getOutputStream());

							byte[] contentType="HTTP/1.1 200 OK\r\nContentType: text/html,image/jpeg\r\n\r\n".getBytes();
							bufferedOutputStream.write(contentType);
							
							byte[] buffer = new byte[1024];
							int len = 0;
							while ((len = bufferedInputStream.read(buffer)) != -1) {
								bufferedOutputStream.write(buffer, 0, len);
							}
							bufferedOutputStream.flush();
							System.out.println("登录成功");
						} else {
							bufferedInputStream = new BufferedInputStream(
									new FileInputStream("Error.html"));
							bufferedOutputStream = new BufferedOutputStream(
									socket.getOutputStream());

							byte[] contentType="HTTP/1.1 200 OK\r\nContentType: text/html,image/jpeg\r\n\r\n".getBytes();
							bufferedOutputStream.write(contentType);
							
							byte[] buffer = new byte[1024];
							int len = 0;
							while ((len = bufferedInputStream.read(buffer)) != -1) {
								bufferedOutputStream.write(buffer, 0, len);
							}
							System.out.println("登录失败");
							bufferedOutputStream.flush();
						}
						break;
					}
				}
			}

			/*
			 * if (file.exists()) { bufferedReader = new BufferedReader(new
			 * InputStreamReader( new FileInputStream(file)));
			 * 
			 * bufferedWriter = new BufferedWriter(new OutputStreamWriter(
			 * socket.getOutputStream())); String str2 = null; while ((str2 =
			 * bufferedReader.readLine()) != null) { bufferedWriter.write(str2);
			 * System.out.println(str2); } }else{ bufferedWriter = new
			 * BufferedWriter(new OutputStreamWriter(
			 * socket.getOutputStream(),"GBK"));
			 * bufferedWriter.write("404 Not Found! 找不到"); }
			 * bufferedWriter.flush();
			 */

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// if (bufferedWriter != null) {
				// bufferedWriter.close();
				// }
				if (bufferedOutputStream != null) {
					bufferedOutputStream.close();
				}
				if (bufferedInputStream != null) {
					bufferedInputStream.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
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
