package com.prodyna.pac.ars.backend.monitoring.methodcall.mbean;

import java.util.HashMap;
import java.util.Map;

public class MethodCallMonitor implements MethodCallMonitorMXBean {

	private final MethodCallCollector collector;

	public MethodCallMonitor(MethodCallCollector collector) {
		this.collector = collector;
	}

	@Override
	public Map<String, Long> getNumberOfCallSatistics() {

		Map<String, MethodCallStatistics> map = collector.getMethodCallStatisticsMap();
		Map<String, Long> result = new HashMap<String, Long>();
		for (String methodSignature : map.keySet()) {
			result.put(methodSignature, map.get(methodSignature).getNumberOfCalls());
		}

		return result;
	}

	@Override
	public Map<String, Double> getMinDurationStatistics() {
		Map<String, MethodCallStatistics> map = collector.getMethodCallStatisticsMap();
		Map<String, Double> result = new HashMap<String, Double>();
		for (String methodSignature : map.keySet()) {
			result.put(methodSignature, map.get(methodSignature).getMinDuration());
		}

		return result;
	}

	@Override
	public Map<String, Double> getMaxDurationStatistics() {
		Map<String, MethodCallStatistics> map = collector.getMethodCallStatisticsMap();
		Map<String, Double> result = new HashMap<String, Double>();
		for (String methodSignature : map.keySet()) {
			result.put(methodSignature, map.get(methodSignature).getMaxDuration());
		}

		return result;
	}

	@Override
	public Map<String, Double> getAverageDurationStatistics() {

		Map<String, MethodCallStatistics> map = collector.getMethodCallStatisticsMap();
		Map<String, Double> result = new HashMap<String, Double>();
		for (String methodSignature : map.keySet()) {
			result.put(methodSignature, map.get(methodSignature).getAverageDuration());
		}

		return result;
	}

	@Override
	public void reset() {
		collector.reset();
	}
}
