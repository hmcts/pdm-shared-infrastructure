<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Manage Users</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body data-help-page="users">

    <div id="wrapper">

        <%@ include file="../common/sidebar.jsp"%>

		<%@ include file="../common/header.jsp"%>

        <!-- Page Content -->
        <div id="page-content-wrapper">

            <div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<h3>Manage Users</h3>
						
						<%-- messages_success is always present so the JS can insert its own messages --%>
						<div id="messages_success" class="alert alert-success" style="${empty successMessage ? 'display:none;' : ''}">
							<c:if test="${not empty successMessage}" >
								<p>${successMessage}</p>
							</c:if>
						</div>

						<%-- messages_error is always present so the JS can insert its own messages --%>
						<spring:hasBindErrors name="userRemoveCommand">
							<c:forEach items="${errors.globalErrors}" var="errorMessage">
								<c:if test="${not empty errorMessage.defaultMessage}">
									<div class="alert alert-danger" id="errors">
										<c:out value="${errorMessage.defaultMessage}" />
									</div>
								</c:if>
							</c:forEach>
						</spring:hasBindErrors>
						<spring:hasBindErrors name="userAddCommand">
							<c:forEach items="${errors.globalErrors}" var="errorMessage">
								<c:if test="${not empty errorMessage.defaultMessage}">
									<div class="alert alert-danger" id="errors">
										<c:out value="${errorMessage.defaultMessage}" />
									</div>
								</c:if>
							</c:forEach>
						</spring:hasBindErrors>	
												
						<div class="row">
							<div class="col-md-6">								

								<form:form 	commandName="userRemoveCommand" 
											action="${context}/users/users" 
											method="POST" 
											cssClass="form-horizontal">
													
									<%-- CSRF Guard token where uri equals form action --%>
									<%--<input type="hidden" name="<csrf:tokenname/>" value="<csrf:tokenvalue uri="${context}/users/users"/>"/>--%>												
									
									<div class="panel panel-primary">
										<div class="panel-heading"><h3 class="panel-title">Existing Users</h3></div>
										<div class="panel-body">
											<c:choose>
												<c:when test="${not empty userList}">												
													<div class="scrollable-table-header">
														<table aria-describedby="Users" class="table table-bordered table-condensed">
															<thead>
																<tr>
																	<th style="width:58%">User Name</th>
																	<th style="width:42%">User Role</th>
																</tr>
															</thead>												
														</table>
													</div>
													<div class="scrollable-table-body">
														<table aria-describedby="UserList" id="users" class="table table-bordered table-condensed">
															<th>
															<tbody>
																<%-- Use spring:eval to encrypt user name --%>													
																<c:forEach var="user" items="${userList}">
																	<c:set var="userIdentifier"><spring:eval expression="user.identifier"/></c:set>
																	<tr data-user="<c:out value="${userIdentifier}"/>" class="clickable-row">													
																		<td style="width:60%"><c:out value="${user.userName}"/></td>
																		<td style="width:40%"><c:out value="${user.userRole.description}"/></td>
																	</tr>
																</c:forEach>															
															</tbody>		
														</table>
													</div>
													<spring:hasBindErrors name="userRemoveCommand">
														<div class="help-block" element="span">
															${errors.hasFieldErrors('userName') ? errors.getFieldError('userName').defaultMessage : ''}
														</div>
													</spring:hasBindErrors>
												</c:when>
												<c:otherwise>
													<%-- No user data found, so just render a simple warning block --%>
													<div class="alert alert-info">
														<p>No users present.</p>
													</div>													
												</c:otherwise>										
											</c:choose>
											
											<input type="hidden" id="userName" name="userName" />								
											
											<div>
												<button type="button" id="btnRemove" class="btn btn-primary" name="btnRemove" value="btnRemove" data-toggle="modal" data-target="#removeModal">
													<span class="glyphicon glyphicon-minus"></span> Remove User</button>
											</div>		
										</div>
									</div>
									<!-- Modal dialog for remove user. -->
									<div id="removeModal" class="modal fade" role="dialog">
										<div class="modal-dialog">
											<div class="modal-content">
	
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal">&times;</button>
													<h4 class="modal-title">Remove User</h4>
												</div>
	
												<div class="modal-body">
													<p>You are about to remove the selected User. Do you wish to continue?</p>
												</div>
	
												<div class="modal-footer">
													<button class="btn btn-primary" id="btnRemoveConfirm" name="btnRemoveConfirm">Confirm</button>
													<button class="btn btn-default" data-dismiss="modal">Cancel</button>
												</div>
												
											</div>
										</div>
									</div><!-- end of removeModal -->
								</form:form>
							</div>																				
							<div class="col-md-6">			
								<form:form 	commandName="userAddCommand" 
											action="${context}/users/users" 
											method="POST" 
											cssClass="form-horizontal">
														
									<%-- CSRF Guard token where uri equals form action --%>
									<%--<input type="hidden" name="<csrf:tokenname/>" value="<csrf:tokenvalue uri="${context}/users/users"/>"/>--%>
								
									<div class="panel panel-primary">
										<div class="panel-heading"><h3 class="panel-title">New User</h3></div>
										<div class="panel-body">
											<div class="col-md-12">	
												<div class="form-group">
													<spring:bind path="userName">
														<div class="${requestScope['org.springframework.validation.BindingResult.userAddCommand'].hasFieldErrors('userName') ? 'has-error' : ''}">
															<label for="userName">User Name *</label>	
															<form:input placeholder="User Name"
																		path="userName" 
																		id="userName"
																		maxlength="30"
																		cssClass="form-control"/>
															<spring:hasBindErrors name="userAddCommand">
																<div class="help-block" element="span">
																	${errors.hasFieldErrors('userName') ? errors.getFieldError('userName').defaultMessage : ''}
																</div>
															</spring:hasBindErrors>
														</div>
													</spring:bind>
												</div>	
												
												<div class="form-group">
													<spring:bind path="userRole">
														<div class="${requestScope['org.springframework.validation.BindingResult.userAddCommand'].hasFieldErrors('userRole') ? 'has-error' : ''}">
															<label for="userRole">User Role *</label>	
															<form:select path="userRole" cssClass="form-control" id="selectRole">
																<form:option value="" label="--- Please select a Role ---" />
																<c:forEach var="role" items="${roleList}">
																	<form:option value="${role}" label="${role.description}" />
																</c:forEach>
															</form:select>
															<spring:hasBindErrors name="userAddCommand">
																<div class="help-block" element="span">
																	${errors.hasFieldErrors('userRole') ? errors.getFieldError('userRole').defaultMessage : ''}
																</div>
															</spring:hasBindErrors>
														</div>
													</spring:bind>
												</div>										
											
												<div class="form-group">
													<button id="btnAdd" class="btn btn-primary" name="btnAdd" value="btnAdd">
														<span class="glyphicon glyphicon-plus"></span> Add User</button>
												</div>
											</div>							
										</div>
									</div>
								</form:form>
							</div>							
						</div>												
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

	<%-- Users Specific javascript files --%>
	<script type="text/javascript" src="${context}/js/users.js"></script>

</body>

</html>
