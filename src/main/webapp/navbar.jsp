<%-- 
    Document   : navbar
    Created on : Dec 26, 2017, 9:00:00 AM
    Author     : prathibha_s
--%>


<div class="tb-navbar f-left tb-header-text">
    <!--<div class="tb-navbar-menu">Menu</div>-->
    <aside class="sidebar-left-collapse">
        <div class="sidebar-links">
            <div id="link_1" class="link-default">
                <a href="#">USER MGT</a>
                <ul class="sub-links">
                    <li id="sublink_1"><a href="${pageContext.request.contextPath}/viewSystemuser.action">System User</a></li>
                </ul>
            </div>
            <div id="link_2" class="link-default">
                <a href="#">SYSTEM CONFIG</a>
                <ul class="sub-links">
                    <li id="sublink_5"><a href="${pageContext.request.contextPath}/viewPasswordPolicy.action">Password Policy</a></li>
                    <li id="sublink_6"><a href="${pageContext.request.contextPath}/viewFAQ.action">FAQ</a></li>
                    <li id="sublink_7"><a href="${pageContext.request.contextPath}/viewEmailTemplate.action">Email Template</a></li>
                    <li id="sublink_8"><a href="${pageContext.request.contextPath}/viewSMSTemplate.action">SMS Template</a></li>
                    <li id="sublink_9"><a href="${pageContext.request.contextPath}/viewTransactionType.action">Transaction Type</a></li>
                    <li id="sublink_10"><a href="${pageContext.request.contextPath}/viewTerms.action">Terms</a></li>
                    <li id="sublink_11"><a href="${pageContext.request.contextPath}/viewAppInfo.action">About us & Support</a></li>
                    <li id="sublink_12"><a href="${pageContext.request.contextPath}/viewConfigData.action">Data</a></li>
                </ul>
            </div>
            <div id="link_3" class="link-default">
                <a href="#">REPORT GEN</a>
                <ul class="sub-links">

                </ul>
            </div>
            <div id="link_4" class="link-default">
                <a href="#">SYSTEM AUDIT</a>
                <ul class="sub-links">
                    <li id="sublink_15"><a href="${pageContext.request.contextPath}/viewSystemAudit.action">System Audit</a></li>
                    <li id="sublink_16"><a href="${pageContext.request.contextPath}/viewLoginHistory.action">Mobile Login History</a></li>
                </ul>
            </div>
            <div id="link_5" class="link-default">
                <a href="#">CUSTOMER MGT</a>
                <ul class="sub-links">
                    <li id="sublink_25"><a href="${pageContext.request.contextPath}/viewCustomerMgt.action">Customer Management</a></li>
                </ul>
            </div>
        </div>
    </aside>
</div>
