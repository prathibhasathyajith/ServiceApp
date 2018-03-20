<%-- 
    Document   : navbar
    Created on : Dec 26, 2017, 9:00:00 AM
    Author     : prathibha_s
--%>
<!--<div class="nav-bar">
    <span>Main Menu</span>
<%--<% if (session.getAttribute("SYSTEMUSERTYPE").equals("admin")) {%> --%>
<ul>
    <li><a id="nav1" href="#">${SYSTEMUSERTYPE}</a></li>
    <li><a id="nav2" href="${pageContext.request.contextPath}/viewParty.action">Party Management</a></li>
    <li><a id="nav3" href="${pageContext.request.contextPath}/viewPartyLA.action">Party-Local Authority Management</a></li>
    <li><a id="nav4" href="${pageContext.request.contextPath}/viewCandidate.action">Candidate Management</a></li>
    <li><a id="nav5" href="${pageContext.request.contextPath}/viewCandidateList.action">Candidate List Management</a></li>
    <li><a id="nav6" href="${pageContext.request.contextPath}/viewService.action">Service List Management</a></li>
    <li><a id="nav7" href="${pageContext.request.contextPath}/viewVotingSummary.action">Voting Summary</a></li>
    <li><a id="nav8" href="#about">About</a></li>
</ul>
</div>-->
<aside class="sidebar-left-collapse">
    <div class="sidebar-links">
        <div id="link_1" class="link-default">
            <a href="#">User Management</a>
            <ul class="sub-links">
                <li id="sublink_1"><a href="${pageContext.request.contextPath}/viewSystemuser.action">System User</a></li>
                <li id="sublink_2"><a href="#">Landscape</a></li>
                <li id="sublink_3"><a href="#">Studio shots</a></li>
                <li id="sublink_4"><a href="#">Macros</a></li>
            </ul>
        </div>

        <div id="link_2" class="link-default">
            <a href="#">Favorites</a>

            <ul class="sub-links">
                <li id="sublink_5"><a href="#">Link 1</a></li>
                <li id="sublink_6"><a href="#">Link 2</a></li>
                <li id="sublink_7"><a href="#">Link 3</a></li>
                <li id="sublink_8"><a href="#">Link 4</a></li>
            </ul>
        </div>
        <div id="link_3" class="link-default">
            <a href="#">Projects</a>

            <ul class="sub-links">
                <li id="sublink_9"><a href="#">Link 1</a></li>
                <li id="sublink_10"><a href="#">Link 3</a></li>
                <li id="sublink_11"><a href="#">Link 3</a></li>
                <li id="sublink_12"><a href="#">Link 3</a></li>
                <li id="sublink_13"><a href="#">Link 4</a></li>
                <li id="sublink_14"><a href="#">Link 4</a></li>
                <li id="sublink_15"><a href="#">Link 4</a></li>
                <li id="sublink_16"><a href="#">Link 4</a></li>

            </ul>
        </div>
        <div id="link_4" class="link-default">
            <a href="#">Places</a>

            <ul class="sub-links">
                <li id="sublink_17"><a href="#">Link 1</a></li>
                <li id="sublink_18"><a href="#">Link 2</a></li>
                <li id="sublink_19"><a href="#">Link 4</a></li>
                <li id="sublink_20"><a href="#">Link 3</a></li>
                <li id="sublink_21"><a href="#">Link 3</a></li>

            </ul>
        </div>
        <div id="link_5" class="link-default">
            <a href="#">test</a>

            <ul class="sub-links">
                <li id="sublink_22"><a href="#">Link 1</a></li>
                <li id="sublink_23"><a href="#">Link 2</a></li>
                <li id="sublink_24"><a href="#">Link 4</a></li>
                <li id="sublink_25"><a href="#">Link 3</a></li>
            </ul>
        </div>
    </div>

</aside>
