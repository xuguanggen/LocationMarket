var a = 6378245.0;
var ee = 0.00669342162296594323;

var outOfChina = function(lnglat) {
    if (lnglat.getLng() < 72.004 || lnglat.getLng() > 137.8374)
        return true;
    if (lnglat.getLat() < 0.8293 || lnglat.getLat() > 55.8271)
        return true;
    return false;
}

var transformLat = function(x, y) {
    var ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
    ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
    ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0 * Math.PI)) * 2.0 / 3.0;
    ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y * Math.PI / 30.0)) * 2.0 / 3.0;
    return ret;
};

var transformLng = function(x, y) {
    var ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
    ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
    ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0 * Math.PI)) * 2.0 / 3.0;
    ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x / 30.0 * Math.PI)) * 2.0 / 3.0;
    return ret;
};

var wgs2gcj = function(lnglat) {
    if (outOfChina(lnglat))
        return lnglat;

    var wgLng = lnglat.getLng();
    var wgLat = lnglat.getLat();
    var dLng = transformLng(wgLng - 105.0, wgLat - 35.0);
    var dLat = transformLat(wgLng - 105.0, wgLat - 35.0);
    var radLat = wgLat / 180.0 * Math.PI;
    var magic = Math.sin(radLat);
    magic = 1 - ee * magic * magic;
    var  sqrtMagic = Math.sqrt(magic);
    dLng = (dLng * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);
    dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
    return new AMap.LngLat(wgLng + dLng, wgLat + dLat);
};

var gcj2wgs = function(lnglat) {
    if (outOfChina(lnglat))
        return lnglat;
    
    var c2 = wgs2gcj(lnglat);
    return new AMap.LngLat(2 * lnglat.getLng() - c2.getLng(), 2 * lnglat.getLat() - c2.getLat());
};
