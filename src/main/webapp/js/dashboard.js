/*
 * javascript functionality applicable to dashboard page(s)
 */

$(document).ready(function () { 
	
	console.log('dashboard.js script - starts');
	
	/* Page Initialization  Start*/
	
	/* Page Status Variables */
    var panelSuccess = "panel-success";
	var panelWarning = "panel-warning";
	var panelDanger = "panel-danger";	
	var successIcon = "glyphicon-ok-sign";
	var warningIcon = "glyphicon-exclamation-sign";
	var dangerIcon = "glyphicon-ban-circle";
	var successMessage = "ALL OK";
	var errorsMessage = "ERRORS";

	/* Page Url Variables */
	var dashboardUrl = "/dashboard/dashboard";
	var courtSiteUrl = "/dashboard/courtsite/";

	/* Page Automatic Reload */
	countdownTimer($('#countdown').text(), function(timeLeft) { $('#countdown').text(timeLeft); }, function() { reload(true); } );
	history.replaceState(null, null, dashboardUrl);

	/* Page Initialization End */

	/* Event Handlers */
	$('#overAllSummary').on('click', 'a[data-reload]', function (event)
	{
		console.log('Click event for reload the page');
		
		reload(false);
	});

	$('#allSites').on('click', 'a[data-fetch]', function (event)
	{
		console.log('Click event for court site fetch');

		var courtSite = $(this).attr('data-fetch');

		refreshCourtSite(this, courtSite, false);
	});

	$('#allSites').on('click', 'a[data-refresh]', function (event)
	{
		console.log('Click event for court site refresh');

		var courtSite = $(this).attr('data-refresh');

		refreshCourtSite(this, courtSite, true);
	});
		
	$('#allSites').on('click', 'a[data-search-1]', function (event)
	{
		console.log('Click event for search');
		
		var courtSite = $(this).attr('data-search-1');
		var macAddress = $(this).attr('data-search-2');
		
		search(courtSite, macAddress);
		return false;
	});

	/* Event Handlers End*/
	
	/* Page Specific Functions - Start */
	function countdownTimer(seconds, update, complete)
	{
		var time = seconds * 1000;
		var start = new Date().getTime();
		var interval = setInterval(function() {
			var timeLeft = time - (new Date().getTime() - start);
			if (timeLeft <= 0)
			{
				clearInterval(interval);
				complete();
			}
			else
			{
				update(Math.floor(timeLeft / 1000));
			}
		}, 200);
	}

	function reload(auto)
	{
		console.log('reload - starts');
		$('a[data-reload] span.glyphicon-refresh').addClass('glyphicon-refresh-animate');
		var url = dashboardUrl;
		if (auto)
		{
			url += "?reload=auto";
		}
		location.replace(url);
		console.log('reload - ends');
	}
	
	function refreshCourtSite(link, courtSite, refresh)
	{
		console.log('refreshCourtSite - starts');
		$(link).find('span.glyphicon-refresh').addClass('glyphicon-refresh-animate');
		var url = courtSiteUrl + courtSite;
		if (refresh)
		{
		  url += "?refresh=true";
		}
		$.getJSON(url)
			.done(function(json)
			{			    
				refreshCourtSiteSuccess(courtSite, json);
			})
			.fail(function(jqXHR, textStatus, error)
			{
			    refreshCourtSiteError(courtSite, textStatus, error);
			})
			.always(function()
			{
			  $(link).find('span.glyphicon-refresh').removeClass('glyphicon-refresh-animate');
			});
		
		console.log('refreshCourtSite - ends');
	}
	
	function updateCourtSiteStatus(courtSite, ragStatus) {
		$('#header'+courtSite).removeClass();
		$('#headerIcon'+courtSite).removeClass();
		var panelStyle = panelSuccess;
		var panelIcon = successIcon;
		var panelMessage = successMessage;
		if (ragStatus == 'R') {
		    panelStyle = panelDanger;
			panelIcon = dangerIcon;
			panelMessage = errorsMessage;
		} else if (ragStatus == 'A') {
		    panelStyle = panelWarning;
			panelIcon = warningIcon;
			panelMessage = errorsMessage;
		}
		$('#header'+courtSite).addClass ('panel '+panelStyle);
		$('#headerIcon'+courtSite).addClass ('glyphicon '+panelIcon);
		$('#headerMessage'+courtSite).text(panelMessage);
	}
	
	function refreshCourtSiteSuccess(courtSite, json)
	{
		console.log('refreshCourtSiteSuccess - starts');
		console.log(json);
		
		// Update the court site status
		updateCourtSiteStatus(courtSite, json.ragStatus);									
		
		// Court site panels to update
		var courtSiteDetail = $('#detail' + courtSite);
		var courtSiteBody = courtSiteDetail.find('.panel-body');
		var courtSiteFooter = courtSiteDetail.find('.panel-footer');
		
		// Create the new outer html for the local proxy and CDUs
		var div = $(document.createElement('div'));
		var outerUl = $(document.createElement('ul')).attr('class','list-unstyled m-bottom-0').appendTo(div);		
		var styleLine = $(document.createElement('li')).appendTo(outerUl);
		var innerUl = $(document.createElement('ul')).attr('type','none').attr('class','p-left-10').appendTo(styleLine);		
        
       	// Set the local proxy status variables
		var localProxyLineStatus = 'text-success';
		var localProxyStatusIcon = successIcon;
		if (json.localProxy.ragStatus == 'R') {
			localProxyLineStatus = 'text-danger';
			localProxyStatusIcon = dangerIcon;
		} else if (json.localProxy.ragStatus == 'A') {
			localProxyLineStatus = 'text-warning';
			localProxyStatusIcon = warningIcon;
		}	
		
		// Add the local proxy line 
		var localProxyLine = $(document.createElement('li')).attr('class',localProxyLineStatus).appendTo(innerUl);
		
		// Add the local proxy status icon 		    	    	
		var localProxyStatus = $(document.createElement('span')).attr('class','glyphicon '+ localProxyStatusIcon).appendTo(localProxyLine);

		// Add the text
		var localProxyOutput = $(document.createElement('a')).attr('href','#').appendTo(localProxyLine);
		localProxyOutput.attr('data-search-1',courtSite);
		localProxyOutput.text(" Local Proxy ");

		// Create the html for the CDUs
        $.each(json.cdus, function(index, cdu) {
        	// Set the cdu status variables
		    var cduLineStatus = 'text-success';
		    var cduStatusIcon = successIcon;
		    if (cdu.ragStatus == 'R') {
		    	cduLineStatus = 'text-danger';
		    	cduStatusIcon = dangerIcon;
		    } else if (cdu.ragStatus == 'A') {
		    	cduLineStatus = 'text-warning';
		    	cduStatusIcon = warningIcon;
		    }	
        	
        	// Add the cdu line 
		    var cduLine = $(document.createElement('li')).attr('class',cduLineStatus).appendTo(innerUl);
		    
			// Add cdu status icon 		    	    	
			var cduStatus = $(document.createElement('span')).attr('class','glyphicon '+ cduStatusIcon).appendTo(cduLine);

			// Add the text
			var cduOutput = $(document.createElement('a')).attr('href','#').appendTo(cduLine);
			cduOutput.attr('data-search-1',courtSite);
			cduOutput.attr('data-search-2',cdu.identifier);	
			var cduLabel = " " + cdu.cduNumber + " - " + cdu.location + " ";
			if (cdu.offlineIndicator == 'Y') {
				cduLabel += "(Known Offline)";
			}
			cduOutput.text(cduLabel);
			
			// Build the url list (for the tooltip)
			var urlSplit = "";
		    $.each(cdu.urls, function(index, value) {
			   urlSplit += "\n" + value.url; 
			});
			if (urlSplit == "") {
			   urlSplit = "None";
			}
			
			// Add the tooltip
		    cduOutput.attr('title','IP : '+ cdu.ipAddress + '\nMAC : '+ cdu.macAddress + '\nURLs : '+ urlSplit);			
		});	
        
		// Replace the html
		courtSiteBody.fadeOut('slow', function()
		{
			courtSiteBody.html(div.html());
			courtSiteBody.fadeIn('slow');
		});
		
		// Get the last refreshed date   		
		var lastRefreshedDate = new Date(json.lastRefreshDate);			
		
		// Update footer with refresh success and hide any previous error
		courtSiteFooter.find('span.label-danger').text('');		
		courtSiteFooter.find('span.label-info').text('Last successful refresh at ' + lastRefreshedDate.toLocaleString());
		
		console.log('refreshCourtSiteSuccess - ends');
	}
	
	function refreshCourtSiteError(courtSite, textStatus, error)
	{
		console.log('refreshCourtSiteError - starts');
		console.log(textStatus + ', ' + error);
		
		// Court site panel to update
		var courtSiteDetail = $('#detail' + courtSite);
		var courtSiteFooter = courtSiteDetail.find('.panel-footer');

		// Update footer with refresh error
		courtSiteFooter.find('span.label-danger').text('Failed to fetch status');

		console.log('refreshCourtSiteError - ends');
	}

	function search(courtSite, macAddress)
	{
		console.log('search - starts');
		
		$('#xhibitCourtSiteId').val(courtSite);
		$('#selectedMacAddress').val(macAddress);
		$('#cduSearchCommand').submit();		
		
		console.log('search - ends');
	}
	
	/* Page Specific Functions - End */
	
	console.log('dashboard.js script - ends');
});