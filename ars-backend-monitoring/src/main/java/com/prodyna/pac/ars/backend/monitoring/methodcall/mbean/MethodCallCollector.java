package com.prodyna.pac.ars.backend.monitoring.methodcall.mbean;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.management.ObjectName;

@Singleton
@Startup
public class MethodCallCollector {

	@Inject
	private MBeanServer mBeanServer;

	private final String objectNameStr = MethodCallMonitorMXBean.OBJECTNAME;
	private ObjectName objectName;

	private final Map<String, MethodCallStatistics> methodCallStatisticsMap = new HashMap<String, MethodCallStatistics>();

	@PreDestroy
	public void unregisterMBean() {
		try {
			mBeanServer.unregisterMBean(objectName);
		} catch (Exception e) {
			throw new RuntimeException("Unregistering MBean " + objectNameStr + " failed");
		}
	}

	@PostConstruct
	public void registerMBean() {
		try {
			objectName = new ObjectName(objectNameStr);
			MethodCallMonitor mbean = new MethodCallMonitor(this);
			mBeanServer.registerMBean(mbean, objectName);
		} catch (Exception e) {
			throw new RuntimeException("Registering MBean " + objectNameStr + " failed");
		}
	}

	@Asynchronous
	public void collect(MethodCall methodCall) {
		MethodCallStatistics methodCallStatistics = methodCallStatisticsMap.get(methodCall.getMethodName());
		if (methodCallStatistics == null) {
			methodCallStatistics = new MethodCallStatistics();
			methodCallStatisticsMap.put(methodCall.getMethodName(), methodCallStatistics);
		}
		methodCallStatistics.processMethodCall(methodCall);
	}

	public void reset() {
		this.methodCallStatisticsMap.clear();
	}

	public Map<String, MethodCallStatistics> getMethodCallStatisticsMap() {
		return this.methodCallStatisticsMap;
	}
}
