package com.cheney.socket.briup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClientTest {
	public static void main(String[] args) {
		Socket s=null;
		BufferedReader br=null;
		try {
			s=new Socket("127.0.0.1",10001);
			InputStream is=s.getInputStream();
			br=new BufferedReader(new InputStreamReader(is));
			String line=br.readLine();
			System.out.println("client:"+line);
			if(br!=null){
				br.close();
			}
			if(s!=null){
				s.close();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
