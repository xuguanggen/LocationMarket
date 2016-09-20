var heatmap = new AMap.Map("heatmap", {
    resizeEnable: true,
    zoom: map.getZoom(),
    center: map.getCenter()
});

var heatmapLayer;
heatmap.plugin(["AMap.Heatmap"], function() {
    heatmapLayer = new AMap.Heatmap(heatmap, {
        radius: 25,
        opacity: [0, 0.8]
    });

});

var drawHeatmap = function(heatmapData, topN) {
	var topN = arguments[1] ? arguments[1] : 0;
    var overlay = map.getAllOverlays("polygon")[0].getPath();
    var southwestlng = 999.0;
    var southwestlat = 999.0;
    var northeastlng = 0.0;
    var northeastlat = 0.0;
    for (var i = 0; i < overlay.length; i++) {
        southwestlng = Math.min(southwestlng, overlay[i].getLng());
        southwestlat = Math.min(southwestlat, overlay[i].getLat());
        northeastlng = Math.max(northeastlng, overlay[i].getLng());
        northeastlat = Math.max(northeastlat, overlay[i].getLat());
    }
    var southWest = new AMap.LngLat(southwestlng, southwestlat);
    var northEast = new AMap.LngLat(northeastlng, northeastlat);
    heatmap.setBounds(new AMap.Bounds(southWest, northEast));

    var points = new Array();
    for (var i = 0; i < heatmapData.length; i++) {
        points[i] = new AMap.LngLat(
            heatmapData[i]["lng"],
            heatmapData[i]["lat"]
        );
    }
    for (var i = 0; i < points.length; i++) {
        points[i] = wgs2gcj(points[i]);
    }
    for (var i = 0; i < heatmapData.length; i++) {
        heatmapData[i]["lng"] = points[i].getLng();
        heatmapData[i]["lat"] = points[i].getLat();
    }
    if (topN === 0) {
        heatmapLayer.setDataSet({
            data: heatmapData
        });
    } else {
        heatmapLayer.setDataSet({
            data: heatmapData,
            max: topN
        });
    }
};