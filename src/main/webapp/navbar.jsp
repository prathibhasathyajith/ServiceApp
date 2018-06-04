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
                <a href="#">USER <span class="text-color-navbar">management</span></a>
                <ul class="sub-links">
                    <li id="sublink_1"><a href="${pageContext.request.contextPath}/viewSystemuser.action">System User</a></li>
                </ul>
            </div>
            <div id="link_2" class="link-default">
                <a href="#">SYSTEM <span class="text-color-navbar">configuration</span></a>
                <ul class="sub-links">
                    <li id="sublink_20"><a href="${pageContext.request.contextPath}/viewPasswordPolicy.action">Password Policy</a></li>
                    <li id="sublink_21"><a href="${pageContext.request.contextPath}/viewFAQ.action">FAQ</a></li>
                    <li id="sublink_22"><a href="${pageContext.request.contextPath}/viewEmailTemplate.action">Email Template</a></li>
                    <li id="sublink_23"><a href="${pageContext.request.contextPath}/viewSMSTemplate.action">SMS Template</a></li>
                    <li id="sublink_24"><a href="${pageContext.request.contextPath}/viewTransactionType.action">Transaction Type</a></li>
                    <li id="sublink_25"><a href="${pageContext.request.contextPath}/viewTerms.action">Terms</a></li>
                    <li id="sublink_26"><a href="${pageContext.request.contextPath}/viewAppInfo.action">About us & Support</a></li>
                    <li id="sublink_27"><a href="${pageContext.request.contextPath}/viewConfigData.action">Data</a></li>
                </ul>
            </div>
            <!--            <div id="link_3" class="link-default">
                            <a href="#">REPORT GEN</a>
                            <ul class="sub-links">
            
                            </ul>
                        </div>-->
            <div id="link_4" class="link-default">
                <a href="#">SYSTEM <span class="text-color-navbar">audit</span></a>
                <ul class="sub-links">
                    <li id="sublink_40"><a href="${pageContext.request.contextPath}/viewSystemAudit.action">System Audit</a></li>
                    <li id="sublink_41"><a href="${pageContext.request.contextPath}/viewLoginHistory.action">Mobile Login History</a></li>
                </ul>
            </div>
            <div id="link_5" class="link-default">
                <a href="#">CUSTOMER <span class="text-color-navbar">management</span></a>
                <ul class="sub-links">
                    <li id="sublink_50"><a href="${pageContext.request.contextPath}/viewCustomerMgt.action">Customer Management</a></li>
                    <li id="sublink_51"><a href="${pageContext.request.contextPath}/viewSuggestUser.action">Suggested User</a></li>
                    <!--<li id="sublink_27"><a href="${pageContext.request.contextPath}/viewRatingMgt.action">Rating Management</a></li>-->
                </ul>
            </div>
            <div id="link_6" class="link-default">
                <a href="#">SERVICE <span class="text-color-navbar">management</span></a>
                <ul class="sub-links">
                    <li id="sublink_60"><a href="${pageContext.request.contextPath}/viewServiceCategory.action">Service Category</a></li>
                    <li id="sublink_61"><a href="${pageContext.request.contextPath}/viewServiceReco.action">Service Recommendation</a></li>
                    <li id="sublink_62"><a href="${pageContext.request.contextPath}/viewLevelConfig.action">Level Configurations</a></li>
                </ul>
            </div>
        </div>
    </aside>
</div>
