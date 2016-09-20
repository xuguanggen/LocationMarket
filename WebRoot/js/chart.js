var config = {
    type: "pie",
    data: {
        datasets: [{
            data: [300, 50, 100],
            backgroundColor: [
                "#FF6384",
                "#36A2EB",
                "#FFCE56"
            ],
            // hoverBackgroundColor: [
            //     "#FF6384",
            //     "#36A2EB",
            //     "#FFCE56"
            // ]
        }],
        labels: [
            "Red",
            "Blue",
            "Yellow"
        ]
    },
    options: {

    }
};

var ctx = document.getElementById("chart").getContext("2d");
var oneChart = new Chart(ctx, config);


var randomColorFactor = function() {
    return Math.round(Math.random() * 255);
};

var randomColor = function(opacity) {
    return 'rgba(' + randomColorFactor() + ',' + randomColorFactor() + ',' + randomColorFactor() + ',' + (opacity || '.3') + ')';
};

var getColor = function(data) {
    var color = new Array();
    for (var i = 0; i < data.length; i++) {
        color[i] = randomColor(0.7);
    }
    return color;
};

var drawChart = function(elementId, data, labels) {
    var ctx = document.getElementById(elementId).getContext("2d");
    var config = {
        type: "pie",
        data: {
            datasets: [{
                data: data,
                backgroundColor: getColor(data)
            }],
            labels: labels
        },
        options: {}
    };
    var oneChart = new Chart(ctx, config);
};