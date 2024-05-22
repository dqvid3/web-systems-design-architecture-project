function verificaStatoImpianti() {
    $.ajax({
        url: "http://localhost:8000/monitoraggio_war_exploded/selectservlet",
        type: "GET",
        dataType: "json",
        success: function (jsonResponse) {
            console.log(jsonResponse);
            let activeCount = jsonResponse.attivi;
            let inactiveCount = jsonResponse.nonAttivi;
            let latCentro = jsonResponse.latCentro;
            let lonCentro = jsonResponse.lonCentro;
            caricaMappa(activeCount, inactiveCount, latCentro, lonCentro);
        },
        error: function (xhr, status, error) {
            let jsonResponse = JSON.parse(xhr.responseText);
            $('.errore').text("Errore durante l'esecuzione di " + jsonResponse.message + "!");
        }
    });
    $("#timestamp").text(getCurrentDateTime());
}

// https://wiki.openstreetmap.org/wiki/Openlayers_POI_layer_example
function caricaMappa(activeCount, inactiveCount, latCentro, lonCentro) {
    map = new OpenLayers.Map("mapdiv");
    map.addLayer(new OpenLayers.Layer.OSM());
    let attivi = new OpenLayers.Layer.Text("Impianti attivi", { location:"attivi.txt", projection: map.displayProjection });
    map.addLayer(attivi);
    let nonAttivi = new OpenLayers.Layer.Text("Impianti non attivi", { location:"nonAttivi.txt", projection: map.displayProjection });
    map.addLayer(nonAttivi);
    let layer_switcher= new OpenLayers.Control.LayerSwitcher({}); // Create layer switcher widget in top right corner of map.
    map.addControl(layer_switcher);
    let lonLat = new OpenLayers.LonLat(lonCentro, latCentro).transform(new OpenLayers.Projection("EPSG:4326"),
        map.getProjectionObject()); // Transform from WGS 1984 to Spherical Mercator Projection.
    let zoom = 13;
    map.setCenter (lonLat, zoom); // Set start centrepoint and zoom.
    aggiornaStato(activeCount, inactiveCount);
}

function aggiornaPagina() {
    location.reload();
}

function aggiornaStato(activeCount, inactiveCount) {
    // Si potrebbe fare anche con <canvas>...
    let pieChart = document.querySelector('.pie');
    let totalCount = activeCount + inactiveCount;
    let activePercentage = (activeCount / totalCount) * 100;
    let inactivePercentage = (inactiveCount / totalCount) * 100;
    pieChart.style.backgroundImage = "conic-gradient(green " + activePercentage + "%, red " +
        activePercentage + "%, red " + (activePercentage + inactivePercentage) +
        "%, transparent " + (activePercentage + inactivePercentage) + "%)";
    document.getElementById("attivo").innerHTML = `Impianti attivi: ${activeCount}/${totalCount}`;
    document.getElementById("nonattivo").innerHTML = `Impianti non attivi: ${inactiveCount}/${totalCount}`;
}

function getCurrentDateTime() {
    const now = new Date();
    const day = String(now.getDate()).padStart(2, '0');
    const month = String(now.getMonth() + 1).padStart(2, '0'); // Mesi in JavaScript sono indicizzati da 0 a 11
    const year = now.getFullYear();
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    return `${day}/${month}/${year} ${hours}:${minutes}:${seconds}`;
}

setInterval(aggiornaPagina, 2 * 60 * 1000);
