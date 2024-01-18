/*
 * javascript functionality applicable to user page(s)
 * Author - M.Harris
 * Date - May 2017
 */

$(document).ready(function () { 
	
	console.log('users.js script - starts');
	
	/* Page Initialization Start */
	
	btnHandler();	
	
	/* Page Initialization End */
	
	/* Event Handlers Start */
	
	/* Handler for table
	 * Clear messages and highlight selected user.
	 */ 
	$('.clickable-row').click(function(e) {
		var elementId = $(this).prop('id');		
		console.log(e.type + ' handler for element id : ' + elementId + " starts");
		clearMessages();				
		if ($(this).hasClass("clicked-row")) {
			$('#userName').val('');
			$(this).removeClass('clicked-row');
		}
		else {
			$('#userName').val($(this).data('user'));
			$(this).addClass('clicked-row').siblings().removeClass('clicked-row');
		}	
		btnHandler();
		console.log(e.type + ' handler for element id : ' + elementId + " completes");
	});
		
	/* Handler for userName field
	 * Clear messages and selectUser drop down.
	 */ 
	$('#userName').on('input', function(e) {
		var elementId = $(this).prop('id');		
		console.log(e.type + ' handler for element id : ' + elementId + " starts");
		clearMessages();				
		console.log(e.type + ' handler for element id : ' + elementId + " completes");
	});
	
	$('#selectRole').change(function(e) {
		var elementId = $(this).prop('id');		
		console.log(e.type + ' handler for element id : ' + elementId + " starts");
		clearMessages();
		console.log(e.type + ' handler for element id : ' + elementId + " completes");
	});
	/* Event Handlers End */
	
	/* Page Specific Functions - Start */
	function btnHandler() {
		
		/* Enable/Disable buttons as required */
		console.log('btnHandler starts...');
		var selected = $('#users tr.clicked-row').size();
		$('#btnRemove').prop('disabled', !selected);
		
		console.log('btnHandler ends');
	}
	
	function clearMessages() {
		setMessage('#messages_success', '');
		setMessage('#messages_error', '');
	}
	
	function setSuccessMessage(msg) {
		setMessage('#messages_success', msg);
		setMessage('#messages_error', '');
	}
	
	function setErrorMessage(msg) {
		setMessage('#messages_success','');
		setMessage('#messages_error', msg);
	}
	
	function setMessage(msgDiv, msg) {
	    $(msgDiv).empty();
	    if (msg == '') {
			$(msgDiv).hide();
		} else {
			$(msgDiv).show();
			$(document.createElement('p')).appendTo($(msgDiv)).text(msg);
		}
	}
	/* Page Specific Functions - End */
	
	console.log('users.js script - ends');
});