<%-- 
    Document   : viewaudit
    Created on : Mar 23, 2018, 10:31:40 AM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>       
        <script type="text/javascript">

            function backToMain() {
                window.location = "${pageContext.request.contextPath}/viewSystemAudit.action?";
            }
            function todo() {
                form = document.getElementById('auditform2');
                form.action = 'individualReportSystemAudit';
//                form.submit();
            }


        </script>
    </head>            
    <body>
        <s:div id="divmsg">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <div class="tb-modal-body tb-header-text">
            <div class="tb-modal">
                <div class="containe-fluid">
                    <s:form id="auditform2" method="post" action="*SystemAudit" cssClass="form" theme="simple">
                        <div class="row "> 
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Audit ID</label>
                                    <s:label style="margin-bottom: 0px;" name="auditId"  value="%{auditDataBean.id}" cssClass="form-control"/>
                                </div>  
                            </div>
                        </div>
                        <div class="row "> 
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Last Updated User</label>
                                    <s:label style="margin-bottom: 0px;" name="user"  value="%{auditDataBean.user}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Last Updated Time</label>
                                    <s:label style="margin-bottom: 0px;" name="created Date"  value="%{auditDataBean.lastUpdatedDate}" cssClass="form-control"/>
                                </div>
                            </div>    
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label>Task</label>
                                    <s:label style="margin-bottom: 0px;" name="task"  value="%{auditDataBean.task}" cssClass="form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label >Section</label>
                                    <s:label style="margin-bottom: 0px;" name="section"  value="%{auditDataBean.section}" cssClass="form-control"/>
                                </div> 
                            </div>
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label >Page</label>
                                    <s:label style="margin-bottom: 0px;" name="page"  value="%{auditDataBean.page}" cssClass="form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label>Description</label>
                                    <s:textarea readonly="true" style="margin-bottom: 0px; word-break: break-all;background-color: white;" name="description"  value="%{auditDataBean.description}" cssClass="form-control"/>
                                </div>
                            </div>
                        </div>        
                        <div class="row"> 
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label>Old Values</label>
                                    <s:textarea readonly="true" style="margin-bottom: 0px; word-break: break-all;background-color: white;" name="oldvalue"  value="%{auditDataBean.oldvalue}" cssClass="form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="row"> 
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label >New Values</label>
                                    <s:textarea readonly="true" style="margin-bottom: 0px; word-break: break-all;background-color: white;" name="newvalue"  value="%{auditDataBean.newvalue}" cssClass="form-control"/>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-9"></div>
                            <div class="col-sm-3">

                                <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                                    <sj:submit
                                        button="true"
                                        value="View PDF"
                                        id="viewindi" 
                                        name="viewindi" 
                                        onclick="todo()"  
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
