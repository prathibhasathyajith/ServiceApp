<%-- 
    Document   : aboutus
    Created on : Mar 30, 2018, 9:02:55 AM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0" />
            <%@include file="/stylelinks.jspf" %>
            <style>
                section {
                    width: 100%;
                    margin: auto;
                    text-align: left;
                }
            </style>
            <script>
                $(document).ready(function () {

                });
                function updateinfo() {
                    $("#divmsg").empty();
                    var code = $("#type").val();
                    var description = $('#edit2').froalaEditor('html.get', true);
                    var isEmpty = $('#edit2').froalaEditor('core.isEmpty');

                    $.ajax({
                        url: '${pageContext.request.contextPath}/updateAppInfo.action',
                        data: {code: code, description: description, empty: isEmpty},
                        dataType: "json",
                        type: "POST",
                        success: function (data) {

                            if (data.message != "") {
                                var msgError = '<div class="ui-widget actionError">\n\
                                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \n\
                                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\n\
                                <span>' + data.message + '</span></p>\n\
                                </div></div>';
                                $("#divmsg").append(msgError);
                            } else {
                                var mess = (code == "ABOUTUS") ? 'About us updated successful' : 'Support updated successful';
                                var msgSuccess = '<div id="successMsg" class="ui-widget actionMessage">\n\
                                <div class="ui-state-highlight ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \n\
                                <p><span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>\n\
                                <span>' + mess +'</span>\n\
                                </p></div></div>';
                                $("#divmsg").append(msgSuccess);
                                resetfields();
                            }

                            $("html, body").animate({scrollTop: 0}, "fast");
                        },
                        error: function (data) {
                            window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                        }
                    });
                }

                function changeType(val) {
                    if (val == '') {
                        val = null;
                    }
                    $.ajax({
                        url: '${pageContext.request.contextPath}/findAppInfo.action',
                        data: {code: val},
                        dataType: "json",
                        type: "POST",
                        success: function (data) {
                            if (data.message == null) {
                                $("#updatebtn").button("enable");
                                $('#edit2').froalaEditor('edit.on');
                                $('#edit2').froalaEditor('html.set', data.description);
                            } else {
                                $("#updatebtn").button("disable");
                                $('#edit2').froalaEditor('html.set', '');
                            }
                        },
                        error: function (data) {
                            window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                        }
                    });
                }
                
                function resetfields() {
                    $("#type").val('');
                    $('#edit2').froalaEditor('html.set', '');
                }
            </script>
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
            <div class="tb-breadcrumb">System Config > System User</div>
            <div class="tb-form">
                <div class="containe-fluid">
                    <s:form action="AppInfo" id="termform" method="post" theme="simple" cssClass="form">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Type</label>
                                    <s:select  id="type" list="%{typeList}"  name="type" 
                                               headerKey=""  headerValue="--Select Type--" 
                                               listKey="key" listValue="value" value="%{type}" 
                                               disabled="false" cssClass="form-control" 
                                               onchange="changeType(this.value)"/>
                                </div>
                            </div>
                        </div> 
                    </s:form>

                    <!--text editor-->
                    <section id="editor2" style="font-family: Roboto">
                        <div id='edit2' style="margin-top: 20px;">

                        </div>
                    </section>

                    <div class="row "></div>
                    <div class="row form-inline">
                        <div class="col-sm-8">
                            <div class="form-group">  
                                <sj:submit button="true" 
                                           id="updatebtn" 
                                           value="Update" 
                                           name = "updatebtn"
                                           disabled="true" 
                                           onclick="updateinfo()"
                                           cssClass="uinew-button-submit" 
                                           />
                            </div>
                        </div>
                    </div>
                </div>


                <script>
                    $(function () {
                        $.FroalaEditor.DefineIcon('clear', {
                            NAME: 'remove'
                        });
                        $('#edit2').froalaEditor({
                            theme: 'dark',
                            height: 250,
                            toolbarButtons: ['fullscreen', 'bold', 'italic', 'underline', 'strikeThrough', 'fontFamily', 'fontSize', '|', 'color', 'paragraphStyle', '|', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'outdent', 'indent', '-', 'insertLink', 'insertImage', 'insertTable', '|', 'quote', 'insertHR', 'undo', 'redo', 'clearFormatting', 'selectAll'],
                            fontFamily: {
                                "Roboto,sans-serif": 'Roboto',
                                "Oswald,sans-serif": 'Oswald',
                                "Montserrat,sans-serif": 'Montserrat',
                                "'Open Sans Condensed',sans-serif": 'Open Sans Condensed'
                            }
                        });
                        $('#edit2').froalaEditor('edit.off');
                    });
//                    toolbarButtons: ['fullscreen', 'bold', 'italic', 'underline', 'strikeThrough', 'subscript', 'superscript', 'fontFamily', 'fontSize', '|', 'color', 'emoticons', 'inlineStyle', 'paragraphStyle', '|', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'outdent', 'indent', '-', 'insertLink', 'insertImage', 'insertVideo', 'insertFile', 'insertTable', '|', 'quote', 'insertHR', 'undo', 'redo', 'clearFormatting', 'selectAll', 'html', '-', 'clear'],

                </script>
            </div>
        </div>
    </body>
</html>
