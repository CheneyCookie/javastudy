package com.cheney.gui.classcode;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Cal extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private JTextField txt;
	private JPanel panel;
	private JButton[] numBtns;
	private JButton sumBtn;
	private JButton subBtn;
	private JButton mulBtn;
	private JButton divBtn;
	
	public Cal(String name){
		setTitle(name);
		setBounds(100,100,300,300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		numBtns=new JButton[10];
		
		init();
	}
	
	private void init() {
		setLayout(new BorderLayout());
		txt=new JTextField(8);
		add(txt,BorderLayout.NORTH);
		
		panel=new JPanel();
		panel.setLayout(new GridLayout(4,4));
		
		for(int i=0;i<numBtns.length;i++){
			numBtns[i]=new JButton(""+i);
			panel.add(numBtns[i]);
		}
		
		sumBtn=new JButton("+");
		subBtn=new JButton("-");
		mulBtn=new JButton("*");
		divBtn=new JButton("/");
		
		panel.add(sumBtn);
		panel.add(subBtn);
		panel.add(mulBtn);
		panel.add(divBtn);
		
		add(panel,BorderLayout.CENTER);
	}

	public static void main(String[] args){
		
		new Cal("cal");
	}
}
