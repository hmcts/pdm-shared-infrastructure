<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html xml:lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
		<meta name="description" content="">
		<meta name="author" content="">
	
	    <title>Login Page</title>

		<%@ include file="../common/stylesheets.jsp"%>
			
	</head>

	<body data-help-page="login">
		
		<%@ include file="header.jsp"%>
		
        <div id="page-content-no-sidebar">

			<div class="container-fluid">
		
				<div class="row">
					<div class="col-md-12">				
			
						<c:url var="loginUrl" value="/login" />
						<form:form action="${loginUrl}" method="post" class="form-horizontal">
	
							<!--<input type="hidden" name="j_character_encoding" value="UTF-8"/>-->
					
							<c:if test="${not empty requestScope.error}">
								<div class="form-group">
									<div class="col-md-6 col-md-offset-2">
										<div class="alert alert-danger">
											<p><span class="glyphicon glyphicon-warning-sign"></span> Invalid username and password</p>
										</div>
									</div>
								</div>
							</c:if>
	
							<div class="form-group">
								<label for="inputUserName" class="control-label col-md-2">Username</label>						
								<div class="col-md-6">
									<input class="form-control" type="text" placeholder="User Name" name="username"/>
								</div>
							</div>
						
							<div class="form-group">
								<label for="inputPassword" class="control-label col-md-2">Password</label>						
								<div class="col-md-6">
									<input class="form-control" type="password" placeholder="Password" name="password" autocomplete="new-password"/>
								</div>
							</div>
						
							<div class="form-group">						
								<div class="col-md-offset-2 col-md-10">
									<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-log-in"></span>&nbsp;Login</button>
								</div>
								<div class="col-md-offset-2 col-md-10">	
									<a href="<c:url value="/dashboard/dashboard"/>" target="pdm_help" class="btn btn-primary" id="lnkHelp"><span class="glyphicon glyphicon-log-in"></span>&nbsp;Bypass Login</a>
								</div>
							</div>
						</form:form>
					
					</div> <!-- end of col-md-12 -->
				</div> <!-- end of row -->
			</div> <!-- end of container -->
		</div> <!-- end of content -->

		<%@ include file="../common/footer.jsp"%>
		
		<%@ include file="../common/scripts.jsp"%>
		
	</body>
</html>
