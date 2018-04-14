<%-- 
    Document   : suggestuserview
    Created on : Apr 10, 2018, 3:14:00 PM
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
            <title>View Su Customer</title>

            <script type="text/javascript">

            </script>
            <style>
                #Referred_table > tbody > tr > td{
                    font-size: 13px;
                    padding: 5px;
                    
                }
            </style>
    </head>

    <body>
        <s:div id="divmsgupdate">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <div class="tb-modal-body tb-header-text">
            <div class="tb-modal">
                <div class="containe-fluid">
                    <s:form id="suggestuserview" method="post" action="SuggestUser" theme="simple" cssClass="form" enctype="multipart/form-data">
                        <h5><b>Suggested User</b></h5>
                        
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="">
                                    <label>User Id</label>
                                    <s:textfield  name="id" id="id" cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="">
                                    <label>First Name</label>
                                    <s:textfield  name="firstName" id="firstName" cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="">
                                    <label>Last Name</label>
                                    <s:textfield  name="lastName" id="lastName" cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="">
                                    <label>Email</label>
                                    <s:textfield  name="email" id="email" cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="">
                                    <label>Area</label>
                                    <s:textfield  name="area" id="area" cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="">
                                    <label>Service Role</label>
                                    <s:textfield  name="serviceRole" id="serviceRole" cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="">
                                    <label>Mobile</label>
                                    <s:textfield  name="mobile" id="mobile" cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="">
                                    <label>Status</label>
                                    <s:textfield  name="status" id="status" cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                        </div>
                        <div class="row row_popup">
                            <div class="horizontal_line_popup"></div>
                        </div>
                        <h5><b>Referred User</b></h5>
                        <br>
                        <div class="row" >
                            <div class="col-sm-2" style="float: left">
                                <div class="squar2 upload-button" >  
                                    <img class="profile-pic " src="data:image/jpeg;base64,<s:property value="prifImg"/>" id="prifImg" name="prifImg">
                                </div>
                                <div class="p-image">
                                    <!--<label>User Image</label>-->
                                </div>
                            </div>
                            <div class="col-sm-10">
                                <s:hidden  name="firstName_user" id="firstName_user"/>
                                <s:hidden  name="lastName_user" id="lastName_user"/>
                                <s:hidden  name="mobile_user" id="mobile_user"/>
                                <s:hidden  name="email_user" id="email_user"/>
                                <s:hidden  name="nic_user" id="nic_user"/>
                                <s:hidden  name="id_user" id="id_user"/>
                                
                                <table id="Referred_table">
                                    <tr>
                                        <td>First Name</td>
                                        <td>:</td>
                                        <td><label id="firstName_user_lbl"></label></td>
                                    </tr>
                                    <tr>
                                        <td>Last Name</td>
                                        <td>:</td>
                                        <td><label id="lastName_user_lbl"></label></td>
                                    </tr>
                                    <tr>
                                        <td>Mobile</td>
                                        <td>:</td>
                                        <td><label id="mobile_user_lbl"></label></td>
                                    </tr>
                                    <tr>
                                        <td>Email</td>
                                        <td>:</td>
                                        <td><label id="email_user_lbl"></label></td>
                                    </tr>
                                    <tr>
                                        <td>NIC</td>
                                        <td>:</td>
                                        <td><label id="nic_user_lbl"></label></td>
                                    </tr>
                                    <tr>
                                        <td>User ID</td>
                                        <td>:</td>
                                        <td><label id="id_user_lbl"></label></td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                    </s:form>
                </div>
            </div>
        </div>
        <script>
            $(document).ready(function () {
                $("#firstName_user_lbl").text($("#firstName_user").val());
                $("#lastName_user_lbl").text($("#lastName_user").val());
                $("#mobile_user_lbl").text($("#mobile_user").val());
                $("#email_user_lbl").text($("#email_user").val());
                $("#nic_user_lbl").text($("#nic_user").val());
                $("#id_user_lbl").text($("#id_user").val());
            });
        </script>
    </body>
</html>
