<%-- Start of include for main tag libraries for XHIBIT Public display manager --%>
<%@ page contentType="text/html; charset=UTF-8" %>

<%-- Basic Core, FMT and FN tags --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Spring Tags --%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%-- CSRF Guard Tags --%> 
<%@ taglib prefix="csrf" uri="/WEB-INF/tld/Owasp.CsrfGuard.tld" %>

<%-- Obtain current web application context and store for later use --%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<%-- End of include for main tag libraries for XHIBIT Public display manager --%>