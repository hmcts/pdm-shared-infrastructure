<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>RAG Status - Help</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body>

	<header>
			
		<h2>RAG Status - Help</h2>	
			
	</header>

	<div class="container-fluid">
	
		<div class="row">
		
			<div class="col-md-12">
								
				<h3>RAG Status</h3>
				<br>
				<ul>
					<li><a href="#overallSummary">Overall Summary</a></li>
					<li><a href="#allSitesStatus">All Sites Status</a></li>
				</ul>	
				<br>
				
                <section id="overallSummary"></section>
				<h4>Overall Summary</h4>
				<p>The &quot;Overall Summary&quot; will either be ALL OK (green), ERRORS (amber) or ERRORS (red). This is based on the operational RAG Status and weighting of the registered CDUs of all the court sites. The &quot;Overall Summary&quot; is refreshed as part of the automatic page reload which happens every 3 minutes
				or when you click on the &quot;Refresh&quot; link. On the right-hand side of the &quot;Refresh&quot; link is a countdown in seconds to the next automatic page reload.</p>
				
				<p>Underneath the &quot;Overall Summary&quot; status is a list of court sites where the RAG status is not Green. This is a quick reference to see where there are possible problems. Those sites with a status of Red will be shown first, followed by court sites with an Amber RAG Status.</p>

				<p>The overall RAG status will be determined using the following rules:</p>
				<ul>
					<li><p>Calculate the total weighting of all operational CDUs in all court sites, i.e. only CDUs that have a Green RAG status.</p> 
					<li><p>Calculate the total weighting of all CDUs in all court sites.</p> 
					<li><p>Calculate the weighting percentage of the operational CDUs compared to the total CDUs.</p> 
					<li><p>Compare the calculated weighting percentage against the thresholds for the Amber (<spring:eval expression="@applicationConfiguration.ragStatusOverallAmberPercent"/>%) and Red (<spring:eval expression="@applicationConfiguration.ragStatusOverallRedPercent"/>%) status to provide the overall RAG status.</p>
				</ul>
				<p style="margin-left: 50px">(N.B. CDUs which are known to be offline are not used in the overall RAG status calculation)</p>
				<p><strong><em>Please Note:</em></strong> The &quot;Overall Summary&quot; status can mean court sites with many CDUs have greater importance over court sites that have a smaller amount of CDUs.</p>
				<br>
				
				<section id="allSitesStatus"></section>
				<h4>All Sites Status</h4>
				<p>The lower part of the page is a list of all of the active court sites. All court sites within this list will be shown as Red, Amber or Green depending on the availability of the CDUs at that court site. However, if the court site cannot be contacted, then the RAG status of all the CDUs,
				the Local Proxy and the court site will always be Red.</p>

				<p>If the court site can be contacted, then the percentage of CDUs at the court site which have a Green RAG status, is compared against the thresholds for the Amber (<spring:eval expression="@applicationConfiguration.ragStatusCourtSiteAmberPercent"/>%) and Red (<spring:eval expression="@applicationConfiguration.ragStatusCourtSiteRedPercent"/>%) status to provide the court site RAG status.</p>
				
				<p>Each court site within the &quot;All Sites Status&quot; list is a clickable item. By clicking on the name of a listed court site or the plus sign, the court site section will expand and display the Local Proxy and the list of registered CDUs.</p>  

				<p>The Local Proxy and each CDU displayed in the list is also a clickable item. By clicking on the Local Proxy, the application will navigate to the &quot;View/Unregister Proxies&quot; page with the court site selected. By clicking on a CDU, the application will navigate to the &quot;Manage CDUS&quot;
				page with the court site and CDU selected.</p>
				
				<p>By hovering over a listed CDU a small dialog box will display the following details:</p>  
				
				<ol>
					<li><p>IP Address</p> 
					<li><p>MAC address</p>
					<li><p>URL Mapping (if applicable)</p>
				</ol>
				
				<p>Also, below the list of the Local Proxy and the registered CDUs is the &quot;Last successful refresh&quot; date and time. This is the date and time when the RAG status was last retrieved from the court site. By clicking the &quot;Refresh&quot; link a more recent reading will be retrieved from the court site.</p>  
				
				<p>Each Local Proxy and CDU shown in the list will have their RAG status displayed by the colour of their text and a small visual icon.</p>
				<br>

			</div>
			<!-- /col-md-12 -->
		</div>
		<!-- /row -->
	</div>
	<!-- /container-fluid -->

	<%@ include file="../common/scripts.jsp"%>
     
</body>

</html>
