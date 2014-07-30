package com.prodyna.pac.ars.backend.common.exception;

public class ExceptionExtractor {

	public static <T> T isException(Exception e, Class<T> clazz) {
		Throwable t = e;
		if (clazz.isInstance(t)) {
			return (T) t;
		} else {
			while (t.getCause() != null) {
				t = e.getCause();
				if (clazz.isInstance(t)) {
					return (T) t;
				}
			}
			return null;
		}
	}

}
