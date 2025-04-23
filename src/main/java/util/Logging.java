package util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {
    private static final Logger LOGGER = LoggerFactory.getLogger(Logging.class);
    private static final String BEFORE_MESSAGE = "Executing ===> {}.{} with arguments: [{}]";
    private static final String AFTER_RETURN_MESSAGE = "Finished ===> {}.{} with arguments: [{}] and returned {}";
    private static final String AFTER_THROW_MESSAGE = "Exception {} in ===> {}.{} with arguments: [{}]";
    private static String getClassName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName();
    }
    private static String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().toShortString();
    }
    private static StringBuilder getMethodArgs(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        StringBuilder argsString = new StringBuilder();
        for (Object arg : args) {
            if (arg != null) {
                argsString.append(arg).append(", ");
            }
        }
        if (!argsString.isEmpty()) {
            argsString.setLength(argsString.length() - 2);
        }
        return argsString;
    }

    @Around("execution(* com.deeb.application.*.*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = getClassName(joinPoint);
        String methodName = getMethodName(joinPoint);
        StringBuilder args = getMethodArgs(joinPoint);
        Object returnVal = null;

        LOGGER.info(BEFORE_MESSAGE, className, methodName, args);
        try {
            returnVal = joinPoint.proceed();
        } catch (Throwable throwable) {
            LOGGER.error(AFTER_THROW_MESSAGE, throwable, className, methodName, args);
            throw throwable;
        }
        LOGGER.info(AFTER_RETURN_MESSAGE, className, methodName, args, returnVal);

        return returnVal;
    }
}
