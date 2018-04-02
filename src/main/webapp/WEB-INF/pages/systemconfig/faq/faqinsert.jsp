<%-- 
    Document   : insertfaq
    Created on : Mar 26, 2018, 11:23:49 AM
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
        <title>Insert System User</title>
        <script type="text/javascript">

            $.subscribe('resetAddButton', function (event, data) {
                $("#iid").val("");
//                $("#itype").val("");
                $("#isection").val("");
                $("#iquestion").val("");
                $("#ianswer").val("");

                $("#divmsginsert").empty();
                $("#istatus").val("");
            });

            function alpha(e) {
                var k;
                document.all ? k = e.keyCode : k = e.which;
                return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57) || (k == 13));
            }



        </script>
        <style>
            .tooltip {

                background-color: black;
                color: #fff;
                border-radius: 6px;
                padding: 5px 0;
                white-space: pre-line;
                height: auto;
            }
            .tooltip-inner {
                min-width: 375%;
                max-width: 100%; 
                text-align: left;
            }

        </style>

    </head>
    <body>
        <s:div id="divmsginsert">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <div class="tb-modal-body tb-header-text">
            <div class="tb-modal">
                <div class="containe-fluid">
                    <s:form id="faqinsert" method="post" action="FAQ" theme="simple" cssClass="form">   

                        <div class="row">
                            <!--                            <div class="col-sm-4">
                                                            <div class="form-group">
                                                                <span style="color: red">*</span><label >Type</label>
                                                                <!s:select name="type" id="itype" list="%{typeList}" headerKey=""  headerValue="--Select Type--" listKey="code" listValue="description"  disabled="false" cssClass="form-control"/>
                                                            </div>
                                                        </div>-->
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >Section</label>
                                    <s:select  id="isection" list="%{sectionFaqList}"  name="section" headerKey=""  headerValue="--Select Section--" listKey="sectionType" listValue="description" disabled="false" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >Status</label>
                                    <s:select  id="istatus" list="%{statusList}"  name="status" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description"  disabled="false" cssClass="form-control"/>
                                </div>
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>Question</label>
                                    <s:textarea name="question" id="iquestion" cssClass="form-control"  maxlength="1000" rows="2"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>Answer</label>
                                    <s:textarea name="answer" id="ianswer" cssClass="form-control"  maxlength="1000" rows="2"  />
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="horizontal_line_popup"></div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="horizontal_line_popup"></div>
                        </div>

                        <div class="row form-inline">
                            <div class="col-sm-8">
                                <div class="form-group">
                                    <span class="mandatoryfield">Mandatory fields are marked with *</span>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <sj:submit 
                                        button="true" 
                                        value="Reset" 
                                        name="reset" 
                                        cssClass="uinew-button-reset"
                                        onClickTopics="resetAddButton"
                                        />                        
                                </div>
                                <div class="form-group" >
                                    <s:url action="addFAQ" var="inserturl"/>
                                    <sj:submit
                                        button="true"
                                        value="Add"
                                        href="%{inserturl}"
                                        onClickTopics=""
                                        targets="divmsginsert"
                                        id="addbtn"
                                        cssClass="uinew-button-submit" 
                                        />                        
                                </div>
                            </div>
                        </div>  
                    </s:form>  
                </div>
            </div>
        </div>  
    </body>
</html>
