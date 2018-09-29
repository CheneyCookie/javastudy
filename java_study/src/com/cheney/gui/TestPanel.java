package com.cheney.gui;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextField;

public class TestPanel {
	public static void main(String[] args) {
		Frame f=new Frame("测试窗口");
		//创建一个Panel容器
		Panel p=new Panel();
		//向Panel容器中添加两个组件
		p.add(new TextField(20));
		p.add(new Button("click me"));
		//将Panel容器添加到Frame窗体
		f.add(p);
		f.setBounds(30,30,250,120);
		f.setVisible(true);
		
	}
}
