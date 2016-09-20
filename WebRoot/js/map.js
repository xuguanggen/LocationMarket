// http
var createXmlHttpRequest = function() {
    if (window.ActiveXObject) {
        return new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        return new XMLHttpRequest();
    }
};

var httpClient = function() {
    this.get = function(url, callback) {
        var anHttpRequest = createXmlHttpRequest();
        anHttpRequest.onreadystatechange = function() {
            if (anHttpRequest.readyState == 4 && anHttpRequest.status == 200)
                callback(anHttpRequest.responseText);
        };

        anHttpRequest.open("GET", url, true);
        anHttpRequest.send(null);
    };
};

var map = new AMap.Map('container', {
    zoom: 12,
    center: [121.468524,31.225313]
});

//var marker1 = new AMap.Marker({
//    position: [121.4125, 31.21944]
//});
//marker1.setMap(map);
//
//var point = new AMap.LngLat(121.4125, 31.21944);
//AMap.convertFrom(point, "gps", function(status, result) {
//    var marker2 = new AMap.Marker({
//        position: result.locations[0]
//    });
//    marker2.setMap(map);
//});
//// alert(point);


var drawRectangle = function() {
    map.clearMap();
    var lefttoplng = document.getElementById("lefttoplng").value;
    var lefttoplat = document.getElementById("lefttoplat").value;
    var rightbottomlng = document.getElementById("rightbottomlng").value;
    var rightbottomlat = document.getElementById("rightbottomlat").value;

    var points = [
        new AMap.LngLat(lefttoplng, lefttoplat),
        new AMap.LngLat(rightbottomlng, rightbottomlat)
    ];
    AMap.convertFrom(points, "gps", function(status, result) {
        var locations = result.locations;
        var polygon = new AMap.Polygon({
            path: [
                [locations[0].getLng(), locations[0].getLat()],
                [locations[0].getLng(), locations[1].getLat()],
                [locations[1].getLng(), locations[1].getLat()],
                [locations[1].getLng(), locations[0].getLat()]
            ],
            strokeColor: "#FF33FF",
            strokeOpacity: 0.2,
            storkeWeight: 2,
            fillColor: "#1791fc",
            fillOpacity: 0.35
        });
        polygon.setMap(map);
    });

    // var points = [
    //     [lefttoplng, lefttoplat],
    //     [lefttoplng, rightbottomlat],
    //     [rightbottomlng, rightbottomlat],
    //     [rightbottomlng, lefttoplat]
    // ];
    // var polygon = new AMap.Polygon({
    //     path: points,
    //     strokeColor: "#FF33FF",
    //     strokeOpacity: 0.2,
    //     storkeWeight: 2,
    //     fillColor: "#1791fc",
    //     fillOpacity: 0.35
    // });
    // polygon.setMap(map);
};

var _onMousedown = function(e) {
    map.clearMap();
};

var _onMouseup = function(e) {
    if (map.getAllOverlays("polygon").length === 0) {
        return;
    }

    var overlay = map.getAllOverlays("polygon")[0].getPath();
    var lefttoplng = 999.0;
    var lefttoplat = 0.0;
    var rightbottomlng = 0.0;
    var rightbottomlat = 999.0;
    for (var i = 0; i < overlay.length; i++) {
        lefttoplng = Math.min(lefttoplng, overlay[i].getLng());
        lefttoplat = Math.max(lefttoplat, overlay[i].getLat());
        rightbottomlng = Math.max(rightbottomlng, overlay[i].getLng());
        rightbottomlat = Math.min(rightbottomlat, overlay[i].getLat());
    }

    var lefttop = gcj2wgs(new AMap.LngLat(lefttoplng, lefttoplat));
    var rightbottom = gcj2wgs(new AMap.LngLat(rightbottomlng, rightbottomlat));
    document.getElementById("lefttoplng").value = lefttop.getLng();
    document.getElementById("lefttoplat").value = lefttop.getLat();
    document.getElementById("rightbottomlng").value = rightbottom.getLng();
    document.getElementById("rightbottomlat").value = rightbottom.getLat();


    // var httpRequest = createXmlHttpRequest();
    // var syncPosition = function () {
    //      var result =  httpRequest.responseText;
    //      alert(result);
    //      document.getElementById("lefttoplng").value = result[0]["Lng"];
    //      document.getElementById("lefttoplat").value = result[0]["Lat"];
    //      document.getElementById("rightbottomlng").value = result[1]["Lng"];
    //      document.getElementById("rightbottomlat").value = result[1]["Lat"];
    // };
    // var url = "http://api.zdoz.net/transmore.ashx";
    // var args = "type=2&lngs="+lefttoplng+";"+rightbottomlng+
    //     "&lats="+lefttoplat+";"+rightbottomlat;

    // httpRequest.onreadystatechange = syncPosition;
    // httpRequest.open("POST", url, true);
    // httpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    // httpRequest.send(args);

    // document.getElementById("lefttoplng").value = lefttoplng;
    // document.getElementById("lefttoplat").value = lefttoplat;
    // document.getElementById("rightbottomlng").value = rightbottomlng;
    // document.getElementById("rightbottomlat").value = rightbottomlat;
};

var _onMousemove = function(e) {
    _onMouseup();
};

//var button1 = document.getElementById("search");
//var listener1 = AMap.event.addDomListener(button1, "click", drawRectangle);

var mouseTool = new AMap.MouseTool(map);




/*AMap.event.addDomListener(document.getElementById("bt1"), "click", function() {
    map.setDefaultCursor("default");
    map.on("mousedown", _onMousedown);
    map.on("mousemove", _onMousemove);
    map.on("mouseup", _onMouseup);
    mouseTool.rectangle({
        strokeColor: "#FF33FF",
        strokeOpacity: 0.2,
        storkeWeight: 2,
        fillColor: "#1791fc",
        fillOpacity: 0.35,
        cursor: "url(http://webapi.amap.com/theme/v1.3/openhand.cur),default"
    });
});
AMap.event.addDomListener(document.getElementById("bt2"), "click", function() {
    map.setDefaultCursor("url(http://webapi.amap.com/theme/v1.3/openhand.cur),default");
    map.off("mousedown", _onMousedown);
    map.off("mousemove", _onMousemove);
    map.off("mouseup", _onMouseup);
    mouseTool.close();
}); */

