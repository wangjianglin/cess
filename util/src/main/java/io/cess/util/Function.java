package io.cess.util;

/**
 * @author lin
 * @date 1/28/16.
 */

@FunctionalInterface
public interface Function<R,T> {

    R function(T t);
}
