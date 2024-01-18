<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Manage Local Proxy</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body data-help-page="view_localproxy">

    <div id="wrapper">


        <%@ include file="../common/sidebar.jsp"%>

		<%@ include file="../common/header.jsp"%>

        <!-- Page Content -->
        <div id="page-content-wrapper">

            <div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<h3>Manage Local Proxy</h3>

						<c:choose>

							<c:when test="${not empty courtSiteList}">
								<%-- We have court site data so render the form --%>
								<form:form commandName="localProxySearchCommand" action="${context}/proxies/view_localproxy" method="POST" class="form-horizontal">

								<%-- CSRF Guard token where uri equals form action --%>
								<%--<input type="hidden" name="<csrf:tokenname/>" value="<csrf:tokenvalue uri="${context}/proxies/view_localproxy"/>"/>--%>


								<%-- messages_success is always present so the JS can insert its own messages --%>
								<div id="messages_success" class="alert alert-success" style="${empty successMessage ? 'display:none;' : ''}">
									<c:if test="${not empty successMessage}" >
										<p>${successMessage}</p>
									</c:if>
								</div>

								<%-- messages_error is always present so the JS can insert its own messages --%>
								<spring:hasBindErrors name="localProxySearchCommand">
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
										<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.localProxySearchCommand'].hasFieldErrors('xhibitCourtSiteId') ? 'has-error' : ''}">
											<%-- Use Spring form:select & spring:eval with form:option to encrypt id --%>
											<form:select path="xhibitCourtSiteId" cssClass="form-control" id="selectSite">
												<form:option value="" label="--- Please select a court site ---" />
												<c:forEach var="courtSite" items="${courtSiteList}">
													<c:set var="courtSiteId"><spring:eval expression="courtSite.id"/></c:set>
													<form:option value="${courtSiteId}" label="${courtSite.courtSiteName}" />
												</c:forEach>
											</form:select>
											<spring:hasBindErrors name="localProxySearchCommand">
												<div class="help-block" element="span">
													${errors.hasFieldErrors('xhibitCourtSiteId') ? errors.getFieldError('xhibitCourtSiteId').defaultMessage : ''}
												</div>
											</spring:hasBindErrors>
										</div>
									</spring:bind>
								</div>

								<%-- button group --%>
								<div class="form-group">
									<div class="col-md-12">
										<button id="btnView" class="btn btn-primary" name="viewlocalproxy" value="view">
											<span class="glyphicon glyphicon-search"></span> View Local Proxy</button>
                    <%-- COMMENTING OUT AUTHENTICATION BLOCK TEMPORARILY FOR TESTING --%>
										<%-- <security:authorize access="hasRole('ROLE_ADMIN')"> --%>
											<button id="btnAmend" class="btn btn-primary" name="btnAmend" value="amend">
												<span class="glyphicon glyphicon-edit"></span> Amend Local Proxy</button>
											<button type="button" id="btnUnregister" class="btn btn-primary" name="unregister" data-toggle="modal" data-target="#unregisterModal">
												<span class="glyphicon glyphicon-minus"></span> Unregister Local Proxy</button>
										<%-- </security:authorize>	--%>
									</div>
								</div>

								<%-- render the local proxy details if present.... --%>
								<div id="proxyInformation" class="form-group">
									<div class="col-md-12">
										<c:if test="${not empty courtSite}" >
											<%-- add in our local proxy panel --%>
											<div class="panel panel-primary">
												<div class="panel-heading">
													Local Proxy Details
												</div>

												<div class="panel-body">
													<p><strong>Title :</strong> <c:out value="${courtSite.title}"/></p>
													<p><strong>IP Address :</strong> <c:out value="${courtSite.ipAddress}"/></p>
													<p><strong>Site Page URL :</strong> <c:out value="${courtSite.pageUrl}"/></p>
													<p><strong>Site Operating Hours :</strong> <c:out value="${courtSite.scheduleTitle}"/></p>
													<p><strong>Notification :</strong> <span style="white-space:pre"><c:out value="${courtSite.notification}"/></span></p>
												</div>
											</div>
										</c:if>
									</div>
								</div>
								<!-- Modal dialog for unregister local proxy, could do this as an include if required -->
								<div id="unregisterModal" class="modal fade" role="dialog">

									<div class="modal-dialog">

										<div class="modal-content">

											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">Unregister LocalProxy</h4>

											</div>

											<div class="modal-body">

												<p>You are about to unregister this Local Proxy. Do you wish to continue?</p>

											</div>

											<div class="modal-footer">

												<button class="btn btn-primary" id="unregisterConfirm" name="unregisterConfirm">Confirm</button>
												<button class="btn btn-default" data-dismiss="modal">Cancel</button>

											</div>
										</div>
									</div>
								</div><!-- end of unregisterModal -->
							</form:form>

							</c:when>
							<c:otherwise>
							<%-- No court site data found --%>
								<div class="alert alert-danger">
									<p>No Court Sites present in database so unable to View/Unregister Proxies.</p>
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

	<%-- Local Proxy Specific javascript files --%>
	<script type="text/javascript" src="${context}/js/proxies.js"></script>

</body>

</html>
