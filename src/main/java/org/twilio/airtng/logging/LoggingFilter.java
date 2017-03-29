package org.twilio.airtng.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ch.qos.logback.access.AccessConstants.LB_INPUT_BUFFER;
import static ch.qos.logback.access.AccessConstants.LB_OUTPUT_BUFFER;

public class LoggingFilter implements Filter{
    private static final String TEMPLATE_WITH_BODY =
            "\nRequest {} {} {} HEADERS:[{}] BODY: {}\nResponse {} HEADERS: [{}] BODY: {} ";
    private static final String TEMPLATE_WITH_NO_BODY =
            "\nRequest {} {} {} HEADERS:[{}] \nResponse {} HEADERS: [{}]";
    private static Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        StatusExposingServletResponse response = new StatusExposingServletResponse((HttpServletResponse)servletResponse);
        chain.doFilter(request, response);

        if(!(request instanceof HttpServletRequest && response instanceof HttpServletResponse)) {
            logger.warn("Couldn't log the request as the request/response are not of the expected type");
            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String requestHeaderString = buildHeadersString(Collections.list(httpRequest.getHeaderNames()),
                n -> httpRequest.getHeader(n));
        String responseHeaderString = response.getHeaderMap().entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(","));

        String template;
        Object[] params;
        if(logger.isDebugEnabled()) {
            byte[] requestBody = (byte[]) request.getAttribute(LB_INPUT_BUFFER);
            byte[] responseBody = (byte[]) request.getAttribute(LB_OUTPUT_BUFFER);

            template = TEMPLATE_WITH_BODY;
            params = new Object[] {
                    httpRequest.getMethod(),
                    httpRequest.getPathInfo(),
                    httpRequest.getProtocol(),
                    requestHeaderString,
                    byteArrayToString(requestBody),
                    response.getStatus(),
                    responseHeaderString,
                    byteArrayToString(responseBody)
            };
        } else {
            template = TEMPLATE_WITH_NO_BODY;
            params = new Object[] {
                    httpRequest.getMethod(),
                    httpRequest.getPathInfo(),
                    httpRequest.getProtocol(),
                    requestHeaderString,
                    response.getStatus(),
                    responseHeaderString
            };
        }
        logger.info(template, params);
    }

    private String byteArrayToString(byte[] requestBody) {
        return requestBody != null ? new String(requestBody, Charset.defaultCharset()) : "";
    }

    private String buildHeadersString(Collection<String> headers, Function<String, String> getHeader) {
        return headers
                .stream()
                .map(h -> h + ":" + getHeader.apply(h))
                .collect(Collectors.joining(", "));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}

class StatusExposingServletResponse extends HttpServletResponseWrapper {

    private int httpStatus = SC_OK;

    private Map<String, String> headers;

    public StatusExposingServletResponse(HttpServletResponse response) {
        super(response);
        headers = new HashMap<>();
    }

    @Override
    public void addHeader(String name, String value) {
        headers.put(name, value);
        super.addHeader(name, value);
    }

    @Override
    public void setHeader(String name, String value) {
        headers.put(name, value);
        super.setHeader(name, value);
    }

    @Override
    public void sendError(int sc) throws IOException {
        httpStatus = sc;
        super.sendError(sc);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        httpStatus = sc;
        super.sendError(sc, msg);
    }

    @Override
    public void setStatus(int sc) {
        httpStatus = sc;
        super.setStatus(sc);
    }


    @Override
    public void sendRedirect(String location) throws IOException {
        httpStatus = SC_MOVED_TEMPORARILY;
        super.sendRedirect(location);
    }

    public int getStatus() {
        return httpStatus;
    }

    public Map<String, String> getHeaderMap() {
        return headers;
    }

}
