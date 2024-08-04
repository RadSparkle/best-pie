package com.bestpie.ui.common.aop;

import com.bestpie.ui.common.entity.Log;
import com.bestpie.ui.common.repository.LogRepository;
import com.bestpie.ui.common.utils.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Aspect
@Component
@Log4j2
public class LoggingAop {

    @Autowired
    private LogRepository logRepository;

    @After("execution(* com.bestpie.ui.api.bestPost.main.controller.BestPostController.*(..))")
    public void logBefore() {
        Log accessLog = new Log();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddress = getClientIpAddress(request);
        String requestURI = request.getRequestURI();

        accessLog.setIp(ipAddress);
        accessLog.setLocation(requestURI);
        accessLog.setLogDate(TimeUtil.getCurrentTime());

        logRepository.save(accessLog);
        log.info("User access: {}, {}", accessLog.getIp(), accessLog.getLocation());
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress != null && !ipAddress.isEmpty() && !"unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = ipAddress.split(",")[0];
        } else {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
