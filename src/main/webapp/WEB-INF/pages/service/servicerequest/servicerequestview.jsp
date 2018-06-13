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
            <script type="text/javascript">

                var lat;
                var lon;

                function loadLatLng() {

                    lon = $("#longitude").text();
                    lat = $("#latitude").text();
                    
//                    initMap(lat,lon);
                    initMap();

                    dialog = $("#dialog").dialog({
                        height: 530,
                        width: 700,
                        modal: true,
                        position: {my: "center center", at: "center center"},
                        open: function (event, ui) {
//                                $(".ui-dialog-titlebar-close", ui.dialog | ui).text("X");
//                                $(".ui-dialog-titlebar").hide();

                        },
                        close: function () {
                            dialog.dialog('destroy');
                        }
                    });
                }
            </script>
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
                                    <label >Customer Latitude</label>
                                    <s:label style="margin-bottom: 0px;" id="latitude"  value="%{serReq.latitude}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Customer Longitude</label>
                                    <s:label style="margin-bottom: 0px;" id="longitude" value="%{serReq.longitude}" cssClass="form-control"/>
                                </div>  
                            </div>
                            <div class="col-sm-1">
                                <div class="form-group">
                                    <label >To map</label>
                                    <label class="form-label" onclick="loadLatLng()"><i class="material-icons">place</i></label>
                                </div>
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Status</label>
                                    <s:label style="margin-bottom: 0px;"  value="%{serReq.status}" cssClass="form-control"/>
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

        <div id="dialog" title="Map Positions" style="display: none;background-color: #dcdcdc;margin: 0px;padding: 0">
            <div>
                <style>
                    #map {
                        height: 420px;
                        width: 95%;
                        margin-top: 2%;
                        margin-left: 2%;
                        border: 1px solid #8e8e8e;

                    }
                </style>
                <div id="map"></div>
                <script>

                    function initMap() {
                        var map = new google.maps.Map(document.getElementById('map'), {
                            zoom: 8,
                            center: {lat: 7.8731, lng: 80.7718}
                        });
                        setMarkers(map, lat, lon);
                    }
                    function setMarkers(map, lat, lon) {

                        var image = {
                            url: '${pageContext.request.contextPath}/resources/images/marker4.png',
                            // This marker is 20 pixels wide by 32 pixels high.
                            size: new google.maps.Size(20, 32),
                            // The origin for this image is (0, 0).
                            origin: new google.maps.Point(0, 0),
                            // The anchor for this image is the base of the flagpole at (0, 32).
                            anchor: new google.maps.Point(0, 32)
                        };
                        // Shapes define the clickable region of the icon. The type defines an HTML
                        // <area> element 'poly' which traces out a polygon as a series of X,Y points.
                        // The final coordinate closes the poly by connecting to the first coordinate.
                        var shape = {
                            coords: [1, 1, 1, 20, 18, 20, 18, 1],
                            type: 'poly'
                        };

                        var marker = new google.maps.Marker({
                            position: {lat: Number(lat), lng: Number(lon)},
                            map: map,
                            icon: image,
                            shape: shape,
                            title: "Point",
                            zIndex: 3
                        });

                    }

                </script>
            </div>
            <div class="form-group text-center" style="margin-top: 10px;"> 
                <button type="button" style="width: 100px" class="btn btn-danger btn-xs" onclick="dialog.dialog('close');">Close&ensp;<i class="fa fa-times-circle" aria-hidden="true"></i></button>
            </div>

        </div>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDurLciCEPA4JI3O0bvFCqqEGkyCWzw5p8&callback=initMap" async defer></script>
    </body>
</html>