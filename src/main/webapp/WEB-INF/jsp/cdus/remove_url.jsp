<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Remove URL Mapping</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body data-help-page="remove_url">

    <div id="wrapper">

       	<%@ include file="../common/sidebar.jsp"%>

		<%@ include file="../common/header.jsp"%>
	
        <!-- Page Content -->
        <div id="page-content-wrapper">
		
            <div class="container-fluid">
                <div class="row">
					
					<div class="col-md-12">	
						<h3>Remove URL Mappings </h3>
						
						<c:if test="${not empty successMessage}" >
							<div class="alert alert-success">
								<p>${successMessage}</p>
							</div>
						</c:if>		
						
						<%-- Render form only if we have a cdu to work on  --%>
						<c:if test="${not empty cdu}" >
							<h4>Current URL mappings for CDU : <c:out value="${cdu.macAddress}"/> / <c:out value="${cdu.cduNumber}"/></h4>
							<c:choose>
								<c:when test="${not empty cdu.urls}">
									<form:form 	commandName="mappingCommand" 
										action="${context}/cdus/remove_url" 
										method="POST" 
										cssClass="form-horizontal">
										
										<%-- CSRF Guard token where uri equals form action --%>
										<%--<input type="hidden" name="<csrf:tokenname/>" value="<csrf:tokenvalue uri="${context}/pdm/cdus/remove_url"/>"/>--%>
										
										<%-- 
											This error block must be within the form:form tags , otherwise you won't get any errors back !!
										--%>
										
										<spring:hasBindErrors name="mappingCommand">
											<c:forEach items="${errors.globalErrors}" var="errorMessage">
												<c:if test="${not empty errorMessage.defaultMessage}">
													<div class="alert alert-danger" id="errors">
														<c:out value="${errorMessage.defaultMessage}" />
													</div>
												</c:if>
											</c:forEach>
										</spring:hasBindErrors>
													
										<div class="form-group">
											<%-- Bind to the urlId --%>
											<spring:bind path="urlId">
												
												<%-- apply the has-error class if we get any errors back from the bindingresult --%>
												<div class="col-md-8 ${status.error ? 'has-error' : ''}">
														<%-- Use Spring form:select & spring:eval with form:option to encrypt id --%>
														<form:select path="urlId" cssClass="form-control" id="selectUrl">
															<form:option value="" label="--- Please select a Url Mapping ---" />
															<c:forEach items="${cdu.urls}" var="url">
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
												<button type="button" id="btnRemoveMapping" name="btnRemoveMapping" class="btn btn-primary" data-toggle="modal" data-target="#removemappingModal">
														<span class="glyphicon glyphicon-minus-sign"> </span>
														Remove URL Mapping
												</button>
												<a href="<c:url value="/cdus/cdus?reset=false"/>" class="btn btn-primary"><span class="glyphicon glyphicon-circle-arrow-left"></span> Return to CDUS page</a>							
											</div>								
										</div>	
										<!-- /form-group -->
										
										<%--Hidden fields here --%>
										<form:hidden path="cduId" />
										
										<!-- Modal dialog for Remove mapping, could do this as an include if required -->
										<div id="removemappingModal" class="modal fade" role="dialog">
										
											<div class="modal-dialog">
											
												<div class="modal-content">
													
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal">&times;</button>
														<h4 class="modal-title">Remove URL Mapping</h4>
														
													</div>
													
													<div class="modal-body">
													
														<p>You are about to remove this URL mapping. Do you wish to continue?</p>
													
													</div>
													
													<div class="modal-footer">
													
														<button class="btn btn-primary" id="btnRemoveMappingConfirm" name="btnRemoveMappingConfirm">Confirm</button>
														<button class="btn btn-default" data-dismiss="modal">Cancel</button>
													
													</div>						
												</div>				
											</div>			
										</div><!-- end of removemappingModal -->
							
										
									</form:form>	
									<!-- /form -->							
								</c:when>
								<c:otherwise>
								    <div class="alert alert-info">
									  <p>No urls are currently mapped to this CDU</p>
									  <a href="<c:url value="/cdus/cdus?reset=false"/>" class="btn btn-primary"><span class="glyphicon glyphicon-circle-arrow-left"></span> Return to CDUS page</a>
									</div>   
								</c:otherwise>								
							</c:choose>
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
