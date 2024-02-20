<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Amend Display</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body data-help-page="delete_display">
    <div id="wrapper">

       
        <%@ include file="../common/sidebar.jsp"%>
        
		<%@ include file="../common/header.jsp"%>
	
        <!-- Page Content -->
        <div id="page-content-wrapper">
		
            <div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<h3>Delete Display</h3>
						
						<c:if test="${not empty successMessage}" >
							<div class="alert alert-success">
								<p>${successMessage}</p>
							</div>
						</c:if>		
						
						<form:form commandName="displayDeleteCommand" 
						           action="${context}/display/delete_display" 
						           method="POST" 
						           class="form-horizontal">

						<%-- CSRF token --%>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
						<%-- 
							This error block must be within the form:form tags , otherwise you won't get any errors back !!
						--%>
						<spring:hasBindErrors name="displayDeleteCommand">
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
							<%-- Displays --%>
							<spring:bind path="displayId">
								<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.displayDeleteCommand'].hasFieldErrors('displayId') ? 'has-error' : ''}">
									<label for="displayId">Location Description *</label>	
									<%-- Use Spring form:select & spring:eval with form:option to encrypt id --%>
									<form:select path="displayId" cssClass="form-control" id="selectDisplay">
										<form:option value="" label="--- Please select a location ---" />
										<c:forEach var="display" items="${displayList}">
											<c:set var="displayId"><spring:eval expression="display.displayId"/></c:set>
											<form:option value="${displayId}" label="${display.descriptionCode}" />
										</c:forEach>
									</form:select>
									<spring:hasBindErrors name="displayDeleteCommand">
										<div class="help-block" element="span">
											${errors.hasFieldErrors('displayId') ? errors.getFieldError('displayId').defaultMessage : ''}
										</div>
									</spring:hasBindErrors>
								</div>
							</spring:bind>

							<%-- Display Type --%>
							<div class="col-md-12">
								<div><strong>Display Type :</strong></div><div id="displayTypeDescriptionCode" class="form-control"></div>
							</div>
							<%-- Court Site --%>
							<div class="col-md-12">
								<div><strong>Court Site :</strong></div><div id="courtSiteCourtSiteName" class="form-control"></div>
							</div>
							<%-- Rotation Set --%>
							<div class="col-md-12">
								<div><strong>Rotation Set :</strong></div><div id="rotationSetDescription" class="form-control"></div>
							</div>
						</div>

						<div class="form-group">
							<div class="col-md-12">																												
							    <button id="btnDeleteConfirm" class="btn btn-primary" name="btnDeleteConfirm"><span class="glyphicon glyphicon-edit"></span> Delete Display</button>
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
	
	<script type="text/javascript" src="${context}/js/delete_display.js"></script>   


</body>

</html>
