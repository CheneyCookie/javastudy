package com.cheney.proxy;

public interface Service {
	void addNew(Person person);
	void delete(Integer id);
	void update(Person person);
}
