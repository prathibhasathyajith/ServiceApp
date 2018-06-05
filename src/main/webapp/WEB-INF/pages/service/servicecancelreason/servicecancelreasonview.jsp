<%-- 
    Document   : servicecancelreasonview
    Created on : Jun 5, 2018, 11:31:44 AM
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
    </head>            
    <body>
        <s:div id="divmsg">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <div class="tb-modal-body tb-header-text">
            <div class="tb-modal">
                <div class="containe-fluid">
                    <s:form id="serviceCancelReason2" method="post" action="*ServiceCancelReason" cssClass="form" theme="simple">
                        <div class="row "> 
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Service ID</label>
                                    <s:label style="margin-bottom: 0px;" name="serviceId"  value="%{scrDataBean.serviceId}" cssClass="form-control"/>
                                </div>  
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-4">
                                ------------------------- Initiated User ---------------------------------------------
                            </div>
                        </div>
                        <div class="row "> 
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Initiated User First Name</label>
                                    <s:label style="margin-bottom: 0px;" name="fname"  value="%{scrDataBean.fname}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Initiated User Last Name</label>
                                    <s:label style="margin-bottom: 0px;" name="lname"  value="%{scrDataBean.lname}" cssClass="form-control"/>
                                </div>
                            </div>    
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label>Mobile</label>
                                    <s:label style="margin-bottom: 0px;" name="mobile"  value="%{scrDataBean.mobile}" cssClass="form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label >NIC</label>
                                    <s:label style="margin-bottom: 0px;" name="nic"  value="%{scrDataBean.nic}" cssClass="form-control"/>
                                </div> 
                            </div>
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label >Email</label>
                                    <s:label style="margin-bottom: 0px;" name="email"  value="%{scrDataBean.email}" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label>Created Time</label>
                                    <s:label style="margin-bottom: 0px;" name="createdTime"  value="%{scrDataBean.createdTime}" cssClass="form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-4">
                                -----------------------------------------------------------------------------------------
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label>Reason</label>
                                    <s:textarea readonly="true" style="margin-bottom: 0px; word-break: break-all;background-color: white;" name="reason"  value="%{scrDataBean.reason}" cssClass="form-control"/>
                                </div>
                            </div>
                        </div>        

                        <div class="row">
                            <div class="col-sm-9"></div>
                            <div class="col-sm-3">

                                <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
                                    <!sj:submit
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