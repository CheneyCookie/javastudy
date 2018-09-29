package com.cheney.socket.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;

public class Response {
	private String url;
	private Socket socket;
	private BufferedInputStream bufferedInputStream;
	private BufferedOutputStream bufferedOutputStream;

	public Response(String url, Socket socket) {
		this.url = url;
		this.socket = socket;
		
		System.out.println(this.socket+":Response:"+this.socket.isClosed());
	}

	//get响应
	public void doGet() {
		File file = new File(url);
		try {
			if(url.equals("")){
				rightOutput("login.html");
			}else if (file.exists()) {
				rightOutput(url);
			} else {
				errorOutput();
			}
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
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	//post响应
	public void doPost(String endString) {
		try {
			String[] nameKV = endString.split("&")[0].split("=");
			String[] passwordKV = endString.split("&")[1].split("=");
			if (nameKV.length == 1 || passwordKV.length == 1) {

				errorOutput();

			} else {
				String name = endString.split("&")[0].split("=")[1];
				String password = endString.split("&")[1].split("=")[1];

				Properties properties = new Properties();
				InputStream in = getClass().getClassLoader().getResourceAsStream(
						"ip.properties");
				properties.load(in);
				String uName=properties.getProperty("name");
				String uPassword=properties.getProperty("password");
				
				if (name.equals(uName) && password.equals(uPassword)) {
					rightOutput(url);

				} else {
					errorOutput();
				}
			}
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
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	//正确输出
	public void rightOutput(String fileName) throws IOException {

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

	//错误输出
	public void errorOutput() throws IOException {
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

	//设置响应头
	public void setContentType(BufferedOutputStream bufferedOutputStream)
			throws IOException {
		byte[] contentType = "HTTP/1.1 200 OK\r\nContentType: text/html,image/jpeg\r\n\r\n"
				.getBytes();
		bufferedOutputStream.write(contentType);
	}
}
