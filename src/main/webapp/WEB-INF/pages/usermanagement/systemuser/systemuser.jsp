<%-- 
    Document   : systemuser
    Created on : Mar 20, 2018, 10:43:43 AM
    Author     : prathibha_s
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/stylelinks.jspf" %>
        <script type="text/javascript">
            $.subscribe('onclicksearch', function (event, data) {
                $('#message').empty();

                var username = $('#username').val();
                var fullname = $('#fullname').val();
                var email = $('#email').val();
                var contactno = $('#contactno').val();
                var status = $('#status').val();
                var serviceid = $('#serviceid').val();
                var nic = $('#nic').val();
                var expirydate = $('#expirydate').val();

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        username: username,
                        fullname: fullname,
                        email: email,
                        contactno: contactno,
                        status: status,
                        nic: nic,
                        serviceid: serviceid,
                        expirydate: expirydate,
                        search: true
                    }
                });

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            });

            $.subscribe('anyerrors', function (event, data) {
                window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
            });

            function resetAllData() {
                $('#username').attr('readOnly', false);
                $('#username').val("");
                $("#username").css("color", "black");
                $('#password').val("");
                $('#confirmpassword').val("");
                $('#expirydate').val('${PASSWORDEXPIRYPERIOD}');
                $("#expirydate").css("color", "#858585");
//                $('#dualauthuser').val("");
                $('#status').val("");
                $('#fullname').val("");
                $('#serviceid').val("");
                $('#address1').val("");
                $('#address2').val("");
                $('#city').val("");
                $('#contactno').val("");
                $('#email').val("");
                $('#nic').val("");
                $('#dateofbirth').val("");

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        username: "",
                        fullname: "",
                        email: "",
                        contactno: "",
                        status: "",
                        nic: "",
                        serviceid: "",
                        expirydate: "",
                        search: false
                    }
                });
                jQuery("#gridtable").trigger("reloadGrid");
                $(".hideme").show();
            }

            function changepasswordformatter(cellvalue, options, rowObject) {

                var status = rowObject.status;

                if (status == "Active") {
                    return "<a href='#' title='Change password' onClick='javascript:changepasswordInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-arrowreturnthick-1-s' style='display: block;margin-left: auto;margin-right: auto;'/></a>";

                }

                return("--");
            }

            function changepasswordInit(keyval) {
                $("#changepwddialog").data('username', keyval).dialog('open');
            }

            $.subscribe('openpwdchangetopage', function (event, data) {
                var $led = $("#changepwddialog");
                $led.html("Loading..");
                $led.load("changepasswordSystemuser.action?username=" + $led.data('username'));
            });

            function changepasswordInit_old(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/changePasswordSystemuser',
                    data: {usernameUserrole: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        } else {
                            resetAllChagePass();
                            $("#systemuseradd").hide();
                            $("#pwdResetform").show();

                            $('#cusername').val(data.username);
                            $('#cusername').attr('readOnly', true);
                            $('#chusername').val(data.husername);
                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }


            function editformatter(cellvalue) {
                return "<a href='#/' title='Edit' onClick='javascript:editSystemuserInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function editSystemuserInit(keyval) {
                $("#updatedialog").data('username', keyval).dialog('open');
            }

            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailSystemuser.action?username=" + $led.data('username'));
            });


            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#/' title='Delete' onClick='javascript:deleteSystemuserInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }
            function deleteSystemuserInit(keyval) {
                $('#divmsg').empty();
                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete system user ' + keyval + ' ?');
                return false;
            }

            function deleteSystemuser(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteSystemuser',
                    data: {username: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);

                        resetFieldData();
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }


//            function resetFieldData() {
//                a = $("#addButton").is(':disabled');
//                u = $("#updateButton").is(':disabled');
//
//                $('#username').attr('readOnly', false);
//                $('#username').val("");
//                $("#username").css("color", "black");
//                $('#password').val("");
//                $('#confirmpassword').val("");
//                $('#expirydate').val('${PASSWORDEXPIRYPERIOD}');
//                $("#expirydate").css("color", "#858585");
////                $('#dualauthuser').val("");
//                $('#status').val("");
//                $('#fullname').val("");
//                $('#serviceid').val("");
//                $('#address1').val("");
//                $('#address2').val("");
//                $('#city').val("");
//                $('#contactno').val("");
//                $('#email').val("");
//                $('#nic').val("");
//                $('#dateofbirth').val("");
//
//                if (a == true && u == true) {
//                    $('#addButton').button("disable");
//                    $('#updateButton').button("disable");
//                } else if (a == true && u == false) {
//                    $('#addButton').button("enable");
//                    $('#updateButton').button("disable");
//                } else if (a == false && u == true) {
//                    $('#addButton').button("enable");
//                    $('#updateButton').button("disable");
//                }
//
//                jQuery("#gridtable").trigger("reloadGrid");
//                $(".hideme").show();
//            }

            function resetFieldData() {

                $("#iusername").val("");
                $("#iserviceid").val("");
                $("#iaddress1").val("");
                $("#ipassword").val("");
                $("#iconfirmpassword").val("");
                $("#idescription").val("");
                $("#inic").val("");
                $("#icity").val("");
                $("#idateofbirth").val("");
                $("#icontactno").val("");
                $("#iemail").val("");
                $("#ifullname").val("");
                $("#iexpirydate").val('${PASSWORDEXPIRYPERIOD}');
                $("#istatus").val("");

                $("#cnewpwd").val("");
                $("#crenewpwd").val("");

                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
            }


            function resetAllChagePass() {
                $('#newpwd').val("");
                $('#renewpwd').val("");
                $('#changepassdivmsg').text("");

                editSystemuser(null);
            }


            function cancelAllData() {
                editSystemuser(null);
            }

            function alpha(e) {
                var k;
                document.all ? k = e.keyCode : k = e.which;
                return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57) || (k == 13));
            }

            function isNumberserch(evt) {
                evt = (evt) ? evt : window.event;
                var charCode = (evt.which) ? evt.which : evt.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                return true;
            }


        </script>

        <title></title>
    </head>
    <body>
        <!--header-->
        <jsp:include page="/header.jsp"/>
        <!--nav bar-->
        <jsp:include page="/navbar.jsp"/>
        <s:div id="divmsg">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <!--body content-->
        <div class="tb-body tb-header-text">
            <div class="tb-breadcrumb">User Management > System User</div>
            <div class="tb-form">
                <div class="containe-fluid">
                    <s:form cssClass="form" id="systemuseradd" method="post" action="Systemuser" theme="simple" >

                        <s:hidden id="oldvalue" name="oldvalue" ></s:hidden>
                        <s:url var="addurl" action="viewpopupSystemuser"/>
                        <s:url var="updateurl" action="UpdateSystemuser"/>

                        <div class="row row_1"> 
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Username</label>
                                    <s:textfield name="username" id="username" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onkeypress="return alpha(event)"/> 
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Full Name</label>
                                    <s:textfield name="fullname" id="fullname" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onkeypress="return alpha(event)"/> 
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Service ID</label>
                                    <s:textfield name="serviceid" id="serviceid" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g, ''))"/> 
                                </div>
                            </div>

                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >NIC</label>
                                    <s:textfield name="nic" id="nic" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onkeypress="return alpha(event)"/> 
                                </div>
                            </div>
                        </div>
                        <div class="row row_1"> 
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Email</label>
                                    <s:textfield name="email" id="email" cssClass="form-control" maxLength="128"  onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, '' .))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''.))"/> 
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Contact Number</label>
                                    <s:textfield name="contactno" id="contactno"  maxLength="11" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" onkeypress="return isNumberserch(event)"/>
                                </div>
                            </div>
                            <!--                            <div class="col-sm-3">
                                                            <div class="form-group">
                                                                <label >User Role</label>
                            <%--<s:select  id="userrole" list="%{userroleList}"  name="userrole" headerKey="" headerValue="--Select User Role--" listKey="userrolecode" listValue="description" cssClass="form-control"/>--%>
                        </div>
                    </div>-->
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Status</label>
                                    <s:select  id="status" list="%{statusList}"  name="status" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description" value="%{status}" disabled="false" cssClass="form-control"/>
                                </div>
                            </div>

                            <!--                                        <div class="col-sm-2">
                                                                        <div class="form-group form-inline">
                                                                            <label >Expiry Date</label>
                                                                            <!s:textfield name="expirydate" id="expirydate" cssClass="form-control"/>
                                                                        </div>
                                                                    </div>    -->
                        </div> 

                        <div class="row row_1 form-inline">
                            <div class="col-sm-4">
                                <div class="form-group">                                                   
                                    <sj:submit 
                                        button="true"
                                        value="Search" 
                                        href="#"
                                        onClickTopics="onclicksearch"  
                                        targets="message"
                                        id="searchbut"
                                        disabled="#vsearch"
                                        cssClass="form-control btn_normal"
                                        cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                        />
                                </div>
                                <div class="form-group">                               
                                    <sj:submit 
                                        button="true" 
                                        value="Reset" 
                                        name="reset" 
                                        onClick="resetAllData()"
                                        cssClass="form-control btn_normal"
                                        cssStyle="border-radius: 12px;"
                                        /> 
                                </div>
                            </div>

                            <div class="col-sm-5"></div>
                            <div class="col-sm-3 text-right">
                                <div class="form-group">
                                    <sj:submit 
                                        openDialog="remotedialog"
                                        button="true"
                                        href="%{addurl}"
                                        disabled="#vadd"
                                        value="Add System User"
                                        id="addButton_new"
                                        targets="remotedialog"   
                                        cssClass="form-control btn_normal"
                                        cssStyle="border-radius: 12px;background-color:#969595;color:white;"
                                        />
                                </div>
                            </div>

                        </div> 
                    </s:form> 

                </div>
                <!--end newly changed-->             
                <sj:dialog 
                    id="deletedialog" 
                    buttons="{ 
                    'OK':function() { deleteSystemuser($(this).data('keyval'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );} 
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Delete System User"                            
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
                    title="Update System User"
                    onOpenTopics="openviewtasktopage" 
                    loadingText="Loading .."
                    width="900"
                    height="450"
                    dialogClass= "dialogclass"
                    />  

                <sj:dialog                                     
                    id="changepwddialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    title="System User Change Password"
                    onOpenTopics="openpwdchangetopage" 
                    loadingText="Loading .."
                    position="center"                            
                    width="900"
                    height="450"
                    dialogClass= "dialogclass"
                    /> 

                <sj:dialog                                     
                    id="remotedialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    title="Add System User"                            
                    loadingText="Loading .."                            
                    position="center"                            
                    width="900"
                    height="500"
                    dialogClass= "dialogclass"
                    />
            </div>
            <div class="tb-table">
                <s:url var="listurl" action="listSystemuser"/>

                <sjg:grid
                    id="gridtable"
                    caption="System User"
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
                    shrinkToFit="false"
                    >
                    <sjg:gridColumn name="username" index="username" title="Edit" width="35" align="center" formatter="editformatter" hidden="#vupdatelink" frozen="true"/>
                    <sjg:gridColumn name="username" index="username" title="Delete" width="60" align="center" formatter="deleteformatter" hidden="#vdelete" frozen="true"/>
                    <sjg:gridColumn name="username" index="username" title="Change Password"  align="center" formatter="changepasswordformatter" hidden="#vpasswordreset" frozen="true"/>
                    <sjg:gridColumn name="username" index="username" title="Username"  sortable="true" frozen="true"/>
                    <sjg:gridColumn name="fullname" index="fullname" title="Full Name"  sortable="true"/>
                    <sjg:gridColumn name="nic" index="nic" title="NIC"  sortable="true"/>
                    <sjg:gridColumn name="userrole" index="userrole.description" title="User Role"  sortable="true"/>

                    <sjg:gridColumn name="contactNo" index="mobile" title="Contact No"  sortable="true"/>
                    <sjg:gridColumn name="email" index="email" title="Email"  sortable="true"/>
                    <sjg:gridColumn name="serviceId" index="empid" title="Service ID"  sortable="true"/>

                    <sjg:gridColumn name="status" index="status.description" title="Status"  sortable="true"/>
                    <sjg:gridColumn name="createtime" index="u.createtime" title="Created Time"  sortable="true" />

                </sjg:grid> 
            </div>
            <div class="tb-other">Other</div>  
        </div>
    </body>
</html>
