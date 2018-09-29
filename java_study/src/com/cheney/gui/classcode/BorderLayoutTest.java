package com.cheney.gui.classcode;

import java.awt.BorderLayout;
import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class BorderLayoutTest extends JFrame{
	private static final long serialVersionUID = 1L;
	private JLabel northLab;
	private JTextArea westText;
	private JTextArea eastText;
	private JLabel centerLab;
	private JButton leftBtn;
	private JButton rightBtn;
	private JButton clearBtn;

	public BorderLayoutTest(){
		setTitle("border");
		setBounds(100,100,500,500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		init();
	}
	
	private void init() {
		setLayout(new BorderLayout());
		northLab=new JLabel("operation");
		add(northLab,BorderLayout.NORTH);
		westText=new JTextArea(5,8);
		add(westText,BorderLayout.WEST);
		eastText=new JTextArea(5,8);
		add(eastText,BorderLayout.EAST);
		centerLab=new JLabel();
		add(centerLab,BorderLayout.CENTER);
		
		JPanel p=new JPanel();
		
		leftBtn=new JButton("left");
		p.add(leftBtn);
		clearBtn=new JButton("clear");
		p.add(clearBtn);
		rightBtn=new JButton("right");
		p.add(rightBtn);
		add(p,BorderLayout.SOUTH);
		
	}

	public static void main(String[] args) {
		new BorderLayoutTest();
	}
}
