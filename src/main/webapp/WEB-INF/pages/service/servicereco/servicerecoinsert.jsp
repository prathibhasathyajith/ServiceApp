<%-- 
    Document   : servicerecoinsert
    Created on : May 1, 2018, 10:41:22 AM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="resouces/css/common/common_popup.css">
        <title>Insert Service Recommendation</title>
        <script type="text/javascript"></script>
    </head>
    <body>
        <s:div id="divmsginsert">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <div class = "tb-modal-body tb-header-text" >
            <div class="tb-modal">
                <div class="containe-fluid">
                    <s:form id="servicerecoinsert" method="post" action="ServiceReco" theme="simple" cssClass="form">   
                    </s:form>  
                </div>
            </div>
        </div>  
    </body>
</html>