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
                body {
                    text-align: center;
                }

                section {
                    width: 100%;
                    margin: auto;
                    text-align: left;
                }

            </style>
            <script>


                function updateTerm(keyval) {
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
                            if (data.message != null) {
                                var msgError = '<div class="ui-widget actionError">\n\
                                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \n\
                                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\n\
                                <span>Terms cannot be empty</span></p>\n\
                                </div></div>';
                                $("#divmsg").append(msgError);
                            } else {
                                var msgSuccess = '<div id="successMsg" class="ui-widget actionMessage">\n\
                                <div class="ui-state-highlight ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \n\
                                <p><span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>\n\
                                <span>Password policy updated successfully</span>\n\
                                </p></div></div>';

                                $("#divmsg").append(msgSuccess);
                            }
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

//                            var qlist = data.questionList;
//                            $("#questionSearch option").remove();
//                            $('#questionSearch').append('<option value="">--Select Question--</option>');
//                            $.each(qlist, function (index, item) {
//                                $('#questionSearch').append("<option value='" + item.QCode + "'>" + item.question + "</option>");
//                            });
                        },
                        error: function (data) {
                            window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                        }
                    });
                }
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
            <div class="tb-breadcrumb">User Management > System User</div>
            <div class="tb-form">
                <div class="containe-fluid">

                    <s:form action="Terms" id="termform" method="post" theme="simple" cssClass="form">
                        <div class="row ">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Version</label>
                                    <s:select cssClass="form-control" id="versionno" list="%{versionList}" name="versionno"
                                              headerKey="" headerValue="--Select Version--"
                                              listKey="key" listValue="value" onchange="onchangeVersion(this.value)" />
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Status</label>
                                    <s:select  id="status" list="%{statusList}"  name="status" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description" value="%{status}" disabled="false" cssClass="form-control" disabled="true"/>
                                </div>
                            </div>
                        </div> 
                    </s:form>

                    <section id="editor" style="font-family: Roboto">
                        <div id='edit' style="margin-top: 30px;">

                        </div>
                    </section>

                    <div>
                        <br>
                            <s:url action="updateTerms" var="updateturl"/>
                            <sj:submit
                                button="true"
                                value="Update"
                                href="%{updateturl}"
                                targets="divmsg"
                                id="updatebtn"
                                cssClass="uinew-button-submit" 
                                />                        
                            <input type="button" value="Save" onclick="updateTerm()" />
                            <input type="button" value="Get" onclick="get()" />
                            <input type="button" value="Set" onclick="set()" />
                            <input type="button" value="Isempty" onclick="Isempty()" />
                            <input type="button" value="editon" onclick="editon()" />
                            <input type="button" value="editoff" onclick="editoff()" />
                            <input type="button" value="clear" onclick="clears()" />
                    </div>


                </div>
            </div>
        </div>


        <script>
            function get() {
                alert($('#edit').froalaEditor('html.get', true));
            }

            function set() {
                $('#edit').froalaEditor('html.set', '<p>My custom paragraph.</p>');
            }

            function Isempty() {
                alert($('#edit').froalaEditor('core.isEmpty'));
            }

            function editon() {
                $('#edit').froalaEditor('edit.on');
            }

            function editoff() {
                $('#edit').froalaEditor('edit.off');
            }

            function clears() {
                $('#edit').froalaEditor('html.set', '');
            }


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
                    toolbarButtons: ['fullscreen', 'bold', 'italic', 'underline', 'strikeThrough', 'subscript', 'superscript', 'fontFamily', 'fontSize', '|', 'color', 'emoticons', 'inlineStyle', 'paragraphStyle', '|', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'outdent', 'indent', '-', 'insertLink', 'insertImage', 'insertVideo', 'insertFile', 'insertTable', '|', 'quote', 'insertHR', 'undo', 'redo', 'clearFormatting', 'selectAll', 'html', '-', 'clear'],
                    fontFamily: {
                        "Roboto,sans-serif": 'Roboto',
                        "Oswald,sans-serif": 'Oswald',
                        "Montserrat,sans-serif": 'Montserrat',
                        "'Open Sans Condensed',sans-serif": 'Open Sans Condensed'
                    }
                });

            });

        </script>
    </body>
</html>
