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
import java.net.Socket;
import java.util.Scanner;

public class GuessNumberClient {
	public static void main(String[] args) {

		String ip = null;
		String port = null;
		Socket socket = null;
		Reader reader = null;
		InputStream in = null;
		OutputStream out = null;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		Scanner sc = null;
		boolean flag = false;

		try {
			reader = new FileReader("ip.properties");
			bufferedReader = new BufferedReader(reader);
			String ip1 = bufferedReader.readLine();
			ip = ip1.split("=")[1];
			String port1 = bufferedReader.readLine();
			port = port1.split("=")[1];

			socket = new Socket(ip, Integer.parseInt(port));
			in = socket.getInputStream();
			out = socket.getOutputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(in));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));

	
				sc = new Scanner(System.in);
				bufferedWriter.write(sc.nextLine());
				String msg = bufferedReader.readLine();
				if (msg.equals("true")) {
					flag = true;
				}
				System.out.println("111");
				System.out.println(msg);
			

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
				if (reader != null) {
					reader.close();
				}
				if(socket!=null){
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
