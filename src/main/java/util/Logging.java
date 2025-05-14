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
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String INDENT = "    ";

    private static final String BEFORE_MESSAGE =
            CYAN + "\n--> Executing: {}\n" + INDENT + "Arguments: [{}]" + RESET;

    private static final String AFTER_RETURN_MESSAGE =
            GREEN + "\n<-- Completed: {}\n" + INDENT + "Returned: [{}]" + RESET;

    private static final String AFTER_THROW_MESSAGE =
            RED + "\n!!! Exception in: {}\n" + INDENT + "Cause: [{}]" + RESET;

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

    @Around("com.deeb.provisionprocess.util.PointcutExpression.allExceptHandleNoResourceFound()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = getMethodName(joinPoint);
        StringBuilder args = getMethodArgs(joinPoint);
        Object returnVal;

        LOGGER.info(BEFORE_MESSAGE, methodName, args);

        try {
            returnVal = joinPoint.proceed();
        } catch (Throwable throwable) {
            LOGGER.error(AFTER_THROW_MESSAGE, methodName, throwable.getMessage());
            throw throwable;
        }

        LOGGER.info(AFTER_RETURN_MESSAGE, methodName, returnVal);
        return returnVal;
    }
}
