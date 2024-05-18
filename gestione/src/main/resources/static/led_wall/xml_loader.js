function createXHR(url, onSuccess, onError) {
    const xhr = new XMLHttpRequest();
    xhr.onload = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200)
                onSuccess(xhr);
            else
                onError(xhr.statusText);
        }
    };
    xhr.open("GET", url);
    xhr.send();
}

function loadXML(palinsestoURL) {
    createXHR(palinsestoURL, (xhr) => {
        const xml = xhr.responseXML;
        const cartelloni = xml.getElementsByTagName('cartellone');
        const percorsi = [], folders = [], durate = [];
        Array.from(cartelloni).forEach(cartellone => {
            percorsi.push(cartellone.getElementsByTagName('percorso')[0].textContent);
            folders.push(cartellone.getElementsByTagName('folder')[0].textContent);
            durate.push(parseInt(cartellone.getElementsByTagName('durata')[0].textContent));
        });
        loadHTMLs(percorsi, folders, durate, palinsestoURL);
    }, (error) => {
        console.error("Errore durante il caricamento del palinsesto:", error);
    });
}

let currentIndex = 0;
var ref_impianto = 1; // Nel rilascio finale NON sara' cosi'!
function loadHTMLs(percorsi, folders, durate, palinsestoURL) {
    const index = currentIndex % percorsi.length;
    createXHR(percorsi[index], (xhr) => {
        let htmlContent = xhr.responseText;
        htmlContent = adjustPaths(htmlContent, folders[index]);
        document.getElementById('led-wall').innerHTML = htmlContent;
        aggiornaStato(ref_impianto, palinsestoURL.match(/\d+/)[0], folders[index], durate[index]);
        currentIndex++;
        setTimeout(() => loadHTMLs(percorsi, folders, durate, palinsestoURL), durate[index] * 1000);
    }, (error) => {
        console.error("Errore durante il caricamento del contenuto HTML:", error);
    });
}

function adjustPaths(htmlContent, folder) {
    // $1 contiene la stringa dentro le ()
    return htmlContent.replace(/src="([^"]*)"/g, `src="../cartelloni/${folder}/$1"`)
        .replace(/href="([^"]*\.css)"/g, `href="../cartelloni/${folder}/$1"`);
}

function aggiornaStato(ref_impianto, cod_palinsesto, nome_cartellone, durata_visualizzazione) {
    var params = `ref_impianto=${ref_impianto}&cod_palinsesto=${cod_palinsesto}&nome_cartellone=${nome_cartellone}&durata_visualizzazione=${durata_visualizzazione}`;
    console.log(params);
    var url = "http://localhost:8000/monitoraggio_war_exploded/insertservlet";
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                console.log("Tutto ok!");
            }
            else {
                console.log("xhr.status != 200");
            }
        }
        else {
            console.log("xhr.readyState != 4");
            console.log(xhr.readyState);
        }
    };
    xhr.send(params);
}
