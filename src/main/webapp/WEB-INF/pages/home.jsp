<%-- 
    Document   : home
    Created on : Dec 25, 2017, 10:04:51 AM
    Author     : prathibha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Project</title>
        <%@include file="/stylelinks.jspf" %>
        <script src="${pageContext.request.contextPath}/resources/highcharts/highcharts.js"></script>
        <script src="${pageContext.request.contextPath}/resources/highcharts/series-label.js"></script>
        <script src="${pageContext.request.contextPath}/resources/highcharts/exporting.js"></script>
        <script src="${pageContext.request.contextPath}/resources/highcharts/export-data.js"></script>
        <script src="${pageContext.request.contextPath}/resources/highcharts/theme1.js"></script>
        <style>
            .chartCss {
                width: 100%;
                height: 300px;
                margin: 0 auto
            }
            .tb-chart-body{
                margin: 10px;
                padding: 10px;
            }
            .tb-chat-box{
                float: left;
                width: 50%;
                /*padding: 10px;*/
                height: auto;
                border: 1px solid white;
                background: linear-gradient(to right, #2d2d2e 0%,#363638 100%);
            }
            .chart-body-title{
                text-align: center;
                text-decoration: underline;
            }
            .chart-body-date{
                text-align: center;
                font-size: 14px;
                font-weight: 600;
                color: #1f4f91;
                font-family: Raleway;
            }
        </style>
    </head>
    <body>
        <!--header-->
        <jsp:include page="/header.jsp"/>
        <!--nav bar-->
        <jsp:include page="/navbar.jsp"/>
        <!--body content-->
        <div class="tb-body f-right tb-header-text">
            <div class="chart-body-title">Requests Summary</div>
            <div class="chart-body-date">For apr , may , jun months </div>
            <div class="tb-chart-body">
                <div class="tb-chat-box">
                    <div id="containerAll" class="chartCss"></div>
                </div>
                <div class="tb-chat-box">
                    <div id="containerComp" class="chartCss"></div>
                </div>
                <div class="tb-chat-box">
                    <div id="containerCancel" class="chartCss"></div>
                </div>
                <div class="tb-chat-box">
                    <div id="containerRej" class="chartCss"></div>
                </div>
            </div>

        </div>
        <!--footer-->
        <jsp:include page="/footer.jsp"/>
        <script>


            // completed data chart
            function chartForCompleted(total, complete, monthlist) {
                Highcharts.chart('containerComp', {
                    chart: {
                        type: 'areaspline'
                    },
                    title: {
                        text: 'Completed Requests Count Monthly'
                    },
                    xAxis: {
                        categories: monthlist

                    },
                    yAxis: {
                        title: {
                            text: 'Requests Count'
                        }
                    },
                    tooltip: {
                        shared: true,
                        valueSuffix: ''
                    },
                    credits: {
                        enabled: false
                    },
                    plotOptions: {
                        areaspline: {
                            fillOpacity: 0.6
                        }
                    },
                    exporting: {
                        enabled: false
                    },
                    series: [{
                            name: 'All requests',
                            data: total
                        }, {
                            name: 'Completed requests',
                            data: complete
                        }]
                });

            }

            function chartForAll(total, monthlist) {
                Highcharts.setOptions(Highcharts.theme1);
                Highcharts.chart('containerAll', {
                    chart: {
                        type: 'areaspline'
                    },
                    title: {
                        text: 'All Requests Count Monthly'
                    },
                    xAxis: {
                        categories: monthlist

                    },
                    yAxis: {
                        title: {
                            text: 'Requests Count'
                        }
                    },
                    tooltip: {
                        shared: true,
                        valueSuffix: ''
                    },
                    credits: {
                        enabled: false
                    },
                    plotOptions: {
                        areaspline: {
                            fillOpacity: 0.6
                        }
                    },
                    exporting: {
                        enabled: false
                    },
                    series: [{
                            name: 'All requests',
                            data: total
                        }]
                });
            }

            function chartForCancel(total, cancel, monthlist) {
                Highcharts.chart('containerCancel', {
                    chart: {
                        type: 'areaspline'
                    },
                    title: {
                        text: 'Canceled Requests Count Monthly (Bass and Customer)'
                    },

                    xAxis: {
                        categories: monthlist

                    },
                    yAxis: {
                        title: {
                            text: 'Requests Count'
                        }
                    },
                    tooltip: {
                        shared: true,
                        valueSuffix: ''
                    },
                    credits: {
                        enabled: false
                    },
                    plotOptions: {
                        areaspline: {
                            fillOpacity: 0.6
                        }
                    },
                    exporting: {
                        enabled: false
                    },
                    series: [{
                            name: 'All Requests',
                            data: total
                        }, {
                            name: 'Cancel requests',
                            data: cancel
                        }]
                });

            }

            function chartForRej(total, rej, monthlist) {
                Highcharts.chart('containerRej', {
                    chart: {
                        type: 'areaspline'
                    },
                    title: {
                        text: 'Rejected Requests Count Monthly (Bass and Customer)'
                    },

                    xAxis: {
                        categories: monthlist

                    },
                    yAxis: {
                        title: {
                            text: 'Requests Count'
                        }
                    },
                    tooltip: {
                        shared: true,
                        valueSuffix: ''
                    },
                    credits: {
                        enabled: false
                    },
                    plotOptions: {
                        areaspline: {
                            fillOpacity: 0.6
                        }
                    },
                    exporting: {
                        enabled: false
                    },
                    series: [{
                            name: 'All Requests',
                            data: total
                        }, {
                            name: 'Rejected requests',
                            data: rej
                        }]
                });

            }

            function getToday() {
                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth() + 1; //January is 0!
                var yyyy = today.getFullYear();

                if (dd < 10) {
                    dd = '0' + dd;
                }

                if (mm < 10) {
                    mm = '0' + mm;
                }

                today = mm + '/' + dd + '/' + yyyy;
                return yyyy + '-' + mm;
            }

            // get all details
            summaryReport();


            function summaryReport() {

                var month = getToday();

                var monthSplit = month.split("-");
                var monthNum = Number(monthSplit[1]);
                var monthPlus;
                var monthStart;
                var yearStart;
                var startDate;

                if (1 <= monthNum && monthNum < 12) {
                    monthPlus = monthNum + 1;
                    if (monthPlus < 10) {
                        monthPlus = Number(monthSplit[0]) + "-0" + monthPlus;
                    } else {
                        monthPlus = Number(monthSplit[0]) + "-" + monthPlus;
                    }
                } else if (monthNum === 12) {
                    monthPlus = Number(monthSplit[0]) + 1 + "-01";
                }

                monthStart = monthNum - 6;

                var sign = Math.sign(monthStart);

                if (sign === 0 || sign < 0) {
                    yearStart = Number(monthSplit[0]) - 1;
                    monthStart = monthStart + 12;
                    if (monthStart < 10) {
                        monthStart = "0" + monthStart;
                    }
                } else if (sign > 0) {
                    yearStart = Number(monthSplit[0]);
                    if (monthStart < 10) {
                        monthStart = "0" + monthStart;
                    }
                }
                
                startDate = yearStart+"-"+monthStart;

               
                $.ajax({
                    url: '${pageContext.request.contextPath}/chartdataServiceRequest',
                    data: {month: month, monthPlus: monthPlus, monthStart:startDate},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        // complete
                        var total = [];
                        var complete = [];
                        var monthlist = [];
                        var cancel = [];
                        var reject = [];
                        var monthText = "For ";
                        
                        $.each(data.chartBean, function (index, item) {
                            total.push(Number(item.totalReq));
                            complete.push(Number(item.completedReq));
                            cancel.push(Number(item.cancelAll));
                            reject.push(Number(item.rejAll));
                            monthlist.push(item.monthDes);
                            monthText += item.monthDes + ",";
                        });

                        $(".chart-body-date").text(monthText + " months");

                        console.log(total);
                        console.log(complete);
                        console.log(monthlist);

                        chartForAll(total, monthlist);
                        chartForCompleted(total, complete, monthlist);
                        chartForCancel(total, cancel, monthlist);
                        chartForRej(total, reject, monthlist);


                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

        </script>
    </body>
</html>
