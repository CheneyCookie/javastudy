package com.cheney.jdbc;

public class GetS {
	private Student student;
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Student getStudent(Student student){
		this.student=student;
		return student;
	}
}
