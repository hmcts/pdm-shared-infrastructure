<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Manage CDUS - Help</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body>

	<header>
			
		<h2>Manage CDUS - Help</h2>	
			
	</header>

	<div class="container-fluid">
	
		<div class="row">
		
			<div class="col-md-12">
				
				<h3>Manage CDUS</h3> 
				
				<p>The &quot;Search CDUS&quot; function, allows you to find CDUs either by their court site or by their MAC Address:</p> 
				
				<ul>
					<li><p>To search by their court site, select a court site from the drop-down list and ensure the MAC Address input field is empty, then click the &quot;Search CDUS&quot; button. The results in the secondary drop-down list will show all CDUs for that court site including those that are unregistered.</p> 
				</ul>
				
				<security:authorize access="hasRole('ROLE_ADMIN')">
					<p style="margin-left: 50px"><strong>PLEASE NOTE:</strong> Registered CDUs will have the &quot;Unregister CDU&quot; button enabled. Unregistered CDUs will have the &quot;Register CDU&quot; button enabled.</p>
				</security:authorize>

				<ul>
					<li><p>To search by MAC Address, enter a full or a partial MAC Address in the provided input field and then click the &quot;Search CDUS&quot; button. The results in the secondary drop-down will show all registered CDUs with a matching MAC Address from any court site.</p>
				</ul>
				
				<security:authorize access="hasRole('ROLE_ADMIN')">
					<p>The &quot;Restart All CDUs&quot; function will send a restart command to all the CDUs for the selected court site.</p>				
				</security:authorize>
				
				<p>The &quot;Show CDU Information&quot; function will display the details of the selected MAC Address (CDU) in the drop-down list.</p>

				<ul>
					<li><p>The &quot;Show CDU Screenshot&quot; option opens a new tab displaying what is currently shown on the CDU screen.</p>
					 <p>Note: The first time of accessing this feature may require you to enable pop-ups for this site.</p></li>    
				</ul>
				
				<security:authorize access="hasRole('ROLE_ADMIN')">
					<p>The &quot;Register CDU&quot; option allows the registering of a new CDU. To register a CDU, search by the court site and select an unregistered MAC Address (CDU) in the drop-down list and then click the &quot;Register CDU&quot; button.</p>  

					<p>The &quot;Unregister CDU&quot; option allows the unregistering of a registered CDU. To unregister a CDU, search by court site or MAC Address and select a registered MAC Address (CDU) in the drop-down list and then click the &quot;Unregister CDU&quot; button. Then click the &quot;Confirm&quot; button from the dialog box.</p>   
				</security:authorize>
				
				<p>The &quot;Restart CDU&quot; function will send a restart command to the selected CDU.</p>
				
				<security:authorize access="hasRole('ROLE_ADMIN')">
					<p>The &quot;Amend CDU&quot; option allows update of data stored for the selected CDU.</p>
				
					<p>The &quot;Add URL Mapping&quot; function is to add the mapping of a specific URL to a registered CDU.</p>  
				
					<p>The &quot;Remove URL Mapping&quot; function is to remove the mapping of a specific URL from a registered CDU.</p>  
				</security:authorize>

			</div>
			<!-- /col-md-12 -->
		</div>
		<!-- /row -->
	</div>
	<!-- /container-fluid -->

	<%@ include file="../common/scripts.jsp"%>
     
</body>

</html>
