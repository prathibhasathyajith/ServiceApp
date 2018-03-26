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
                    <li id="sublink_12"><a href="${pageContext.request.contextPath}/viewSystemAudit.action">System Audit</a></li>
                </ul>
            </div>
        </div>
    </aside>
</div>
