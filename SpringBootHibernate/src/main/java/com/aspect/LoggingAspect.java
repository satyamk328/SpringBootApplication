package com.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
/**
 * This class is an aspect which is used to log entering , exiting and log Exceptions from methods
 * 
 * @author skumar49

 */
@Component
@Aspect
public class LoggingAspect {

	
	/**
	 * This method is used to log entering , exiting and log Exceptions from methods as specified by the pointcuts
	 * startup
	 * 
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Around("execution(* com..*.*(..))")
	public Object logMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
		final Class<?> targetClass = joinPoint.getTarget().getClass();
		final Logger logger = LoggerFactory.getLogger(targetClass.getName());
		try {
			final String className = targetClass.getSimpleName();
			logger.info(getPreMessage(joinPoint, className));
			final StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			final Object retVal = joinPoint.proceed();
			stopWatch.stop();
			logger.info(getPostMessage(joinPoint, className, stopWatch.getTotalTimeMillis()));
			return retVal;
		} catch (Exception ex) {
			logger.error(getErrorMessage(ex), ex);
			throw ex;
		}
	}

	/**
	 * This method is used to log entering into a method
	 * 
	 * @param joinPoint
	 * @param className
	 * @return String object
	 */
	private static String getPreMessage(final JoinPoint joinPoint, final String className) {
		final StringBuilder builder = new StringBuilder().append("Entered in ").append(className).append(".")
				.append(joinPoint.getSignature().getName()).append("(");
		appendTo(builder, joinPoint);
		return builder.append(")").toString();
	}

	/**
	 * This method is used to log exiting from a method
	 * 
	 * @param joinPoint
	 * @param className
	 * @return String object
	 */
	private static String getPostMessage(final JoinPoint joinPoint, final String className, final long millis) {
		return new StringBuilder().append("Exit from ").append(className).append(".")
				.append(joinPoint.getSignature().getName()).append("(..); Execution time: ").append(millis)
				.append(" ms;").toString();
	}

	/**
	 * This method is used to log exception
	 * @param ex
	 * @return String object
	 */
	private static String getErrorMessage(final Throwable ex) {
		return ex.getMessage();
	}
	
	/**
	 * This method is use to append string
	 * @param builder
	 * @param joinPoint
	 */
	private static void appendTo(final StringBuilder builder, final JoinPoint joinPoint) {
		final Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			if (i != 0) {
				builder.append(", ");
			}
			builder.append(args[i]);
		}
	}
}