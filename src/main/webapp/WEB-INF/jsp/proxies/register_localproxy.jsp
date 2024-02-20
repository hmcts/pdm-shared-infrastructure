<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Register Local Proxy</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body data-help-page="register_localproxy">

    <div id="wrapper">


        <%@ include file="../common/sidebar.jsp"%>

		<%@ include file="../common/header.jsp"%>

        <!-- Page Content -->
        <div id="page-content-wrapper">

            <div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<h3>Register Local Proxy</h3>

						<c:if test="${not empty successMessage}" >
							<div class="alert alert-success">
								<p>${successMessage}</p>
							</div>
						</c:if>

						<c:choose>

							<c:when test="${not empty courtSiteList}">
								<%-- We have court site data so render the form --%>
								<form:form commandName="localProxyRegisterCommand" action="${context}/proxies/register_localproxy" method="POST" class="form-horizontal">

								<%-- CSRF token --%>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
								<%--
									This error block must be within the form:form tags , otherwise you won't get any errors back !!
								--%>
								<spring:hasBindErrors name="localProxyRegisterCommand">
									<c:forEach items="${errors.globalErrors}" var="errorMessage">
										<c:if test="${not empty errorMessage.defaultMessage}">
											<div class="alert alert-danger" id="errors">
												<c:out value="${errorMessage.defaultMessage}" />
											</div>
										</c:if>
									</c:forEach>
								</spring:hasBindErrors>

								<div class="form-group">
									<spring:bind path="xhibitCourtSiteId">
										<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.localProxyRegisterCommand'].hasFieldErrors('xhibitCourtSiteId') ? 'has-error' : ''}">
											<%-- Use Spring form:select & spring:eval with form:option to encrypt id --%>
											<form:select path="xhibitCourtSiteId" cssClass="form-control" id="xhibitCourtSiteId">
												<form:option value="" label="--- Please select a court site ---" />
												<c:forEach var="courtSite" items="${courtSiteList}">
													<c:set var="courtSiteId"><spring:eval expression="courtSite.id"/></c:set>
													<form:option value="${courtSiteId}" label="${courtSite.courtSiteName}" />
												</c:forEach>
											</form:select>
											<spring:hasBindErrors name="localProxyRegisterCommand">
												<div class="help-block" element="span">
													${errors.hasFieldErrors('xhibitCourtSiteId') ? errors.getFieldError('xhibitCourtSiteId').defaultMessage : ''}
												</div>
											</spring:hasBindErrors>
										</div>
									</spring:bind>
								</div>

								<%-- Local Proxy Title, again use spring:bind  so we can access any binding errors--%>
								<div class="form-group">
									<spring:bind path="title">
										<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.localProxyRegisterCommand'].hasFieldErrors('title') ? 'has-error' : ''}">
											<label for="title">Title *</label>
											<form:input placeholder="Title"
														path="title"
														id="title"
														maxlength="255"
														cssClass="form-control"/>
											<spring:hasBindErrors name="localProxyRegisterCommand">
												<div class="help-block" element="span">
													${errors.hasFieldErrors('title') ? errors.getFieldError('title').defaultMessage : ''}
												</div>
											</spring:hasBindErrors>
										</div>
									</spring:bind>
								</div>

								<%-- Local Proxy IP Address, again use spring:bind  so we can access any binding errors--%>
								<div class="form-group">
									<spring:bind path="ipAddress">
										<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.localProxyRegisterCommand'].hasFieldErrors('ipAddress') ? 'has-error' : ''}">
											<label for="ipAddress">IP Address *</label>
											<form:input placeholder="IP Address"
														path="ipAddress"
														id="ipAddress"
														maxlength="100"
														cssClass="form-control"/>
											<spring:hasBindErrors name="localProxyRegisterCommand">
												<div class="help-block" element="span">
													${errors.hasFieldErrors('ipAddress') ? errors.getFieldError('ipAddress').defaultMessage : ''}
												</div>
											</spring:hasBindErrors>
										</div>
									</spring:bind>
								</div>

								<%-- Schedule, mandatory numeric --%>
								<div class="form-group">
									<spring:bind path="scheduleId">
										<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.localProxyRegisterCommand'].hasFieldErrors('scheduleId') ? 'has-error' : ''}">
											<label for="scheduleId">Site Operating Hours *</label>
											<%-- Use Spring form:select & spring:eval with form:option to encrypt id --%>
											<form:select path="scheduleId" cssClass="form-control" id="scheduleId">
												<form:option value="" label="--- Please select the Operating Hours ---" />
												<c:forEach var="schedules" items="${scheduleList}">
													<c:set var="scheduleId"><spring:eval expression="schedules.id"/></c:set>
													<form:option value="${scheduleId}" label="${schedules.title}" />
												</c:forEach>
											</form:select>
											<spring:hasBindErrors name="localProxyRegisterCommand">
												<div class="help-block" element="span">
													${errors.hasFieldErrors('scheduleId') ? errors.getFieldError('scheduleId').defaultMessage : ''}
												</div>
											</spring:hasBindErrors>
										</div>
									</spring:bind>
								</div>

								<%-- Notification, optional up to 500 chars --%>
								<div class="form-group">
									<spring:bind path="notification">
										<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.localProxyRegisterCommand'].hasFieldErrors('notification') ? 'has-error' : ''}">
											<label for="notification">Notification</label>
											<form:textarea placeholder="Notification"
														path="notification"
														id="notification"
														rows="10"
														cols="50"
														cssClass="form-control"/>
											<spring:hasBindErrors name="localProxyRegisterCommand">
												<div class="help-block" element="span">
													${errors.hasFieldErrors('notification') ? errors.getFieldError('notification').defaultMessage : ''}
												</div>
											</spring:hasBindErrors>
										</div>
									</spring:bind>
								</div>

								<div class="form-group">
									<div class="col-md-12">
										<button id="btnregister" class="btn btn-primary" name="registerlocalproxy" ><span class="glyphicon glyphicon-plus-sign"></span> Register Local Proxy</button>
									</div>
								</div>

							</form:form>

							</c:when>
							<c:otherwise>
							<%-- No court site data found --%>
								<div class="alert alert-danger">
									<p>No Court Sites present in database so unable to Register Proxy.</p>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div> <!-- End row 1 -->
            </div>
			<!-- /container-fluid -->
        </div>
        <!-- /#page-content-wrapper -->
		<%@ include file="../common/footer.jsp"%>

    </div>
    <!-- /#wrapper -->

	<%@ include file="../common/scripts.jsp"%>




</body>

</html>
