<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Amend Courtel</title>

    <%@ include file="../common/stylesheets.jsp"%>

</head>

<body data-help-page="view_courtel">

<div id="wrapper">


    <%@ include file="../common/sidebar.jsp"%>

    <%@ include file="../common/header.jsp"%>

    <!-- Page Content -->
    <div id="page-content-wrapper">

        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <h3>Amend Courtel</h3>

                    <c:if test="${not empty successMessage}" >
                        <div class="alert alert-success">
                            <p>${successMessage}</p>
                        </div>
                    </c:if>

                    <form:form commandName="courtelAmendCommand"
                               action="${context}/courtel/amend_courtel"
                               method="POST"
                               class="form-horizontal">

                        <%-- CSRF token --%>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <%--
                            This error block must be within the form:form tags , otherwise you won't get any errors back !!
                        --%>
                        <spring:hasBindErrors name="courtelAmendCommand">
                            <c:forEach items="${errors.globalErrors}" var="errorMessage">
                                <c:if test="${not empty errorMessage.defaultMessage}">
                                    <div class="alert alert-danger" id="errors">
                                        <c:out value="${errorMessage.defaultMessage}" />
                                    </div>
                                </c:if>
                            </c:forEach>
                        </spring:hasBindErrors>
                        <!-- /form-group -->

                        <div class="form-group">
                            <spring:bind path="courtelListAmount">
                                <div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.courtelAmendCommand'].hasFieldErrors('courtelListAmount') ? 'has-error' : ''}">
                                    <label for="courtelListAmount">Courtel List Amount</label>
                                    <form:input placeholder=""
                                                path="courtelListAmount"
                                                id="courtelListAmount"
                                                maxlength="255"
                                                cssClass="form-control"/>
                                    <spring:hasBindErrors name="courtelAmendCommand">
                                        <div class="help-block" element="span">
                                                ${errors.hasFieldErrors('courtelListAmount') ? errors.getFieldError('courtelListAmount').defaultMessage : ''}
                                        </div>
                                    </spring:hasBindErrors>
                                </div>
                            </spring:bind>

                            <spring:bind path="courtelMaxRetry">
                                <div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.courtelAmendCommand'].hasFieldErrors('courtelMaxRetry') ? 'has-error' : ''}">
                                    <label for="courtelMaxRetry">Courtel Max Retry</label>
                                    <form:input placeholder=""
                                                path="courtelMaxRetry"
                                                id="courtelMaxRetry"
                                                maxlength="255"
                                                cssClass="form-control"/>
                                    <spring:hasBindErrors name="courtelAmendCommand">
                                        <div class="help-block" element="span">
                                                ${errors.hasFieldErrors('courtelMaxRetry') ? errors.getFieldError('courtelMaxRetry').defaultMessage : ''}
                                        </div>
                                    </spring:hasBindErrors>
                                </div>
                            </spring:bind>

                            <spring:bind path="messageLookupDelay">
                                <div class="col-md-12 ${requestScope['org.springframework.validation.BindingResult.courtelAmendCommand'].hasFieldErrors('messageLookupDelay') ? 'has-error' : ''}">
                                    <label for="messageLookupDelay">Message Lookup Delay</label>
                                    <form:input placeholder=""
                                                path="messageLookupDelay"
                                                id="messageLookupDelay"
                                                maxlength="255"
                                                cssClass="form-control"/>
                                    <spring:hasBindErrors name="courtelAmendCommand">
                                        <div class="help-block" element="span">
                                                ${errors.hasFieldErrors('messageLookupDelay') ? errors.getFieldError('messageLookupDelay').defaultMessage : ''}
                                        </div>
                                    </spring:hasBindErrors>
                                </div>
                            </spring:bind>
                    </div>

                        <div class="form-group">
                            <div class="col-md-12">
                                <button id="btnUpdateConfirm" class="btn btn-primary" name="btnUpdateConfirm"><span
                                        class="glyphicon glyphicon-edit"></span> Update Courtel</button>
                            </div>
                        </div>
                    <!-- /form-group -->

                    </form:form>
                    <!-- /form -->

                </div>
            </div> <!-- End row 1 -->
        </div>
        <!-- /container-fluid -->
    </div>
    <!-- /#page-content-wrapper -->
    <%@ include file="../common/footer.jsp"%>

</div>
<!-- /#wrapper -->

<%@ include file="../common/scripts.jsp"%>

<script type="text/javascript" src="${context}/js/amend_courtel.js"></script>


</body>

</html>