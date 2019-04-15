package com.lemon.spring.controller.rest;

import com.lemon.spring.config.Constants;
import com.lemon.spring.controller.web.HomeControllerWeb;
import com.lemon.spring.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("unused")
@Profile(Constants.PROFILE_DEVELOPMENT)
@RestController
public class DefaultErrorController implements ErrorController {
    private static final String ERROR_PATH = "/error";
    @Autowired(required = false)
    private HomeControllerWeb homeControllerWeb;

    @Inject
    private ApplicationService applicationService;

    @RequestMapping(value = ERROR_PATH, produces = "text/plain")
    public String error(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String error = applicationService.logPathError(httpServletRequest, httpServletResponse);
        if (homeControllerWeb != null) return homeControllerWeb.home();
        return error;
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
