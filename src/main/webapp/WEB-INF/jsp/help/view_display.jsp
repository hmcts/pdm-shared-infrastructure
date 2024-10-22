<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Manage Display - Help</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body>

	<header>
			
		<h2>Manage Display - Help</h2>	
			
	</header>

	<div class="container-fluid">
	
		<div class="row">
		
			<div class="col-md-12">
				
				<h3>Manage Display</h3>

				<!-- <security:authorize access="hasRole('ROLE_ADMIN')">  -->
					<p>The &quot;Amend Display&quot; option allows update of the data stored against the selected sites display.</p>
				
					<p>The &quot;Create Display&quot; option allows creation of a new display stored against the selected site.</p>
				
					<p>The &quot;Delete Display&quot; option allows deletion of a display stored against the selected site.</p>
				<!-- </security:authorize> -->
			</div>
			<!-- /col-md-12 -->
		</div>
		<!-- /row -->
	</div>
	<!-- /container-fluid -->

	<%@ include file="../common/scripts.jsp"%>
     
</body>

</html>
