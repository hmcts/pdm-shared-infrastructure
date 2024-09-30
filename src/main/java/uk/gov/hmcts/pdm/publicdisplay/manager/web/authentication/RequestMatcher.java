package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication;

import jakarta.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface RequestMatcher {

    String INTERNAL_URL_MATCHER = "/login";

    RequestMatcher URL_MAPPER_INTERNAL = (req) -> req.getRequestURL().toString().contains(INTERNAL_URL_MATCHER);

    boolean doesMatch(HttpServletRequest req);
}