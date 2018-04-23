package com.googlecode.pongo.runtime;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import com.mongodb.BasicDBList;

public abstract class BasicDBListWrapper<T> implements List<T> {
	
	protected BasicDBList dbList;
	
	public BasicDBListWrapper(BasicDBList dbList) {
		this.dbList = dbList;
	}
	
	protected abstract T wrap(Object o);
	
	protected abstract Object unwrap(Object o);
	
	protected void added(T t) {};
	
	protected void removed(Object o) {};
	
	@Override
	public int size() {
		return dbList.size();
	}

	@Override
	public boolean isEmpty() {
		return dbList.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return dbList.contains(unwrap(o));
	}
	
	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(T e) {
		if (e != null && !contains(e)) {
			dbList.add(unwrap(e));
			added(e);
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean remove(Object o) {
		boolean result = dbList.remove(unwrap(o));
		removed(o);
		return result;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean result = c.size() > 0;
		for (T o : c) {
			result = result && add(o);
		}
		return result;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = c.size() > 0;
		for (Object o : c) {
			result = result && remove(o);
		}
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		dbList.clear();
	}
	
	@Override
	public T get(int index) {
		return (T) wrap(dbList.get(index));
	}

	@Override
	public T set(int index, T element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, T element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T remove(int index) {
		dbList.remove(index);
		return null;
	}

	@Override
	public int indexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOf(Object o) {
		return indexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
	}
}
