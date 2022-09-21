package in.dudeapp.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 * Created by mohan on 22/07/22
 */
@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled:true}")
public class ExecutionTimeAdvice {

    @Around("@annotation(in.dudeapp.common.annotation.TrackExecutionTime)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endTime = System.currentTimeMillis();
        String [] classNameTokens = point.getSignature().getDeclaringTypeName().split("\\.");
        String className = classNameTokens[classNameTokens.length-1];
        log.info("Class Name: {}, Method Name: {}. Time taken for Execution is : {} ms", className, point.getSignature().getName(), (endTime - startTime));
        return object;
    }
}
