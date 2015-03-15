package com.artificialliver.base;

public abstract class BaseService<T> {
	public abstract BaseMapper<T> getMapper();
	
	public void insert(T t){
		getMapper().insert(t);
	}
	
	public void delete(T t){
		getMapper().delete(t);
	}
	
	public void update(T t){
		getMapper().update(t);
	}
	
	public T select(Integer id){
		return getMapper().select(id);
	}
}
