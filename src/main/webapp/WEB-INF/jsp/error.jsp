<%@ include file="common/include.jsp"%>
<!DOCTYPE html>
<html xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
		<meta name="description" content="">
		<meta name="author" content="">
	
	    <title>Error Page</title>

		<%@ include file="common/stylesheets.jsp"%>
			
	</head>

	<body>
		
		<%@ include file="error/header.jsp"%>
		
        <div id="page-content-no-sidebar">
        
			<div class="container-fluid">
				
				<div class="row">
					<div class="col-md-12">				
					
						<div class="col-md-3">
							<!-- No content -->
						</div>
						
						<div class="col-md-6">
							<div class="panel panel-info">
								<div class="panel-heading">
									<h3 class="panel-title"><span class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;Error Code <c:out value="${requestScope['javax.servlet.error.status_code']}"/></h3>							
								</div>
								<div class="panel-body">
									<p>The application has encountered an error. Please contact support for assistance.</p>
								</div>					
							</div>
	
							<div class="btn-group">						
								<a href="<c:url value="/home"/>" class="btn btn-primary"><span class="glyphicon glyphicon-log-in"></span>&nbsp;Home</a>
							</div>
						</div>
					
					</div> <!-- end of col-md-12 -->				
				</div> <!-- end of row -->
			</div> <!-- end of container -->
		</div> <!-- end of content -->
		
		<%-- Output exception details in the page source as an HTML comment --%>
		<%--
		<!--
			Failed URL: <c:out value="${requestScope['javax.servlet.error.request_uri']}"/>
			<c:if test="${not empty requestScope['javax.servlet.error.exception']}">
			Exception: <c:out value="${requestScope['javax.servlet.error.exception'].class.canonicalName}"/>
			
				<c:out value="${requestScope['javax.servlet.error.exception'].message}"/>
			<c:forEach items="${requestScope['javax.servlet.error.exception'].stackTrace}" var="stackTraceElement">
				<c:out value="${stackTraceElement}"/>
			</c:forEach>
			</c:if>
		-->
		--%>
		
		<%@ include file="common/footer.jsp"%>
		
		<%@ include file="common/scripts.jsp"%>
		
	</body>
</html>
