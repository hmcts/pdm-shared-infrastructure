<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Manage Court</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body data-help-page="view_court">

    <div id="wrapper">


        <%@ include file="../common/sidebar.jsp"%>

		<%@ include file="../common/header.jsp"%>

        <!-- Page Content -->
        <div id="page-content-wrapper">

            <div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<h3>Manage Court</h3>

						<c:choose>

							<c:when test="${not empty courtList}">
								<%-- We have court site data so render the form --%>
								<form:form commandName="courtSearchCommand" action="${context}/court/view_court" method="POST" class="form-horizontal">

								<%-- CSRF Guard token where uri equals form action --%>
								<%--<input type="hidden" name="<csrf:tokenname/>" value="<csrf:tokenvalue uri="${context}/court/view_court"/>"/>--%>


								<%-- messages_success is always present so the JS can insert its own messages --%>
								<div id="messages_success" class="alert alert-success" style="${empty successMessage ? 'display:none;' : ''}">
									<c:if test="${not empty successMessage}" >
										<p>${successMessage}</p>
									</c:if>
								</div>

								<%-- messages_error is always present so the JS can insert its own messages --%>
								<spring:hasBindErrors name="courtSearchCommand">
									<c:forEach items="${errors.globalErrors}" var="errorMessage">
										<c:if test="${not empty errorMessage.defaultMessage}">
											<div class="alert alert-danger" id="errors">
												<c:out value="${errorMessage.defaultMessage}" />
											</div>
										</c:if>
									</c:forEach>
								</spring:hasBindErrors>

								<div class="form-group">
									<spring:bind path="courtId">
										<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.courtSearchCommand'].hasFieldErrors('courtId') ? 'has-error' : ''}">
											<%-- Use Spring form:select & spring:eval with form:option to encrypt id --%>
											<form:select path="courtId" cssClass="form-control" id="selectCourt">
												<form:option value="" label="--- Please select a court ---" />
												<c:forEach var="court" items="${courtList}">
													<c:set var="courtId"><spring:eval expression="court.id"/></c:set>
													<form:option value="${courtId}" label="${court.courtName}" />
												</c:forEach>
											</form:select>
											<spring:hasBindErrors name="courtSearchCommand">
												<div class="help-block" element="span">
													${errors.hasFieldErrors('courtId') ? errors.getFieldError('courtId').defaultMessage : ''}
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
												<span class="glyphicon glyphicon-edit"></span> Amend Court</button>
											<button id="btnAdd" class="btn btn-primary" name="btnAdd" value="add">
												<span class="glyphicon glyphicon-plus"></span> Create Court</button>
										</security:authorize>
									</div>
								</div>
							</form:form>

							</c:when>
							<c:otherwise>
							<%-- No court data found --%>
								<div class="alert alert-danger">
									<p>No Courts present in database so unable to View/delete Courts.</p>
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

	<%-- Local display Specific javascript files --%>
	<script type="text/javascript" src="${context}/js/court.js"></script>

</body>

</html>
