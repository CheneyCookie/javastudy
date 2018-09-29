package com.cheney.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class HttpServer implements Server {

	ServerSocket serverSocket;
	Socket socket;
	int port;

	/*
	 * (non-Javadoc)
	 * 
	 * 初始化
	 */
	@Override
	public void init() {
		try {
			Properties properties = new Properties();
			InputStream in = getClass().getClassLoader().getResourceAsStream(
					"ip.properties");
			properties.load(in);
			String portStr = properties.getProperty("port");
			port = Integer.parseInt(portStr);

			System.out.println(port);

			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 等待客户端请求
	 */
	@Override
	public void receive() {
		while (true) {
			try {
				socket = serverSocket.accept();
				new HttpThread(socket).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		HttpServer httpServer = new HttpServer();
		httpServer.init();
		httpServer.receive();
	}
}

class HttpThread extends Thread {
	private Socket socket;
	private BufferedReader bufferedReader;

	public HttpThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			Request request = new Request(bufferedReader);
			// 获取提交方法
			String method = request.getMethod();
			// 获取资源路径
			String url = request.getUrl();
			// 获取用户信息
			String endString = request.getEndString();

			Response response = new Response(url, socket);

			// 做出响应
			if (method.equals("GET")) {
				response.doGet();
			} else {
				response.doPost(endString);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
