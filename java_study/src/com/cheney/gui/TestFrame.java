package com.cheney.gui;

import java.awt.Frame;

public class TestFrame {
	public static void main(String[] args) {
		Frame f=new Frame("测试窗口");
//		设置窗口大小位置
		f.setBounds(30,30,250,200);
//		将窗口显示出来（Frame对象默认处于隐藏状态）
		f.setVisible(true);
	}
}
