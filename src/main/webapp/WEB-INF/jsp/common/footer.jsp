<!-- Start of Standard footer for XHIBIT Public display manager -->
<footer class="footer">			
	<div class="container-fluid">		
		<div class="row">				
			<div class="col-xs-6 col-md-3">
				<strong><span class="glyphicon glyphicon-phone-alt"></span> Support Contact Number</strong>
			</div>					
			<div class="col-xs-6 col-md-9">
				<span>0800 021 7233 DOESTHISAPPEAR?</span>
			</div>					
		</div>					
				
		<div class="row">				
			<div class="col-xs-6 col-md-3">				
				<strong><span class="glyphicon glyphicon-envelope"></span> Support Contact Email</strong>
			</div>					
			<div class="col-xs-6 col-md-9">
				<span><a href="mailto:itservicedesk@justice.gsi.gov.uk">IT Service Desk (Atos Origin)</a></span>
			</div>					
		</div>					

		<div class="row">				
			<div class="col-xs-6 col-md-3">
				<strong><span class="glyphicon glyphicon-cog"></span> Application Version</strong>
			</div>
			<div class="col-xs-6 col-md-9">					
				<span><%@ include file="build_version.jsp"%></span>
			</div>
		</div>					
				
		<div class="row">				
			<div class="col-xs-6 col-md-3">
				<strong><span class="glyphicon glyphicon-calendar"></span> Current Date</strong>
			</div>
			<div class="col-xs-6 col-md-9">
				<jsp:useBean id="now" class="java.util.Date"/>
				<span><fmt:formatDate type="date" dateStyle="long" value="${now}"/></span>
			</div>			
		</div>					
	</div>
</footer>
<!-- End of Standard footer for XHIBIT Public display manager -->
