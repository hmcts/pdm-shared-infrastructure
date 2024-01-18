/*
 * javascript functionality applicable to view_display amend_display pages
 * Author - M.Harris
 * Date - December 2023
 */
$(document).ready(function () {

	/* Page Initialization */
	btnHandler();

	/* Page Initialization end */

	/* Event Handlers */

	/* Handler for #selectCourt dropdown
	 * Clear messages, disable/enable buttons.
	 */ 
	$('#selectCourt').change(function(e) {
		var elementId = $(this).prop('id');
		console.log(e.type + ' handler for element id : ' + elementId + " starts");
		clearMessages();
		btnHandler();
		console.log(e.type + ' handler for element id : ' + elementId + " completes");
	});

	$('#btnUpdate').click(function(e) {		
		var elementId = $(this).prop('id');
		console.log(e.type + ' handler for element id : ' + elementId + " starts");
		clearMessages();
		updateBtnHandler();
		console.log(e.type + ' handler for element id : ' + elementId + " completes");
		return false;
	});

	/* Event Handlers end*/

	/* Page Specific Functions - Start */
	function btnHandler() {

		/* Enable/Disable buttons as required */
		console.log('btnHandler starts...');

		/* View Page*/
		if ($('#selectCourt').size()) {
			$('#btnAdd').prop('disabled', true);
			$('#btnAmend').prop('disabled', true);
			$('#btnDelete').prop('disabled', true);

			// enable the buttons only if a court has been selected
			if ($('#selectCourt').find(':selected').size() && $('#selectCourt').find(':selected').val() != '') {
				$('#btnAdd').prop('disabled', false);
				$('#btnAmend').prop('disabled', false);
				$('#btnDelete').prop('disabled', false);
			}
		}		
		console.log('btnHandler ends');
	}

	function updateBtnHandler() {
		console.log('updateBtnHandler starts...');
		if ($('#originalNotification').val() != $('#notification').val())  {
			$('#notificationModal').modal('show');
		}
		else {
			$('#btnUpdateConfirm').click();	
		}		
		console.log('updateBtnHandler ends');		
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

});
