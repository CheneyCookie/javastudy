package com.cheney.socket.guess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Socket;
import java.util.Scanner;

public class GuessNumberClient {
	private static boolean isRunning;
	public static void main(String[] args) {
		
		Socket socket = null;
		Reader reader = null;
		InputStream in = null;
		OutputStream out = null;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		

		try {
			socket = new Socket("127.0.0.1",30000);

			
			in = socket.getInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(in));
			String msg = bufferedReader.readLine();
			//System.out.println("client:随机数"+Integer.parseInt(msg));
			isRunning=true;
			guess(Integer.parseInt(msg));

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

	private static void guess(int ran) {
		if(isRunning){
			System.out.println("请输入1~100的数：");
			Scanner sc=new Scanner(System.in);
			while(true){
				int num=sc.nextInt();
				if(num>ran){
					System.out.println("太大");
				}else if(num<ran){
					System.out.println("太小");
				}else{
					System.out.println("恭喜你，猜对了");
					break;
				}
			}
			sc.close();
		}
	}
}
 


