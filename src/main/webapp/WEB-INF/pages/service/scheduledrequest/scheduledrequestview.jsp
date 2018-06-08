<%-- 
    Document   : scheduledrequestview
    Created on : Jun 7, 2018, 1:48:02 PM
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
            <title>View Scheduled Requests</title>
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
                    <s:form id="scheduledReqview" method="post" action="*ScheduledReq" theme="simple" cssClass="form" >
                        <div class="row "> 
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Customer ID</label>
                                    <s:label style="margin-bottom: 0px;" name="cusId"  value="%{sreqBean.cusId}" cssClass="form-control"/>
                                </div>  
                            </div>
                        </div>
                        <div class="row "> 
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Bass Type</label>
                                    <s:label style="margin-bottom: 0px;" name="bassType"  value="%{sreqBean.bassType}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Scheduled Date</label>
                                    <s:label style="margin-bottom: 0px;" name="scheduledDate"  value="%{sreqBean.scheduledDate}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Created Time</label>
                                    <s:label style="margin-bottom: 0px;" name="createdTime"  value="%{sreqBean.createdTime}" cssClass="form-control"/>
                                </div>  
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >First Name</label>
                                    <s:label style="margin-bottom: 0px;" name="fname"  value="%{sreqBean.fname}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Last Name</label>
                                    <s:label style="margin-bottom: 0px;" name="lname"  value="%{sreqBean.lname}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Mobile</label>
                                    <s:label style="margin-bottom: 0px;" name="mobile"  value="%{sreqBean.mobile}" cssClass="form-control"/>
                                </div>  
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Latitude</label>
                                    <s:label style="margin-bottom: 0px;" name="latitude"  value="%{sreqBean.latitude}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Longitude</label>
                                    <s:label style="margin-bottom: 0px;" name="longitude"  value="%{sreqBean.longitude}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Map</label>
                                    <s:label style="margin-bottom: 0px;" name="longitude"  value="%{sreqBean.longitude}" cssClass="form-control"/>
                                </div>  
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Description</label>
                                    <s:label style="margin-bottom: 0px;" name="description"  value="%{sreqBean.description}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Address</label>
                                    <s:label style="margin-bottom: 0px;" name="address"  value="%{sreqBean.address}" cssClass="form-control"/>
                                </div>  
                            </div>
                        </div>
                    </s:form>
                </div>
            </div>
        </div>
    </body>
</html>