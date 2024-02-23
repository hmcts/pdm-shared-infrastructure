<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Manage Display</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body data-help-page="view_judge">

    <div id="wrapper">


        <%@ include file="../common/sidebar.jsp"%>

		<%@ include file="../common/header.jsp"%>

        <!-- Page Content -->
        <div id="page-content-wrapper">

            <div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<h3>Manage Judge</h3>

						<c:choose>

							<c:when test="${not empty courtSiteList}">
								<%-- We have court site data so render the form --%>
								<form:form commandName="judgeSearchCommand" action="${context}/judge/view_judge" method="POST" class="form-horizontal">

								<%-- CSRF token --%>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						

								<%-- messages_success is always present so the JS can insert its own messages --%>
								<div id="messages_success" class="alert alert-success" style="${empty successMessage ? 'display:none;' : ''}">
									<c:if test="${not empty successMessage}" >
										<p>${successMessage}</p>
									</c:if>
								</div>

								<%-- messages_error is always present so the JS can insert its own messages --%>
								<spring:hasBindErrors name="judgeSearchCommand">
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
										<div class="col-md-12 ${status.error ? 'has-error' : ''}">
											<%-- Use Spring form:select & spring:eval with form:option to encrypt id --%>
											<form:select path="xhibitCourtSiteId" cssClass="form-control" id="selectSite">
												<form:option value="" label="--- Please select a court site ---" />
												<c:forEach var="courtSite" items="${courtSiteList}">
													<c:set var="courtSiteId"><spring:eval expression="courtSite.id"/></c:set>
													<form:option value="${courtSiteId}" label="${courtSite.courtSiteName}" />
												</c:forEach>
											</form:select>
											<spring:hasBindErrors name="judgeSearchCommand">
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
										<security:authorize access="hasRole('ROLE_ADMIN')"> 
											<button id="btnAmend" class="btn btn-primary" name="btnAmend" value="amend">
												<span class="glyphicon glyphicon-edit"></span> Amend Judge</button>
											<button id="btnAdd" class="btn btn-primary" name="btnAdd" value="add">
												<span class="glyphicon glyphicon-plus"></span> Create Judge</button>
											<button id="btnDelete" class="btn btn-primary" name="btnDelete" value="delete">
												<span class="glyphicon glyphicon-minus"></span> Delete Judge</button>
										</security:authorize>
									</div>
								</div>
							</form:form>

							</c:when>
							<c:otherwise>
							<%-- No court site data found --%>
								<div class="alert alert-danger">
									<p>No Court Sites present in database so unable to View/delete Judges.</p>
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

	<%-- Judge Specific javascript files --%>
	<script type="text/javascript" src="${context}/js/judge.js"></script>

</body>

</html>
