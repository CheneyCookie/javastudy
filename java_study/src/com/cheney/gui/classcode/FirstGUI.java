package com.cheney.gui.classcode;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FirstGUI {
	
	private JFrame frame;
	private JTextField firstTxt;
	private JTextField secondTxt;
	private JLabel lab;
	private JButton btn;
	private JTextField resTxt;
	
	public FirstGUI(){
		frame=new JFrame();
		frame.setTitle("first");
		frame.setVisible(true);
		frame.setBounds(100, 100, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
	}
	
	private void init(){
		//2.给容器设置布局管理器
		frame.setLayout(new FlowLayout());
		firstTxt=new JTextField(10);
		lab=new JLabel("+");
		secondTxt=new JTextField(10);
		btn=new JButton("=");
		resTxt=new JTextField(10);
		
		frame.add(firstTxt);
		frame.add(lab);
		frame.add(secondTxt);
		frame.add(btn);
		frame.add(resTxt);
	}
	
	public static void main(String[] args) {
		new FirstGUI();
	}
}
