<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Create Display</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body data-help-page="create_display">

    <div id="wrapper">


        <%@ include file="../common/sidebar.jsp"%>

		<%@ include file="../common/header.jsp"%>

        <!-- Page Content -->
        <div id="page-content-wrapper">

            <div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<h3>Create Display</h3>

						<c:if test="${not empty successMessage}" >
							<div class="alert alert-success">
								<p>${successMessage}</p>
							</div>
						</c:if>

						<form:form commandName="displayCreateCommand"
						           action="${context}/display/create_display"
						           method="POST"
						           class="form-horizontal">

						<%-- CSRF token --%>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
						<%--
							This error block must be within the form:form tags , otherwise you won't get any errors back !!
						--%>
						<spring:hasBindErrors name="displayCreateCommand">
							<c:forEach items="${errors.globalErrors}" var="errorMessage">
								<c:if test="${not empty errorMessage.defaultMessage}">
									<div class="alert alert-danger" id="errors">
										<c:out value="${errorMessage.defaultMessage}" />
									</div>
								</c:if>
							</c:forEach>
						</spring:hasBindErrors>
						<!-- /form-group -->

						<div class="form-group">
							<%-- Description, mandatory, 30 chars --%>
							<spring:bind path="descriptionCode">
								<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.displayCreateCommand'].hasFieldErrors('descriptionCode') ? 'has-error' : ''}">
										<label for="descriptionCode">Location Description *</label>
										<form:input placeholder=""
													path="descriptionCode"
													id="descriptionCode"
													style="text-transform: lowercase"
													maxlength="30"
													cssClass="form-control"/>
										<spring:hasBindErrors name="displayCreateCommand">
											<div class="help-block" element="span">
												${errors.hasFieldErrors('descriptionCode') ? errors.getFieldError('descriptionCode').defaultMessage : ''}
											</div>
										</spring:hasBindErrors>
								</div>
							</spring:bind>

							<%-- Display Type --%>
							<spring:bind path="displayTypeId">
								<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.displayCreateCommand'].hasFieldErrors('displayTypeId') ? 'has-error' : ''}">
									<label for="displayTypeId">Display Type *</label>
									<%-- Use Spring form:select & spring:eval with form:option to encrypt id --%>
									<form:select path="displayTypeId" cssClass="form-control" id="selectDisplayType">
										<form:option value="" label="--- Please select a display type ---" />
										<c:forEach var="displayType" items="${displayTypeList}">
											<c:set var="displayTypeId"><spring:eval expression="displayType.displayTypeId"/></c:set>
											<form:option value="${displayTypeId}" label="${displayType.descriptionCode}" />
										</c:forEach>
									</form:select>
									<spring:hasBindErrors name="displayCreateCommand">
										<div class="help-block" element="span">
											${errors.hasFieldErrors('displayTypeId') ? errors.getFieldError('displayTypeId').defaultMessage : ''}
										</div>
									</spring:hasBindErrors>
								</div>
							</spring:bind>

							<%-- Court Site --%>
							<spring:bind path="xhibitCourtSiteId">
								<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.displayCreateCommand'].hasFieldErrors('xhibitCourtSiteId') ? 'has-error' : ''}">
									<label for="xhibitCourtSiteId">Court Site *</label>
									<%-- Use Spring form:select & spring:eval with form:option to encrypt id --%>
									<form:select path="xhibitCourtSiteId" cssClass="form-control" id="selectCourtSite">
										<form:option value="" label="--- Please select a court site ---" />
										<c:forEach var="courtSite" items="${courtSiteList}">
											<c:set var="courtSiteId"><spring:eval expression="courtSite.id"/></c:set>
											<form:option value="${courtSiteId}" label="${courtSite.courtSiteCode} - ${courtSite.courtSiteName}" />
										</c:forEach>
									</form:select>
									<spring:hasBindErrors name="displayCreateCommand">
										<div class="help-block" element="span">
											${errors.hasFieldErrors('xhibitCourtSiteId') ? errors.getFieldError('xhibitCourtSiteId').defaultMessage : ''}
										</div>
									</spring:hasBindErrors>
								</div>
							</spring:bind>

							<%-- Rotation Set --%>
							<spring:bind path="rotationSetId">
								<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.displayCreateCommand'].hasFieldErrors('rotationSetId') ? 'has-error' : ''}">
									<label for="rotationSetId">Rotation Set *</label>
									<%-- Use Spring form:select & spring:eval with form:option to encrypt id --%>
									<form:select path="rotationSetId" cssClass="form-control" id="selectRotationSet">
										<form:option value="" label="--- Please select a rotation set ---" />
										<c:forEach var="rotationSet" items="${rotationSetList}">
											<c:set var="rotationSetId"><spring:eval expression="rotationSet.rotationSetId"/></c:set>
											<form:option value="${rotationSetId}" label="${rotationSet.description}" />
										</c:forEach>
									</form:select>
									<spring:hasBindErrors name="displayCreateCommand">
										<div class="help-block" element="span">
											${errors.hasFieldErrors('rotationSetId') ? errors.getFieldError('rotationSetId').defaultMessage : ''}
										</div>
									</spring:hasBindErrors>
								</div>
							</spring:bind>
						</div>

						<div class="form-group">
							<div class="col-md-12">
							    <button id="btnCreateConfirm" class="btn btn-primary" name="btnCreateConfirm"><span class="glyphicon glyphicon-edit"></span> Create Display</button>
								<a href="<c:url value="/display/view_display?reset=false"/>" class="btn btn-primary"><span class="glyphicon glyphicon-circle-arrow-left"></span> Return to Manage Display</a>
							</div>
						</div>
						<!-- /form-group -->

					</form:form>
					<!-- /form -->

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

	<script type="text/javascript" src="${context}/js/display.js"></script>


</body>

</html>
