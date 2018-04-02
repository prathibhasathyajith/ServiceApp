<%-- 
    Document   : faq
    Created on : Mar 26, 2018, 11:23:36 AM
    Author     : prathibha_s
--%>

<%@page import="com.serviceapp.varlist.CommonVarlist"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">

    <head>

        <%@include file="/stylelinks.jspf" %>
        <script type="text/javascript">

            $.subscribe('anyerrors', function (event, data) {
                window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
            });

            function resetAllData() {
                $('#id').attr('readOnly', false);
                $('#id').val("");
                $("#id").css("color", "black");
//                $('#type').val("");
                $('#section').val("");
                $('#status').val("");
                $('#question').val("");
                $('#answer').val("");
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        id: "",
//                        type: "",
                        section: "",
                        status: "",
                        question: "",
                        answer: "",
                        search: false
                    }
                });
                jQuery("#gridtable").trigger("reloadGrid");
                $(".hideme").show();
            }

            function editformatter(cellvalue) {
                return "<a href='#/' title='Edit' onClick='javascript:editFaqInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block; margin-left: auto; margin-right: auto;'/></a>";
            }

            function editFaqInit(keyval) {
                $("#updatedialog").data('id', keyval).dialog('open');
            }

            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailFAQ.action?id=" + $led.data('id'));
            });

            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#/' title='Delete' onClick='javascript:deleteFaqInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display: block; margin-left: auto; margin-right: auto;'/></a>";
            }

            function deleteFaqInit(keyval) {
                $('#divmsg').empty();
                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete FAQ : ' + keyval + ' ?');
                return false;
            }

            function deleteFAQ(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteFAQ',
                    data: {id: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);

                        jQuery("#gridtable").trigger("reloadGrid");
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function resetFieldData() {

                $("#iid").val("");
                $("#itype").val("");
                $("#isection").val("");
                $("#istatus").val("");
                $("#iquestion").val("");
                $("#ianswer").val("");

                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function cancelAllData() {
                editSystemUser(null);
            }

            function alpha(e) {
                var k;
                document.all ? k = e.keyCode : k = e.which;
                return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57) || (k == 13));
            }



            function searchFAQ() {
                $('#message').empty();
                var id = $('#id').val();
//                var type = $('#type').val();
                var section = $('#section').val();
                var status = $('#status').val();
                var question = $('#question').val();
                var answer = $('#answer').val();
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        id: id,
//                        type: type,
                        section: section,
                        status: status,
                        question: question,
                        answer: answer,
                        search: true
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

        </script>

        <title></title>
    </head>

    <body style="">
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
                        <s:form cssClass="form" id="faqadd" method="post" action="FAQ" theme="simple" >

                            <s:hidden id="oldvalue" name="oldvalue" ></s:hidden>
                            <s:url var="addurl" action="viewpopupFAQ"/>
                            <s:url var="updateurl" action="UpdateFAQ"/>

                            <div class="row"> 
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label >ID</label>
                                        <s:textfield name="id" id="id" maxlength="6" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onkeypress="return alpha(event)"/> 
                                    </div>
                                </div>
                                <!--                                <div class="col-sm-3">
                                                                    <div class="form-group">
                                                                        <label >Type</label>
                                                                        <!s:select  id="type" list="%{typeList}"  name="type" headerKey=""  headerValue="--Select Type--" listKey="code" listValue="description"  disabled="false" cssClass="form-control" value="%{type}" />
                                                                    </div>
                                                                </div>-->
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label >Section</label>
                                        <s:select  id="section" list="%{sectionFaqList}"  name="section" headerKey=""  headerValue="--Select Section--" listKey="sectionType" listValue="description"  disabled="false" cssClass="form-control" value="%{section}" />
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label >Status</label>
                                        <s:select  id="status" list="%{statusList}"  name="status" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description"  disabled="false" cssClass="form-control" value="%{status}" />
                                    </div>
                                </div>

                            </div>
                            <div class="row"> 
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label >Question</label>
                                        <s:textarea name="question" id="question" maxlength="2000" cssClass="form-control" /> 
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label >Answer</label>
                                        <s:textarea name="answer" id="answer" maxlength="2000" cssClass="form-control"/> 
                                    </div>
                                </div>
                            </div>
                        </s:form> 
                        <div class="row form-inline">
                            <div class="col-sm-4">
                                <div class="form-group">                                             
                                    <sj:submit 
                                        button="true"
                                        value="Search" 
                                        onclick="searchFAQ()"  
                                        id="searchbut"
                                        cssClass="uinew-button-submit"
                                        />
                                </div>
                                <div class="form-group">                               
                                    <sj:submit 
                                        button="true" 
                                        value="Reset" 
                                        name="reset" 
                                        onClick="resetAllData()"
                                        cssClass="uinew-button-reset"
                                        /> 
                                </div>
                            </div>


                            <!--                                    <div class="col-sm-5"></div>        
                            -->
                            <div class="col-sm-5"></div>
                            <div class="col-sm-3 text-right">
                                <div class="form-group">
                                    <sj:submit 
                                        openDialog="remotedialog"
                                        button="true"
                                        href="%{addurl}"
                                        buttonIcon="ui-icon-newwin"
                                        value="Add New FAQ"
                                        id="addButton_new"
                                        cssClass="uinew-button-submit"
                                        />
                                </div>
                            </div>
                        </div> 
                    </div>
                    <!--end newly changed-->             
                    <sj:dialog 
                        id="deletedialog" 
                        buttons="{ 
                        'OK':function() { deleteFAQ($(this).data('keyval'));$( this ).dialog( 'close' ); },
                        'Cancel':function() { $( this ).dialog( 'close' );} 
                        }" 
                        autoOpen="false" 
                        modal="true" 
                        title="Delete FAQ"                            
                        />
                    <!-- Start delete process dialog box -->
                    <sj:dialog 
                        id="deletesuccdialog" 
                        buttons="{
                        'OK':function() { $( this ).dialog( 'close' );}
                        }"  
                        autoOpen="false" 
                        modal="true" 
                        title="Deleting Process." 
                        />

                    <sj:dialog                                     
                        id="updatedialog"                                 
                        autoOpen="false" 
                        modal="true" 
                        position="center"
                        title="Update FAQ"
                        onOpenTopics="openviewtasktopage" 
                        loadingText="Loading .."
                        width="900"
                        height="450"
                        dialogClass= "dialogclass"
                        />  

                    <sj:dialog                                     
                        id="remotedialog"                                 
                        autoOpen="false" 
                        modal="true" 
                        title="Add FAQ"                            
                        loadingText="Loading .."                            
                        position="center"                            
                        width="900"
                        height="500"
                        dialogClass= "dialogclass"
                        />
                </div>
                <!-- start table -->
                <div class="tb-table">
                    <s:url var="listurl" action="listFAQ"/>
                    <s:set var="pcaption">FAQ</s:set>

                    <sjg:grid
                        id="gridtable"
                        caption="%{pcaption}"
                        dataType="json"
                        href="%{listurl}"
                        pager="true"
                        gridModel="gridModel"
                        rowList="10,15,20"
                        rowNum="10"
                        autowidth="true"
                        rownumbers="true"
                        onCompleteTopics="completetopics"
                        rowTotal="false"
                        viewrecords="true"
                        onErrorTopics="anyerrors"
                        >
                        <sjg:gridColumn name="id" index="id" title="Edit" width="30" formatter="editformatter" hidden="#vupdatelink" frozen="false"/>
                        <sjg:gridColumn name="id" index="id" title="Delete" width="50" formatter="deleteformatter" hidden="#vdelete" frozen="false"/>
                        <sjg:gridColumn name="id" index="id" title="ID"  sortable="true" frozen="false"/>
                        <%--<sjg:gridColumn name="type" index="type" title="Type"  sortable="true"/>--%>
                        <sjg:gridColumn name="section" index="section" title="Section"  sortable="true"/>
                        <sjg:gridColumn name="status" index="status" title="Status"  sortable="true"/>
                        <sjg:gridColumn name="question" index="question" title="Question"  sortable="true"/>
                        <sjg:gridColumn name="answer" index="answer" title="Answer"  sortable="true"/>
                        <sjg:gridColumn name="createdtime" index="createdtime" title="Created Time"  sortable="true"/>

                    </sjg:grid> 
                </div> 
            </div>
        </body>
</html>
