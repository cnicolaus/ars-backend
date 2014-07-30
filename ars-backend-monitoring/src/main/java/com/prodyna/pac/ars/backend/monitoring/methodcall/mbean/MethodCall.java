package com.prodyna.pac.ars.backend.monitoring.methodcall.mbean;

public class MethodCall {

	private final long duration;

	private final String methodName;

	public MethodCall(String methodName, long duration) {
		this.methodName = methodName;
		this.duration = duration;
	}

	public String getMethodName() {
		return methodName;
	}

	public long getDuration() {
		return duration;
	}
}
