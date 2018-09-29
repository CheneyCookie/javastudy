package com.cheney.socket.briup.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerImplEx implements Server {

	ServerSocket serverSocket = null;
	Socket socket = null;

	@Override
	public void init() {
		try {
			serverSocket = new ServerSocket(11111);
			while (true) {

				socket = serverSocket.accept();
				new NewGetThread(socket).start();

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
		ServerImplEx serverImplEx = new ServerImplEx();
		serverImplEx.init();
	}

}

class NewGetThread extends Thread {
	private Socket socket;
	private BufferedReader bufferedReader = null;
	// private BufferedWriter bufferedWriter = null;
	private BufferedInputStream bufferedInputStream = null;
	private BufferedOutputStream bufferedOutputStream = null;

	public NewGetThread(Socket socket) {
		this.socket = socket;
	}

	public void get(String fileName) throws FileNotFoundException, IOException {
		File file = new File(fileName);
		System.out.println(file.exists());
		if (file.exists()) {
			rightOutput(fileName);
		} else {
			errorOutput();
		}
	}

	public void post(String fileName) throws NumberFormatException, FileNotFoundException, IOException {
			String strRed = null;
			int length = 0;
			char[] endChar = null;
			String endString = null;

			String name = null;
			String password = null;
			while ((strRed = bufferedReader.readLine()) != null) {
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

					String[] nameKV = endString.split("&")[0].split("=");
					String[] passwordKV = endString.split("&")[1].split("=");
					if (nameKV.length == 1 || passwordKV.length == 1) {
						errorOutput();
					} else {
						name = endString.split("&")[0].split("=")[1];
						password = endString.split("&")[1].split("=")[1];

						if (name.equals("cheney") && password.equals("121716")) {

							rightOutput(fileName);

						} else {

							errorOutput();
						}
					}
					break;
				}
			}
	}

	public void rightOutput(String fileName) throws FileNotFoundException,IOException {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(
					fileName));

			bufferedOutputStream = new BufferedOutputStream(
					socket.getOutputStream());

			// 设置响应头
			setContentType(bufferedOutputStream);

			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = bufferedInputStream.read(buffer)) != -1) {
				bufferedOutputStream.write(buffer, 0, len);
			}
	}

	public void errorOutput() throws FileNotFoundException,IOException {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(
					"Error.html"));

			bufferedOutputStream = new BufferedOutputStream(
					socket.getOutputStream());

			setContentType(bufferedOutputStream);

			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = bufferedInputStream.read(buffer)) != -1) {
				bufferedOutputStream.write(buffer, 0, len);
			}
			System.out.println("登录失败");
			bufferedOutputStream.flush();
	}

	public void request() {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String str = null;
			str = bufferedReader.readLine();
			System.out.println(str);
			String[] str1 = new String[3];
			str1 = str.split(" ");
			String method = str1[0];
			String url = str1[1].substring(1);
			System.out.println("method:" + method + " url:" + url);
			String fileName = url.split("\\?")[0];
			System.out.println("file:" + fileName);

			response(str.split(" ")[0], fileName);

		}catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
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

	public void response(String str, String fileName) throws FileNotFoundException, IOException {
		if (str.equals("GET")) {
			get(fileName);
		} else {
			post(fileName);
		}
	}
	
	public void setContentType(BufferedOutputStream bufferedOutputStream) throws IOException{
		byte[] contentType = "HTTP/1.1 200 OK\r\nContentType: text/html,image/jpeg\r\n\r\n"
				.getBytes();
//		Content-Type
//		byte[] contentType = "HTTP/1.1 200 OK\r\nContent-Type: text/html,image/jpeg\r\n\r\n".getBytes();
			bufferedOutputStream.write(contentType);
	}

	@Override
	public void run() {
		request();
	}
}
