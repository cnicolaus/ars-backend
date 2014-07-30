package com.prodyna.pac.ars.backend.monitoring.methodcall.mbean;

import java.util.Map;

public interface MethodCallMonitorMXBean {

	public static final String OBJECTNAME = "com.prodyna.pac.ars.backend:service=MethodCallMonitor";

	public Map<String, Long> getNumberOfCallSatistics();

	public Map<String, Double> getMinDurationStatistics();

	public Map<String, Double> getMaxDurationStatistics();

	public Map<String, Double> getAverageDurationStatistics();

	public void reset();
}
