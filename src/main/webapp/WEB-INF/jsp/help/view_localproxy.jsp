<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Manage Local Proxy - Help</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body>

	<header>
			
		<h2>Manage Local Proxy - Help</h2>	
			
	</header>

	<div class="container-fluid">
	
		<div class="row">
		
			<div class="col-md-12">
				
				<h3>Manage Local Proxy</h3>

				<p>The &quot;View Local Proxy&quot; function allows the display of the local proxy information for the selected court site.</p>
				
				<!-- <security:authorize access="hasRole('ROLE_ADMIN')"> -->
					<p>The &quot;Amend Local Proxy&quot; option allows update of the data stored against the selected sites local proxy.</p>
				
					<p>The &quot;Unregister Local Proxy&quot; option is available upon selection of a site allowing the unregistering of the local proxy for the selected court site.</p>
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
