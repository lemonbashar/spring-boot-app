package com.lemon.spring.controller.rest;

import com.lemon.spring.controller.web.HomeControllerWeb;
import com.lemon.spring.service.ApplicationService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("unused")
@RestController
public class DefaultErrorController implements ErrorController {
    private static final String ERROR_PATH="/error";
    @Inject
    private HomeControllerWeb homeControllerWeb;

    @Inject
    private ApplicationService applicationService;

    @RequestMapping(value = ERROR_PATH)
    public String error(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        applicationService.logPathError(httpServletRequest,httpServletResponse);
        return homeControllerWeb.home();
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
