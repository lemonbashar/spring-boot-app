package com.lemon.spring.service;

import com.lemon.spring.controller.rest.DefaultErrorController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

@Service
public class ApplicationService {
    protected final Logger log= Logger.getLogger(DefaultErrorController.class);


    public void logPathError(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Integer statusCode = (Integer) httpServletRequest.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) httpServletRequest.getAttribute("javax.servlet.error.exception");
        StringBuilder builder=new StringBuilder();
        builder.append("<><><><><>").append(LocalTime.now().toString()).append("<><><><><><>\n----------ERROR OCCURRED---------\n");
        if(statusCode!=null) builder.append("ERROR-STATUS:").append(statusCode);
        if(exception!=null) {
            StackTraceElement[] traceElements=exception.getStackTrace();
            if(traceElements!=null && traceElements.length>0) {
                StackTraceElement traceElement=traceElements[0];
                builder.append("  | CAUSE:").append(exception.getMessage()).append("  |  ON-LINE-NUMBER:").append(traceElement.getLineNumber()).append("  |  ON-FILE:").append(traceElement.getFileName()).append("  |  ON-CLASS:").append(traceElement.getClassName());
            }
        }
        builder.append("FROM-REMOTE_ADDRESS:").append(httpServletRequest.getRemoteAddr()).append("  |  FROM-REMOTE-USER:").append(httpServletRequest.getRemoteUser()).append("  |  ACCESS_URL:").append(httpServletRequest.getRequestURI()).append("\n").append("<><><><><>").append(LocalTime.now().toString()).append("<><><><><><>\n----------ERROR OCCURRED---------\n");
        log.debug(builder.toString());
    }
}
