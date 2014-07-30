package com.prodyna.pac.ars.backend.monitoring.methodcall.mbean;

public class MethodCallStatistics {

	private long numberOfCalls;

	private Long minDuration;

	private Long maxDuration;

	private Long totalDuration;

	public void processMethodCall(MethodCall methodCall) {

		numberOfCalls++;

		long duration = methodCall.getDuration();

		calculateTotalDuration(duration);
		calculateMinDuration(duration);
		calculateMaxDuration(duration);
	}

	public long getNumberOfCalls() {
		return numberOfCalls;
	}

	public double getAverageDuration() {
		double averageDuration;
		if (totalDuration == null) {
			averageDuration = 0;
		} else {
			averageDuration = totalDuration / numberOfCalls;
		}

		return averageDuration;
	}

	public double getMinDuration() {
		return this.minDuration;
	}

	public double getMaxDuration() {
		return this.maxDuration;
	}

	private void calculateMaxDuration(long duration) {
		if (maxDuration == null || maxDuration < duration) {
			maxDuration = duration;
		}
	}

	private void calculateMinDuration(long duration) {
		if (minDuration == null || minDuration > duration) {
			minDuration = duration;
		}
	}

	private void calculateTotalDuration(long duration) {
		if (totalDuration == null) {
			totalDuration = duration;
		} else {
			totalDuration += duration;
		}
	}
}
