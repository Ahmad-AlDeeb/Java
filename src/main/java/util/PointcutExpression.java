package util;

import org.aspectj.lang.annotation.Pointcut;

public class PointcutExpression {
    @Pointcut("execution(* com.deeb.provisionprocess..*.*(..))")
    public void all() {
    }

    @Pointcut("execution(* com.deeb.provisionprocess.exception.GlobalExceptionHandler.handleNoResourceFound(..))")
    public void handleNoResourceFound() {
    }

    @Pointcut("all() && !handleNoResourceFound()")
    public void allExceptHandleNoResourceFound() {
    }
}
