package com.zupbank.bank.api;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@UtilityClass
public class ResourceUriHelper {

    public static void addUriInResponseHeader(Object resourceId) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}/step-2")
                .buildAndExpand(resourceId).toUri();

        HttpServletResponse response = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getResponse();

        response.setHeader(HttpHeaders.LOCATION, uri.toString());
    }

    public static void addUriInResponseHeaderStep2(Object resourceId) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .replacePath("v1/proposals/{id}/step-3")
                .buildAndExpand(resourceId).toUri();

        HttpServletResponse response = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getResponse();

        response.setHeader(HttpHeaders.LOCATION, uri.toString());
    }

    public static void addUriInResponseHeaderStep3(Object resourceId) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .replacePath("v1/proposals/{id}/accept")
                .buildAndExpand(resourceId).toUri();

        HttpServletResponse response = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getResponse();
        response.setHeader(HttpHeaders.LOCATION, uri.toString());
    }

    public static void addUriInResponseHeaderToAccept(Object resourceId) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .replacePath("v1/proposals/{id}")
                .buildAndExpand(resourceId).toUri();

        HttpServletResponse response = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getResponse();
        response.setHeader(HttpHeaders.LOCATION, uri.toString());
    }
}
