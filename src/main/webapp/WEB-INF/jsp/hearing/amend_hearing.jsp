<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Amend Hearing Type</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body data-help-page="amend_hearing">

    <div id="wrapper">


        <%@ include file="../common/sidebar.jsp"%>

		<%@ include file="../common/header.jsp"%>

        <!-- Page Content -->
        <div id="page-content-wrapper">

            <div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<h3>Amend Hearing Type</h3>

						<c:if test="${not empty successMessage}" >
							<div class="alert alert-success">
								<p>${successMessage}</p>
							</div>
						</c:if>

						<form:form commandName="hearingTypeAmendCommand"
						           action="${context}/hearing/amend_hearing"
						           method="POST"
						           class="form-horizontal">

						<%-- CSRF Guard token where uri equals form action --%>
						<%--<input type="hidden" name="<csrf:tokenname/>" value="<csrf:tokenvalue uri="${context}/pdm/hearing/amend_hearing"/>"/>--%>

						<%--
							This error block must be within the form:form tags , otherwise you won't get any errors back !!
						--%>
						<spring:hasBindErrors name="hearingTypeAmendCommand">
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
							<%-- Hearing Types --%>
							<spring:bind path="refHearingTypeId">
								<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.hearingTypeAmendCommand'].hasFieldErrors('refHearingTypeId') ? 'has-error' : ''}">
									<label for="refHearingTypeId">Code *</label>
									<%-- Use Spring form:select & spring:eval with form:option to encrypt id --%>
									<form:select path="refHearingTypeId" cssClass="form-control" id="selectHearingType">
										<form:option value="" label="--- Please select a hearing type ---" />
										<c:forEach var="refHearingType" items="${hearingTypeList}">
											<c:set var="refHearingTypeId"><spring:eval expression="refHearingType.refHearingTypeId"/></c:set>
											<form:option value="${refHearingTypeId}" label="${refHearingType.hearingTypeCode}" />
										</c:forEach>
									</form:select>
									<spring:hasBindErrors name="hearingTypeAmendCommand">
										<div class="help-block" element="span">
											${errors.hasFieldErrors('refHearingTypeId') ? errors.getFieldError('refHearingTypeId').defaultMessage : ''}
										</div>
									</spring:hasBindErrors>
								</div>
							</spring:bind>

							<%-- Description --%>
							<spring:bind path="hearingTypeDesc">
								<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.hearingTypeAmendCommand'].hasFieldErrors('hearingTypeDesc') ? 'has-error' : ''}">
									<label for="hearingTypeDesc">Description *</label>
									<form:input placeholder="Description"
												path="hearingTypeDesc"
												id="hearingTypeDesc"
												maxlength="72"
												cssClass="form-control"/>
									<spring:hasBindErrors name="hearingTypeAmendCommand">
										<div class="help-block" element="span">
											${errors.hasFieldErrors('hearingTypeDesc') ? errors.getFieldError('hearingTypeDesc').defaultMessage : ''}
										</div>
									</spring:hasBindErrors>
								</div>
							</spring:bind>

							<%-- Category --%>
							<spring:bind path="category">
								<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.hearingTypeAmendCommand'].hasFieldErrors('category') ? 'has-error' : ''}">
									<label for="category">Category *</label>
									<%-- Use Spring form:select & spring:eval with form:option to encrypt id --%>
									<form:select path="category" cssClass="form-control" id="category">
										<form:option value="" label="--- Please select a Category ---" />
										<c:forEach var="category" items="${categoriesList}">
											<c:set var="category"><spring:eval expression="category"/></c:set>
											<form:option value="${category}" label="${category}" />
										</c:forEach>
									</form:select>
									<spring:hasBindErrors name="hearingTypeAmendCommand">
										<div class="help-block" element="span">
											${errors.hasFieldErrors('category') ? errors.getFieldError('category').defaultMessage : ''}
										</div>
									</spring:hasBindErrors>
								</div>
							</spring:bind>
						</div>

						<div class="form-group">
							<div class="col-md-12">
							    <button id="btnUpdateConfirm" class="btn btn-primary" name="btnUpdateConfirm"><span class="glyphicon glyphicon-edit"></span> Update Hearing Type</button>
								<a href="<c:url value="/hearing/view_hearing?reset=false"/>" class="btn btn-primary"><span class="glyphicon glyphicon-circle-arrow-left"></span> Return to Manage Hearing Type</a>
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

	<script type="text/javascript" src="${context}/js/amend_hearing.js"></script>


</body>

</html>
