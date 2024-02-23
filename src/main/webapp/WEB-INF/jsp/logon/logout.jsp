<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
		<meta name="description" content="">
		<meta name="author" content="">
	
	    <title>Logout Page</title>
		
		<%@ include file="../common/stylesheets.jsp"%>
			
	</head>

	<body data-help-page="logout">	
	
		<%@ include file="header.jsp"%>
		
        <div id="page-content-no-sidebar">
		
			<div class="container-fluid">
				
				<div class="row">
					<div class="col-md-12">				
					
						<div class="col-md-3">
							<!-- No content -->
						</div>
						
						<div class="col-md-6">
							<c:choose>
								<c:when test="${empty requestScope.error}">
									<div class="panel panel-success">
										<div class="panel-heading">
											<h3 class="panel-title"><span class="glyphicon glyphicon-ok"></span>&nbsp;Logout Status</h3>							
										</div>
										<div class="panel-body">
											<p>You have logged out successfully.</p>
										</div>					
									</div>
								</c:when>
								<c:when test="${requestScope.error eq 'invalidToken'}">
									<div class="panel panel-info">
										<div class="panel-heading">
											<h3 class="panel-title"><span class="glyphicon glyphicon-warning-sign"></span>&nbsp;Invalid session</h3>							
										</div>
										<div class="panel-body">
											<p>Your session is invalid, please login again.</p>
										</div>					
									</div>
								</c:when>
								<c:otherwise>
									<div class="panel panel-info">
										<div class="panel-heading">
											<h3 class="panel-title"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;Inactive session</h3>							
										</div>
										<div class="panel-body">
											<p>Your session has become inactive, please login again.</p>
										</div>					
									</div>
								</c:otherwise>
							</c:choose>
	
							<div class="btn-group">						
								<a href="<c:url value="/login"/>" class="btn btn-primary"><span class="glyphicon glyphicon-log-in"></span>&nbsp;Login</a>
							</div>
						</div>
					
					</div> <!-- end of col-md-12 -->				
				</div> <!-- end of row -->
			</div> <!-- end of container -->
		</div> <!-- end of content -->

		<%@ include file="../common/footer.jsp"%>
		
		<%@ include file="../common/scripts.jsp"%>
			
	</body>
</html>
