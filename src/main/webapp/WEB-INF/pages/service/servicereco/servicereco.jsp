<%-- 
    Document   : servicereco
    Created on : May 1, 2018, 10:41:14 AM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="/stylelinks.jspf" %>
        <script type="text/javascript">
        </script>
        <title></title>
    </head>
    <body>
        <!--header-->
        <jsp:include page="/header.jsp"/>
        <!--nav bar-->
        <jsp:include page="/navbar.jsp"/>

        <!--body content-->
        <div class="tb-body">
            <s:div id="divmsg">
                <s:actionerror theme="jquery"/>
                <s:actionmessage theme="jquery"/>
            </s:div>
            <div class="tb-breadcrumb">Service > Service Recommendation</div>
            <div class="tb-form">
                <div class="containe-fluid">
                    <s:form id="serviceReco" method="post" action="ServiceReco" theme="simple" cssClass="form" >

                    </s:form>
                </div>
                <div class="tb-table"></div>
            </div>
        </div>
    </body>
</html>