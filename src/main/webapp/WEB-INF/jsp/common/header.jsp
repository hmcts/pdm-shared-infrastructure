<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!-- Start of Standard Header for XHIBIT Public display manager -->
<header>
	<div class="row">
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-6">
					<div class="pull-left">
						<h2 class="m-top-10">XHIBIT Public Display Manager</h2>											
					</div>
				</div>
				<div class="col-md-6">
					<div class="pull-right">
						<p class="pull-right m-top-20">You are logged in as <security:authentication property="name"/></p>
					</div>
				</div>
			</div>
			<div class="row m-bottom-10">			
				<div class="col-md-12">
					<div class="btn-group pull-left">
						<a href="#menu-toggle" class="btn btn-primary" id="menu-toggle"><span class="glyphicon glyphicon-menu-hamburger"></span>&nbsp;&nbsp;Toggle Menu</a>
					</div>				
					<div class="btn-group pull-right">
						<a href="<c:url value="/help/"/>" target="pdm_help" class="btn btn-primary" id="lnkHelp"><span class="glyphicon glyphicon-question-sign"></span>&nbsp;Help</a>
						<a href="<c:url value="/logout"/>" class="btn btn-primary" id="lnkLogout"><span class="glyphicon glyphicon-log-out"></span>&nbsp;Logout</a>
					</div>					
				</div>
			</div>
		</div>
	</div>
</header>
<!-- End of Standard Header for XHIBIT Public display manager -->
