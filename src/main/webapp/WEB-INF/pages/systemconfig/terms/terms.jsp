<%-- 
    Document   : terms
    Created on : Mar 27, 2018, 9:01:24 PM
    Author     : prathibha
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
                #version-add{
                    display: none;
                }
                #version-select{
                    display: none;
                }
                #showdiv{
                    display: none;
                }

            </style>
            <script>
                $(document).ready(function () {

                });

                function addTerm() {
                    $("#divmsg").empty();
                    var versionInsert = $("#versionInsert").val();
                    var status = $("#status").val();
                    var description = $('#edit').froalaEditor('html.get', true);
                    var isEmpty = $('#edit').froalaEditor('core.isEmpty');

                    $.ajax({
                        url: '${pageContext.request.contextPath}/addTerms.action',
                        data: {versionno: versionInsert, description: description, status: status, empty: isEmpty},
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
                                var msgSuccess = '<div id="successMsg" class="ui-widget actionMessage">\n\
                                <div class="ui-state-highlight ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \n\
                                <p><span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>\n\
                                <span>Terms added successfully</span>\n\
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


                function updateTerm() {
                    $("#divmsg").empty();

                    var versionno = $("#versionno").val();
                    var status = $("#status").val();
                    var description = $('#edit').froalaEditor('html.get', true);
                    var isEmpty = $('#edit').froalaEditor('core.isEmpty');


                    $.ajax({
                        url: '${pageContext.request.contextPath}/updateTerms.action',
                        data: {versionno: versionno, description: description, status: status, empty: isEmpty},
                        dataType: "json",
                        type: "POST",
                        success: function (data) {

                            console.log(data.message);
                            if (data.message != "") {
                                var msgError = '<div class="ui-widget actionError">\n\
                                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \n\
                                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\n\
                                <span>' + data.message + '</span></p>\n\
                                </div></div>';
                                $("#divmsg").append(msgError);
                            } else {
                                var msgSuccess = '<div id="successMsg" class="ui-widget actionMessage">\n\
                                <div class="ui-state-highlight ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \n\
                                <p><span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>\n\
                                <span>Terms updated successfully</span>\n\
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


                function onchangeVersion(keyval) {
                    if (keyval == '') {
                        keyval = null;
                    }
                    $.ajax({
                        url: '${pageContext.request.contextPath}/findTerms.action',
                        data: {versionno: keyval},
                        dataType: "json",
                        type: "POST",
                        success: function (data) {

                            $("#status").val(data.status);
                            $("#status").attr("disabled", true);

                            if (data.status == "ACT") {
                                $('#edit').froalaEditor('edit.off');
                            } else {
                                $('#edit').froalaEditor('edit.on');
                                $("#status").attr("disabled", false);
                            }

                            $('#edit').froalaEditor('html.set', data.description);
                        },
                        error: function (data) {
                            window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                        }
                    });
                }

                function show(val) {
                    if (val) {
                        $("#updatebtnshow").removeClass("uinew-button-reset");
                        $("#updatebtnshow").addClass("uinew-button-other");
                        
                        $("#addbtnshow").removeClass("uinew-button-other");
                        $("#addbtnshow").addClass("uinew-button-reset");
                        
                        $("#divmsg").empty();
                        $('#edit').froalaEditor('edit.on');

                        resetfields();

                        $("#status").attr("disabled", true);
                        $("#version-add").fadeOut(1);
                        $("#version-add").fadeOut(1);
                        $("#version-select").fadeIn(1);
                        $("#version-select").fadeIn(1);
                        $("#showdiv").fadeIn(1);

                        $("#updatebtn").button("enable");
                        $("#addbtn").button("disable");

                    } else {
                        $("#addbtnshow").removeClass("uinew-button-reset");
                        $("#addbtnshow").addClass("uinew-button-other");
                        
                        $("#updatebtnshow").removeClass("uinew-button-other");
                        $("#updatebtnshow").addClass("uinew-button-reset");
                        
                        $("#divmsg").empty();
                        $('#edit').froalaEditor('edit.on');

                        resetfields();

                        $("#status").attr("disabled", false);
                        $("#version-select").fadeOut(1);
                        $("#version-select").fadeOut(1);
                        $("#version-add").fadeIn(1);
                        $("#version-add").fadeIn(1);
                        $("#showdiv").fadeIn(1);
                        $("#addbtn").button("enable");
                        $("#updatebtn").button("disable");
                    }
                }

                function resetfields() {
                    $("#versionno").val('');
                    $("#versionInsert").val('');
                    $("#status").val('');
                    $("#versionInsert").val('');
                    $('#edit').froalaEditor('html.set', '');
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
            <div class="tb-breadcrumb">User Management > System User</div>
            <div class="tb-form">
                <div class="containe-fluid">
                    <s:form action="Terms" id="termform" method="post" theme="simple" cssClass="form">
                        <div class="row">
                            <div class="col-sm-12">
                                <input id="addbtnshow" type="button" class="uinew-button-other" value="To add terms" onclick="show(false)" />
                                <input id="updatebtnshow" type="button" class="uinew-button-other" value="To update Terms" onclick="show(true)" />
                            </div>
                        </div>
                        <div class="row " id="showdiv">
                            <div class="col-sm-3">
                                <div class="form-group" id="version-select">
                                    <label>Version</label>
                                    <s:select cssClass="form-control" id="versionno" list="%{versionList}" name="versionno"
                                              headerKey="" headerValue="--Select Version--"
                                              listKey="key" listValue="value" onchange="onchangeVersion(this.value)" />
                                </div>
                                <div class="form-group" id="version-add">
                                    <label>Version No</label>
                                    <s:textfield  name="versionInsert" id="versionInsert" 
                                                  maxLength="20" cssClass="form-control"
                                                  onkeyup="$(this).val($(this).val().replace(/[^0-9.]/g,''))" 
                                                  onmouseout="$(this).val($(this).val().replace(/[^0-9.]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Status</label>
                                    <s:select  id="status" list="%{statusList}"  name="status" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description" value="%{status}" cssClass="form-control" disabled="true"/>
                                </div>
                            </div>
                        </div> 
                    </s:form>

                    <!--text editor-->
                    <section id="editor" style="font-family: Roboto">
                        <div id='edit' style="margin-top: 20px;">

                        </div>
                    </section>

                    <div class="row "></div>
                    <div class="row form-inline">
                        <div class="col-sm-8">
                            <div class="form-group">
                                <sj:submit button="true" 
                                           id="addbtn" 
                                           disabled="true" 
                                           cssClass="uinew-button-submit" 
                                           value="Add"
                                           onclick="addTerm()"
                                           />
                            </div>
                            <div class="form-group">  
                                <sj:submit button="true" 
                                           id="updatebtn" 
                                           value="Update" 
                                           disabled="true" 
                                           onclick="updateTerm()"
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
                        $.FroalaEditor.RegisterCommand('clear', {
                            title: 'Clear HTML',
                            focus: false,
                            undo: true,
                            refreshAfterCallback: true,
                            callback: function () {
                                this.html.set('');
                                this.events.focus();
                            }
                        });

                        $('#edit').froalaEditor({
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

                        $('#edit').froalaEditor('edit.off');

                    });

                </script>
            </div>
        </div>
    </body>
</html>
