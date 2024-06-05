/*
 * javascript functionality applicable to amend_courtel page
 * Author - A.Ghafouri
 * Date - May 2024
 */
$(document).ready(function () {

    /* Event Handlers */

    /* Handler to
     * Clear messages.
     */

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
