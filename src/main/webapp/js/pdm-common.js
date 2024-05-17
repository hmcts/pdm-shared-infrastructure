/*
 * Common javascript functionality applicable to multiple pages
 */
$(document).ready(function () {

	// Append page to help link from data-help-page attribute on body
	if ($("#lnkHelp").length) {
		var helpLink = $('#lnkHelp').prop('href');
		var helpPage = $('body').data('help-page');	
		$('#lnkHelp').prop('href', helpLink + helpPage);
	}

	// Toggle menu wrapper on button click
	if ($("#menu-toggle").length) {
		$("#menu-toggle").click(function(e) {
	        e.preventDefault();
	        $("#wrapper").toggleClass("toggled");
	    });
	}
});

/*
 * Global ajax error handler to catch session issues
 */
$(document).ajaxError(function (event, jqXHR, settings, error) {
	if (jqXHR.status == 401) {
		location.replace('/' + jqXHR.getResponseHeader('X-Logout-URL'));
	}
});
