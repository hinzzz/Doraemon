package com.hinz.predicate;

@FunctionalInterface
public interface MyPredicate<T> {

	boolean test(T t);

}
