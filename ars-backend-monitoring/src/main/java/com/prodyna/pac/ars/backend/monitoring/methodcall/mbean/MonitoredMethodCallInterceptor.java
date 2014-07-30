package com.prodyna.pac.ars.backend.monitoring.methodcall.mbean;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.prodyna.pac.ars.backend.monitoring.methodcall.MonitoredMethodCall;

@Interceptor
@MonitoredMethodCall
public class MonitoredMethodCallInterceptor {

	@Inject
	private MethodCallCollector collector;

	@AroundInvoke
	public Object addMethodCallToStatistics(InvocationContext ctx) throws Exception {

		long time1 = System.currentTimeMillis();
		Object result = ctx.proceed();
		long time2 = System.currentTimeMillis();

		String signature = createMethodSignature(ctx);
		long duration = time2 - time1;
		MethodCall methodCall = new MethodCall(signature, duration);
		collector.collect(methodCall);

		return result;
	}

	private String createMethodSignature(InvocationContext ctx) {

		StringBuilder builder = new StringBuilder();
		builder.append(ctx.getTarget().getClass().getCanonicalName());
		builder.append("#");
		builder.append(ctx.getMethod().getName());

		builder.append("(");
		Object[] parameters = ctx.getParameters();
		int count = 0;
		for (Object obj : parameters) {
			builder.append(obj.getClass().getName());
			if (++count < parameters.length) {
				builder.append(",");
			}
		}
		builder.append(")");

		return builder.toString();
	}
}
