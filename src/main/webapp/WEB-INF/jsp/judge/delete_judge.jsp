<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Delete Judge</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body data-help-page="delete_judge">
    
    <div id="wrapper">

       
        <%@ include file="../common/sidebar.jsp"%>
        
		<%@ include file="../common/header.jsp"%>
	
        <!-- Page Content -->
        <div id="page-content-wrapper">
		
            <div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<h3>Delete Judge</h3>
						
						<c:if test="${not empty successMessage}" >
							<div class="alert alert-success">
								<p>${successMessage}</p>
							</div>
						</c:if>		
						
						<form:form commandName="judgeDeleteCommand" 
						           action="${context}/judge/delete_judge" 
						           method="POST" 
						           class="form-horizontal">

						<%-- CSRF Guard token where uri equals form action --%>
						<%--<input type="hidden" name="<csrf:tokenname/>" value="<csrf:tokenvalue uri="${context}/pdm/judge/delete_judge"/>"/>--%>
					
						<%-- 
							This error block must be within the form:form tags , otherwise you won't get any errors back !!
						--%>
						<spring:hasBindErrors name="judgeDeleteCommand">
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
							<%-- Full List Title --%>
							<spring:bind path="refJudgeId">
								<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.judgeDeleteCommand'].hasFieldErrors('refJudgeId') ? 'has-error' : ''}">
									<label for="refJudgeId">Select a Judge</label>	
									<%-- Use Spring form:select & spring:eval with form:option to encrypt id --%>
									<form:select path="refJudgeId" cssClass="form-control" id="selectJudge">
										<form:option value="" label="--- Please select a Judge ---" />
										<c:forEach var="judge" items="${judgeList}">
											<c:set var="refJudgeId"><spring:eval expression="judge.refJudgeId"/></c:set>
											<form:option value="${refJudgeId}" label="${judge.fullListTitle1}" />
										</c:forEach>
									</form:select>
									<spring:hasBindErrors name="judgeDeleteCommand">
										<div class="help-block" element="span">
											${errors.hasFieldErrors('refJudgeId') ? errors.getFieldError('refJudgeId').defaultMessage : ''}
										</div>
									</spring:hasBindErrors>
								</div>
							</spring:bind>

							<%-- Surname --%>
							<div class="col-md-12">
								<div><strong>Surname</strong></div><div id="surname" class="form-control"></div>
							</div>
							<%-- First Name --%>
							<div class="col-md-12">
								<div><strong>First Name</strong></div><div id="firstName" class="form-control"></div>
							</div>
							<%-- Middle Name --%>
							<div class="col-md-12">
								<div><strong>Middle Name</strong></div><div id="middleName" class="form-control"></div>
							</div>
							<%-- Title --%>
							<div class="col-md-12">
								<div><strong>Title</strong></div><div id="title" class="form-control"></div>
							</div>
							<%-- Full List Title --%>
							<div class="col-md-12">
								<div><strong>Full List Title</strong></div><div id="fullListTitle1" class="form-control"></div>
							</div>
							<%-- Judge Type --%>
							<div class="col-md-12">
								<div><strong>Judge Type :</strong></div><div id="judgeType" class="form-control"></div>
							</div>
						</div>

						<div class="form-group">
							<div class="col-md-12">																												
							    <button id="btnDeleteConfirm" class="btn btn-primary" name="btnDeleteConfirm"><span class="glyphicon glyphicon-edit"></span> Delete Judge</button>
								<a href="<c:url value="/judge/view_judge?reset=false"/>" class="btn btn-primary"><span class="glyphicon glyphicon-circle-arrow-left"></span> Return to Manage Judge</a>																	
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
	
	<script type="text/javascript" src="${context}/js/delete_judge.js"></script>   


</body>

</html>
