package com.lemon.spring.controller.rest;

import com.lemon.spring.controller.web.AccountControllerWeb;
import com.lemon.spring.controller.web.HomeControllerWeb;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class DefaultErrorController implements ErrorController {
    private static final String ERROR_PATH="/error";

    @RequestMapping(value = ERROR_PATH)
    public String error(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        return "Internal Error Occurred";
    }

    /**
     * Returns the path of the error page.
     *
     * @return the error path
     */
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
