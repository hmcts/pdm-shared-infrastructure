package uk.gov.hmcts.pdm.publicdisplay.manager.web.logon;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class MutableHttpServletRequest extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders;

    public MutableHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.customHeaders = new ConcurrentHashMap<>();
    }

    /**
     * addHeader.
     */
    public void addHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }

    /**
     * getHeader.
     */
    @Override
    public String getHeader(String name) {
        if (customHeaders.get(name) != null) {
            return customHeaders.get(name);
        }
        return getOriginalRequest().getHeader(name);
    }

    /**
     * getHeaderNames.
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        // create a set of the custom header names
        Set<String> set = new HashSet<>(customHeaders.keySet());

        // now add the headers from the wrapped request object
        Enumeration<String> element = getOriginalRequest().getHeaderNames();
        while (element.hasMoreElements()) {
            set.add(element.nextElement());
        }

        // create an enumeration from the set and return
        return Collections.enumeration(set);
    }

    private HttpServletRequest getOriginalRequest() {
        return (HttpServletRequest) getRequest();
    }
}
