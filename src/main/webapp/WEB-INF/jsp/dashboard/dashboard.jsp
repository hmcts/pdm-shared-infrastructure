<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Dashboard - Public Display Manager</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>


<body data-help-page="dashboard">

	<!-- Page Status Variables -->
	<c:set var="panelSuccess" value="panel-success"/>
	<c:set var="panelWarning" value="panel-warning"/>
	<c:set var="panelDanger" value="panel-danger"/>
	<c:set var="successIcon" value="glyphicon-ok-sign"/>
	<c:set var="warningIcon" value="glyphicon-exclamation-sign"/>
	<c:set var="dangerIcon" value="glyphicon-ban-circle"/>
	<c:set var="successMessage" value="ALL OK"/>
	<c:set var="errorsMessage" value="ERRORS"/>

    <div id="wrapper">

        <%@ include file="../common/sidebar.jsp"%>

		<%@ include file="../common/header.jsp"%>

        <!-- Page Content -->
        <div id="page-content-wrapper">

            <div class="container-fluid">
                <div class="row">
                    <div id="overAllSummary" class="col-md-12 p-0">
                        <c:set var="overAllStatusCode" value="${overAllStatus}"/>
						<c:choose>
							<c:when test="${overAllStatusCode=='R'}" >
								<c:set var="overAllStatusDesc" value="${errorsMessage}"/>
								<c:set var="overAllStatusIcon" value="${dangerIcon}"/>
								<c:set var="overAllStatusAlert" value="alert-danger"/>
							</c:when>
							<c:when test="${overAllStatusCode=='A'}" >
								<c:set var="overAllStatusDesc" value="${errorsMessage}"/>
								<c:set var="overAllStatusIcon" value="${warningIcon}"/>
								<c:set var="overAllStatusAlert" value="alert-warning"/>
							</c:when>
							<c:otherwise>
								<c:set var="overAllStatusDesc" value="${successMessage}"/>
								<c:set var="overAllStatusIcon" value="${successIcon}"/>
								<c:set var="overAllStatusAlert" value="alert-success"/>
							</c:otherwise>
						</c:choose>

						<c:set var="overAllStatusTooltipText" value="${noOfCourtSiteListRedStatus} Court sites Red, ${noOfCourtSiteListAmberStatus} Court sites Amber, ${noOfCourtSiteListGreenStatus} Court sites Green"/>
						<div class="alert ${overAllStatusAlert}">
							<div class="row">
								<div class="col-md-9">
									<div class="pull-left">
										<h2 class="m-top-0"><span data-toggle="tooltip" title="${overAllStatusTooltipText}"><span class="glyphicon ${overAllStatusIcon}"></span> Overall Summary : ${overAllStatusDesc}</span></h2>
									</div>
								</div>
								<div class="col-md-3">
									<div class="pull-right">
										<a data-reload="#" href="#"><span class="glyphicon glyphicon-refresh"></span> Refresh in <span id="countdown">${countdown}</span>s</a>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="pull-left">
										<c:choose>
											<c:when test="${not empty courtSiteListRedStatus ||
															not empty courtSiteListAmberStatus}">
												<h4 class="m-top-5 m-bottom-5">Sites Not OK : </h4>
												<ul class="list-unstyled">
													<c:forEach var="courtSite" items="${courtSiteListRedStatus}">
														<li><span class="text-danger"><span class="glyphicon ${dangerIcon}"></span><c:out value=" ${courtSite.courtSiteName}"/></span></li>
													</c:forEach>
													<c:forEach var="courtSite" items="${courtSiteListAmberStatus}">
														<li><span class="text-warning"><span class="glyphicon ${warningIcon}"></span><c:out value=" ${courtSite.courtSiteName}"/></span></li>
													</c:forEach>
												</ul>
											</c:when>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
                    </div>
				</div>

				<form:form id="cduSearchCommand" action="${context}/dashboard/search" method="post">
					<input type="hidden" id="xhibitCourtSiteId" name="xhibitCourtSiteId" />
					<input type="hidden" id="selectedMacAddress" name="selectedMacAddress" />
				</form:form>

				<div class="row">
					<!-- Add in our collapsible panel group to show the site statuses -->
					<div id="allSites" class="col-md-12 p-0">
						<div class="panel-group" id="accordion">
							<div class="panel panel-info">
								<div class="panel-heading">
									<h4 class="panel-title clearfix">
										<a data-toggle="collapse" data-parent="#accordion" href="#collapseStatus"><span class="glyphicon glyphicon-info-sign"></span> All Sites Status</a>
									</h4>
								</div> <!-- end of panel heading 1 -->

								<div id="collapseStatus" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="panel-group" id="accordionNested">

									    <c:choose>
											<c:when test="${not empty courtSiteList}">

												<!-- Start of panel loop-->
												<c:forEach var="courtSite" items="${courtSiteList}">
													<c:choose>
										    			<c:when test="${courtSite.ragStatus=='R'}" >
										    				<c:set var="panelStyle" value="${panelDanger}"/>
										    				<c:set var="panelStatusIcon" value="${dangerIcon}"/>
										    				<c:set var="panelStatusMessage" value="${errorsMessage}"/>
														</c:when>
														<c:when test="${courtSite.ragStatus=='A'}" >
										    				<c:set var="panelStyle" value="${panelWarning}"/>
										    				<c:set var="panelStatusIcon" value="${warningIcon}"/>
										    				<c:set var="panelStatusMessage" value="${errorsMessage}"/>
														</c:when>
														<c:otherwise>
										    				<c:set var="panelStyle" value="${panelSuccess}"/>
										    				<c:set var="panelStatusIcon" value="${successIcon}"/>
										    				<c:set var="panelStatusMessage" value="${successMessage}"/>
														</c:otherwise>
													</c:choose>

												    <c:set var="courtSiteId"><spring:eval expression="courtSite.id"/></c:set>

											   		<div id="header<c:out value="${courtSiteId}"/>" class="panel ${panelStyle}">
														<div class="panel-heading">
															<h4 class="panel-title clearfix">
																<a class="collapsed" data-toggle="collapse" data-parent="#accordionNested" data-fetch="${courtSiteId}" href="#detail<c:out value="${courtSiteId}"/>"><span id="headerIcon<c:out value="${courtSiteId}"/>" class="glyphicon ${panelStatusIcon}"></span><c:out value=" ${courtSite.courtSiteName}"/></a> - <strong id="headerMessage<c:out value="${courtSiteId}"/>"><c:out value="${panelStatusMessage}"/></strong>
															</h4>
														</div> <!-- end of panel heading -->

														<div id="detail<c:out value="${courtSiteId}"/>" class="panel-collapse collapse">
															<div class="panel-body">
													    		<!-- dynamic cdu list is created here --
																<ul class="list-unstyled m-bottom-0">
																	<li>
																		<ul type="none" class="p-left-10">
																			<li class="text-success">
																				<span class="glyphicon glyphicon-ok-sign"></span>
																				<a href="#" data-search-1="courtSite"> Local Proxy </a>
																			</li>
																			<li class="text-success">
																				<span class="glyphicon glyphicon-ok-sign"></span>
																				<a href="#" data-search-1="courtSite" data-search-2="cdu" title="IP : 192.168.1.100 MAC : 77:50:56:c2:21:af URLs : None"> CDU0077 - Court Site Reception </a>
																			</li>
																		</ul>
																	</li>
																</ul>
																--end of dynamic cdu list -->
															</div> <!-- end of panel body -->
															<div class="panel-footer">
																<div class="pull-left">
																	<!-- status labels for ajax refresh -->
																	<span class="label label-info"></span>
																	<span class="label label-danger"></span>
																</div>
																<div class="pull-right">
																	<a data-refresh="${courtSiteId}" href="#header<c:out value="${courtSiteId}"/>"><span class="glyphicon glyphicon-refresh"></span> Refresh</a>
																</div>
																<div class="clearfix"></div>
															</div> <!-- end of panel footer -->
														</div> <!-- end of panel collapse -->
								            		</div> <!-- end of court site -->
								     			</c:forEach>
											</c:when>
											<c:otherwise>
												<div class="panel-heading">
													<h4 class="panel-title">
														<strong>No registered local proxy</strong>
													</h4>
												</div>
											</c:otherwise>
										</c:choose>
										</div> <!-- end of panel group -->
									</div><!-- end of panel body -->
								</div><!-- end of panel collapse -->
							</div> <!-- end of panel -->
						</div> <!-- end of panel group -->
					</div> <!-- end of col div -->
                </div> <!-- end of row div -->
            </div> <!-- end of container div -->
        </div> <!-- /#page-content-wrapper -->
		<%@ include file="../common/footer.jsp"%>

    </div> <!-- /#wrapper -->

	<%@ include file="../common/scripts.jsp"%>

	<%-- Dashboard specific javascript files --%>
	<script type="text/javascript" src="${context}/js/dashboard.js"></script>

</body>

</html>
