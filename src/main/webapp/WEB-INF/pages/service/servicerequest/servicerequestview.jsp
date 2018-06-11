<%-- 
    Document   : servicerequestview
    Created on : Jun 11, 2018, 10:17:53 AM
    Author     : prathibha_s
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>View Service Request</title>
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
                    <s:form id="servicerecoview" method="post" action="ServiceRequest" theme="simple" cssClass="form" >
                        <div class="row "> 
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Service ID</label>
                                    <s:label style="margin-bottom: 0px;"   value="%{serReq.serviceId}" cssClass="form-control"/>
                                </div>  
                            </div>
                        </div><div class="row "> 
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Customer ID</label>
                                    <s:label style="margin-bottom: 0px;"  value="%{serReq.cusId}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Customer First Name</label>
                                    <s:label style="margin-bottom: 0px;"  value="%{serReq.cusfname}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Bass ID</label>
                                    <s:label style="margin-bottom: 0px;"  value="%{serReq.bassId}" cssClass="form-control"/>
                                </div>  
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Bass First Name</label>
                                    <s:label style="margin-bottom: 0px;"  value="%{serReq.bassfname}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Status</label>
                                    <s:label style="margin-bottom: 0px;"  value="%{serReq.status}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Customer Latitude</label>
                                    <s:label style="margin-bottom: 0px;"   value="%{serReq.latitude}" cssClass="form-control"/>
                                </div>  
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Customer Longitude</label>
                                    <s:label style="margin-bottom: 0px;"  value="%{serReq.longitude}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-8">
                                <div class="form-group">
                                    <label >Customer Address</label>
                                    <s:label style="margin-bottom: 0px;"  value="%{serReq.custAddress}" cssClass="form-control" />
                                    </div>  
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Updated Time</label>
                                    <s:label style="margin-bottom: 0px;"  value="%{serReq.updatedTime}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Created Time</label>
                                    <s:label style="margin-bottom: 0px;"  value="%{serReq.createdTime}" cssClass="form-control"/>
                                </div>  
                            </div>
                        </div>
                    </s:form>
                </div>
            </div>
        </div>
    </body>
</html>