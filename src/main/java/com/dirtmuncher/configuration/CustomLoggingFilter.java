package com.dirtmuncher.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.CommonsRequestLoggingFilter;


/**
 * CustomLoggingFilter allows for tweaking the message logged request message to add the request addr IP
 */
public class CustomLoggingFilter extends CommonsRequestLoggingFilter {

    @Override
    protected void beforeRequest(HttpServletRequest request,  String message) {
        String enhancedMessage = request.getRemoteAddr() + " " + message; // Retrieve the IP address
        super.beforeRequest(request, message + enhancedMessage);
    }

}
