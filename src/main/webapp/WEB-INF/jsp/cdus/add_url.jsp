<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Add URL Mapping</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body data-help-page="add_url">

    <div id="wrapper">

       	<%@ include file="../common/sidebar.jsp"%>

		<%@ include file="../common/header.jsp"%>
	
        <!-- Page Content -->
        <div id="page-content-wrapper">
		
            <div class="container-fluid">
                <div class="row">
					
					<div class="col-md-12">	
							
						<h3>Add URL Mappings</h3>						
						
						<c:if test="${not empty successMessage}" >
							<div class="alert alert-success">
								<p>${successMessage}</p>
							</div>
						</c:if>	
						
						
						<%-- Just show the currently assigned URL mappings for now  --%>
						<c:if test="${not empty cdu}" >
							<h4>Current URL mappings for CDU : <c:out value="${cdu.macAddress}"/> / <c:out value="${cdu.cduNumber}"/></h4>
							<c:choose>
								<c:when test="${not empty cdu.urls}">
								<%-- Non-editable list --%>
									<ul>
										<c:forEach items="${cdu.urls}" var="url">
											<li><c:out value="${url.url} / ${url.description}"/></li>
										</c:forEach>
									</ul>	
																			
								</c:when>
								<c:otherwise>
									<p>No urls are currently mapped to this CDU </p> 
								</c:otherwise>							
							</c:choose>
							
							<form:form 	commandName="mappingCommand" 
										action="${context}/cdus/add_url" 
										method="POST" 
										cssClass="form-horizontal">
							
								<%-- CSRF Guard token where uri equals form action --%>
								<%--<input type="hidden" name="<csrf:tokenname/>" value="<csrf:tokenvalue uri="${context}/pdm/cdus/add_url"/>"/>--%>
																
								<spring:hasBindErrors name="mappingCommand">
									<c:forEach items="${errors.globalErrors}" var="errorMessage">
										<c:if test="${not empty errorMessage.defaultMessage}">
											<div class="alert alert-danger" id="errors">
												<c:out value="${errorMessage.defaultMessage}" />
											</div>
										</c:if>
									</c:forEach>
								</spring:hasBindErrors>
											
								
									<%-- Bind to the urlId if data present --%>
									<c:choose>
										<c:when test="${not empty availableUrls }">
											<%--Urls available --%>
											<div class="form-group">
												<spring:bind path="urlId">
											
													<%-- apply the has-error class if we get any errors back from the bindingresult --%>
													<div class="col-md-8 ${status.error ? 'has-error' : ''}">
															<%-- Use Spring form:select & spring:eval with form:option to encrypt id --%>
															<form:select path="urlId" cssClass="form-control" id="selectUrl">
																<form:option value="" label="--- Please select a Url ---" />
																<c:forEach items="${availableUrls}" var="url">
																	<c:set var="myUrlId"><spring:eval expression="url.id"/></c:set>
																	<form:option value="${myUrlId}" label="${url.url} / ${url.description}" />
																</c:forEach>
															</form:select>
															<%-- Add the actual spring error message underneath the select box --%>
															<spring:hasBindErrors name="cduAmendCommand">
																<div class="help-block" element="span">
																	${errors.hasFieldErrors('urlId') ? errors.getFieldError('urlId').defaultMessage : ''}
																</div>
															</spring:hasBindErrors>
													</div>
												</spring:bind>											
											</div>
											<!-- /form-group -->
								
											<div class="form-group">
												<div class="col-md-6">
													<button id="btnAddMapping" name="btnAddMapping" class="btn btn-primary" data-toggle="modal">
															<span class="glyphicon glyphicon-plus-sign"> </span>
															Add URL Mapping
													</button>
													<a href="<c:url value="/cdus/cdus?reset=false"/>" class="btn btn-primary"><span class="glyphicon glyphicon-circle-arrow-left"></span> Return to CDUS page</a>
												</div>
											</div>	
											<!-- /form-group -->
								
											<%--Hidden fields here --%>
											<form:hidden path="cduId" />
										
										
										</c:when>
										<c:otherwise>
											
											<%--Nothing to map --%>
											<div class="alert alert-info">
												<p>There are currently no available urls for this CDU</p>
												<a href="<c:url value="/cdus/cdus?reset=false"/>" class="btn btn-primary"><span class="glyphicon glyphicon-circle-arrow-left"></span> Return to CDUS page</a>
											</div>
										</c:otherwise>
									</c:choose>
						  </form:form>						  
						</c:if>
					</div>
				</div>
				<!-- /row  -->
			</div>
			<!--  /container-fluid -->
		</div>
		<!--  /page-content-wrapper -->
		
		<%@ include file="../common/footer.jsp"%>

	</div>
	<!--  /wrapper -->
	
	<%@ include file="../common/scripts.jsp"%>
	
	<%-- CDU Specific javascript files --%>
	<script type="text/javascript" src="${context}/js/cdus.js"></script>

</body>

</html>
