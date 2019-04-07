package com.lemon.spring.service;

import com.lemon.framework.properties.spring.ApplicationProperties;
import com.lemon.spring.controller.rest.DefaultErrorController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SuppressWarnings("WeakerAccess")
@Service
public class ApplicationService {
    protected final Logger log= Logger.getLogger(DefaultErrorController.class);

    @Inject
    private ApplicationProperties properties;


    public String logPathError(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Integer statusCode = (Integer) httpServletRequest.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) httpServletRequest.getAttribute("javax.servlet.error.exception");
        StringBuilder builder=new StringBuilder();
        builder.append("----------ERROR OCCURRED---------\n").append("<><><><><>").append(LocalTime.now().toString())
                .append("<><><><><><>\n")
                .append("Application Type:[")
                .append(properties.settings.applicationType)
                .append("]\n");
        if(statusCode!=null) builder.append("ERROR-STATUS:").append(statusCode);
        if(exception!=null) {
            StackTraceElement[] traceElements=exception.getStackTrace();
            if(traceElements!=null && traceElements.length>0) {
                StackTraceElement traceElement=traceElements[0];
                builder.append("  | CAUSE:").append(exception.getMessage()).append("  |  ON-LINE-NUMBER:").append(traceElement.getLineNumber()).append("  |  ON-FILE:").append(traceElement.getFileName()).append("  |  ON-CLASS:").append(traceElement.getClassName());
            }
        }
        builder.append("  |  FROM-REMOTE_ADDRESS:").append(httpServletRequest.getRemoteAddr()).append("  |  FROM-REMOTE-USER:").append(httpServletRequest.getRemoteUser()).append("  |  ACCESS_URL:").append(httpServletRequest.getRequestURI()).append("\n").append("<><><><><>").append(LocalTime.now().toString()).append("<><><><><><>\n----------ERROR OCCURRED---------\n");
        log.debug(builder.toString());
        return builder.toString();
    }

    public boolean isToday(LocalDateTime dateTime) {
        return isEqualDate(dateTime.toLocalDate(),LocalDateTime.now().toLocalDate());
    }

    public boolean isToday(LocalDate date) {
        return isEqualDate(LocalDate.now(),date);
    }

    public boolean isEqualDate(LocalDate now, LocalDate date) {
        return now.getYear()==date.getYear() && now.getMonth()==date.getMonth() && now.getDayOfMonth()==date.getDayOfMonth();
    }
}
