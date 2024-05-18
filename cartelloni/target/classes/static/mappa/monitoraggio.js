/*
function verificaStatoImpianti() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8000/helloworld/helloservlet", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var jsonResponse = JSON.parse(xhr.responseText);
                console.log(jsonResponse);
                activeCount = jsonResponse.attivi;
                inactiveCount = jsonResponse.nonAttivi;
                alert(jsonResponse.latCentro);
                alert(jsonResponse.lonCentro);
                caricaMappa(activeCount, inactiveCount);
            }
            else {
                console.log("xhr.status != 200");
                document.getElementById('mapdiv').innerText = 'Errore durante il caricamento dei dati!';
            }
        }
        else {
            console.log("xhr.readyState != 4");
            console.log(xhr.readyState);
        }
    };
    xhr.send();
    document.getElementById("timestamp").innerText = getCurrentDateTime();
}
*/
function verificaStatoImpianti() {
    $.ajax({
        url: "http://localhost:8000/JakartaEE_war_exploded/hello-servlet",
        type: "GET",
        dataType: "json",
        success: function (jsonResponse) {
            console.log(jsonResponse);
            var activeCount = jsonResponse.attivi;
            var inactiveCount = jsonResponse.nonAttivi;
            alert(jsonResponse.latCentro);
            alert(jsonResponse.lonCentro);
            caricaMappa(activeCount, inactiveCount);
        },
        error: function (xhr, status, error) {
            console.log("Errore durante il caricamento dei dati: " + error);
            $('#mapdiv').text('Errore durante il caricamento dei dati!');
        }
    });
    $("#timestamp").text(getCurrentDateTime());
}

function caricaMappa(activeCount, inactiveCount) {
    map = new OpenLayers.Map("mapdiv");
    map.addLayer(new OpenLayers.Layer.OSM());
    var attivi = new OpenLayers.Layer.Text("Impianti attivi", { location:"bin/attivi.txt", projection: map.displayProjection });
    map.addLayer(attivi);
    var nonAttivi = new OpenLayers.Layer.Text("Impianti non attivi", { location:"bin/nonAttivi.txt", projection: map.displayProjection });
    map.addLayer(nonAttivi);
    var layer_switcher= new OpenLayers.Control.LayerSwitcher({}); // Create layer switcher widget in top right corner of map.
    map.addControl(layer_switcher); // calcolare media
    var lonLat = new OpenLayers.LonLat(13.366752, 38.120202).transform(new OpenLayers.Projection("EPSG:4326"),
        map.getProjectionObject()); // Transform from WGS 1984 to Spherical Mercator Projection.
    var zoom = 13;
    map.setCenter (lonLat, zoom); // Set start centrepoint and zoom.
    aggiornaStato(activeCount, inactiveCount);
    // aggiungiClickListenerImmagini(38.110202, 13.366752);
}

function aggiornaPagina() {
    // location.reload(true); Cache
    location.reload();
}

function aggiornaStato(activeCount, inactiveCount) {
    var pieChart = document.querySelector('.pie');
    var totalCount = activeCount + inactiveCount;
    var activePercentage = (activeCount / totalCount) * 100;
    var inactivePercentage = (inactiveCount / totalCount) * 100;
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
/*
function aggiungiClickListenerImmagini(lat, lon) {
    alert("siamo in aggiungi click listener");
    const images = document.querySelectorAll('img[src="green.png"], img[src="red.png"]');
    images.forEach(image => {
        alert(image);
        image.addEventListener('click', function() {
            centraMappa(lon, lat);
            alert("siamo in Centra MAppa da aggiungi click listener");
        });
    });
}

// Funzione per centrare la mappa sulle coordinate specificate
function centraMappa(lon, lat) {
    alert("siamo in Centra MAppa da centra mappa");
    const lonLat = new OpenLayers.LonLat(lon, lat).transform(
        new OpenLayers.Projection("EPSG:4326"), // dal sistema WGS 1984
        window.map.getProjectionObject() // al sistema di proiezione della mappa
    );
    window.map.setCenter(lonLat, 12); // 12 è il livello di zoom
}
*/
