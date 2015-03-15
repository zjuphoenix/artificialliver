package com.artificialliver.base;

public interface BaseMapper<T> {
	void insert(T t);
	void delete(T t);
	void update(T t);
	T select(Integer id);
}
