<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Amend Court</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body data-help-page="amend_court">

    <div id="wrapper">


        <%@ include file="../common/sidebar.jsp"%>

		<%@ include file="../common/header.jsp"%>

        <!-- Page Content -->
        <div id="page-content-wrapper">

            <div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<h3>Amend Court</h3>

						<c:if test="${not empty successMessage}" >
							<div class="alert alert-success">
								<p>${successMessage}</p>
							</div>
						</c:if>

						<form:form commandName="courtAmendCommand"
						           action="${context}/court/amend_court"
						           method="POST"
						           class="form-horizontal">

						<%-- CSRF Guard token where uri equals form action --%>
						<%--<input type="hidden" name="<csrf:tokenname/>" value="<csrf:tokenvalue uri="${context}/pdm/court/amend_court"/>"/>--%>

						<%--
							This error block must be within the form:form tags , otherwise you won't get any errors back !!
						--%>
						<spring:hasBindErrors name="courtAmendCommand">
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
                            <%-- Court Site --%>
                            <spring:bind path="xhibitCourtSiteId">
                                <div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.courtAmendCommand'].hasFieldErrors('xhibitCourtSiteId') ? 'has-error' : ''}">
                                    <label for="xhibitCourtSiteId">Court Site*</label>
                                    <%-- Use Spring form:select & spring:eval with form:option to encrypt id --%>
                                    <form:select path="xhibitCourtSiteId" cssClass="form-control" id="selectSite">
                                        <form:option value="" label="--- Please select a court site ---" />
                                        <c:forEach var="courtSite" items="${courtSiteList}">
                                            <c:set var="courtSiteId"><spring:eval expression="courtSite.id"/></c:set>
                                            <form:option value="${courtSiteId}" label="${courtSite.courtSiteName}" />
                                        </c:forEach>
                                    </form:select>
                                    <spring:hasBindErrors name="courtRoomSearchCommand">
                                        <div class="help-block" element="span">
                                            ${errors.hasFieldErrors('xhibitCourtSiteId') ? errors.getFieldError('xhibitCourtSiteId').defaultMessage : ''}
                                        </div>
                                    </spring:hasBindErrors>
                                </div>
                            </spring:bind>

							<%-- Court Site Name --%>
							<spring:bind path="courtSiteName">
								<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.courtAmendCommand'].hasFieldErrors('courtSiteName') ? 'has-error' : ''}">
									<label for="courtSiteName">Court Site Name*</label>
									<form:input placeholder=""
												path="courtSiteName"
												id="courtSiteName"
												style="text-transform: uppercase"
												maxlength="255"
												cssClass="form-control" />
									<spring:hasBindErrors name="courtAmendCommand">
										<div class="help-block" element="span">
											${errors.hasFieldErrors('courtSiteName') ? errors.getFieldError('courtSiteName').defaultMessage : ''}
										</div>
									</spring:hasBindErrors>
								</div>
							</spring:bind>

							<%-- Court Site Code --%>
							<spring:bind path="courtSiteCode">
								<div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.courtAmendCommand'].hasFieldErrors('courtSiteCode') ? 'has-error' : ''}">
									<label for="courtSiteCode">Court Site Code</label>
									<form:input placeholder=""
												path="courtSiteCode"
												id="courtSiteCode"
												style="text-transform: uppercase"
                        readonly="true"
												maxlength="50"
												cssClass="form-control" />
									<spring:hasBindErrors name="courtAmendCommand">
										<div class="help-block" element="span">
											${errors.hasFieldErrors('courtSiteCode') ? errors.getFieldError('courtSiteCode').defaultMessage : ''}
										</div>
									</spring:hasBindErrors>
								</div>
							</spring:bind>
						</div>

						<div class="form-group">
							<div class="col-md-12">
							    <button id="btnUpdateConfirm" class="btn btn-primary" name="btnUpdateConfirm"><span class="glyphicon glyphicon-edit"></span> Update Court</button>
								<a href="<c:url value="/court/view_court?reset=false"/>" class="btn btn-primary"><span class="glyphicon glyphicon-circle-arrow-left"></span> Return to Manage Court</a>
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

	<script type="text/javascript" src="${context}/js/amend_court.js"></script>


</body>

</html>
