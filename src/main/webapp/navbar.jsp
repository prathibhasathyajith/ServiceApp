<%-- 
    Document   : navbar
    Created on : Dec 26, 2017, 9:00:00 AM
    Author     : prathibha_s
--%>
<div class="nav-bar">
    <span>Main Menu</span>
    <%--<% if (session.getAttribute("SYSTEMUSERTYPE").equals("admin")) {%> --%>
    <ul>
        <!--<li><a id="nav1" href="#">${SYSTEMUSERTYPE}</a></li>-->
        <li><a id="nav2" href="${pageContext.request.contextPath}/viewParty.action">Party Management</a></li>
        <li><a id="nav3" href="${pageContext.request.contextPath}/viewPartyLA.action">Party-Local Authority Management</a></li>
        <li><a id="nav4" href="${pageContext.request.contextPath}/viewCandidate.action">Candidate Management</a></li>
        <li><a id="nav5" href="${pageContext.request.contextPath}/viewCandidateList.action">Candidate List Management</a></li>
        <li><a id="nav6" href="${pageContext.request.contextPath}/viewService.action">Service List Management</a></li>
        <li><a id="nav7" href="${pageContext.request.contextPath}/viewVotingSummary.action">Voting Summary</a></li>
        <!--<li><a id="nav8" href="#about">About</a></li>-->
    </ul>
</div>

