package com.prodyna.pac.ars.backend.common.logging;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

@Interceptor
@Logged
public class MethodCallLoggingInterceptor {

	@Inject
	private Logger logger;

	@AroundInvoke
	public Object logMethodCall(InvocationContext ctx) throws Exception {

		logger.info("Entering method: " + ctx.getMethod().getDeclaringClass().getName() + "."
				+ ctx.getMethod().getName());

		Object result = ctx.proceed();

		logger.info("Exiting method: " + ctx.getMethod().getDeclaringClass().getName() + "."
				+ ctx.getMethod().getName());

		return result;
	}

}
