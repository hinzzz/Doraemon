package com.hinz.predicate;

@FunctionalInterface
public interface MyPredicate<T> {

	public boolean test(T t);

}
