<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Send the user to the home page which will first redirect the
	 user to the login page if they are not already authenticated
	 because of the web.xml security-constraint configuration --%>
<c:redirect url="/pdm/home" />