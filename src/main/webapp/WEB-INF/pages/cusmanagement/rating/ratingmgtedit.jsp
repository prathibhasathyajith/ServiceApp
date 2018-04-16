<%-- 
    Document   : ratingmgtedit
    Created on : Apr 16, 2018, 5:45:47 PM
    Author     : prathibha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>


<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Edit Rating</title>
            <script type="text/javascript"></script>
    </head>
    <body>
        <s:div id="divmsgupdate">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <div class="tb-modal-body tb-header-text">
            <div class="tb-modal">
                <div class="containe-fluid">
                    <s:form id="ratingmgtedit" method="post" action="RatingMgt" theme="simple" cssClass="form" >

                    </s:form>
                </div>
            </div>
        </div>
    </body>
</html>