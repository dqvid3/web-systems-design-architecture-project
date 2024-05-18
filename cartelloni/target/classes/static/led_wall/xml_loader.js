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
        loadHTMLs(percorsi, folders, durate);
    }, (error) => {
        console.error("Errore durante il caricamento del palinsesto:", error);
    });
}

let currentIndex = 0;
function loadHTMLs(percorsi, folders, durate) {
    const index = currentIndex % percorsi.length;
    createXHR(percorsi[index], (xhr) => {
        let htmlContent = xhr.responseText;
        htmlContent = adjustPaths(htmlContent, folders[index]);
        document.getElementById('led-wall').innerHTML = htmlContent;
        currentIndex++;
        setTimeout(() => loadHTMLs(percorsi, folders, durate), durate[index] * 1000);
    }, (error) => {
        console.error("Errore durante il caricamento del contenuto HTML:", error);
    });
}

function adjustPaths(htmlContent, folder) {
    // $1 contiene la stringa dentro le ()
    return htmlContent.replace(/src="([^"]*)"/g, `src="../cartelloni/${folder}/$1"`)
        .replace(/href="([^"]*\.css)"/g, `href="../cartelloni/${folder}/$1"`);
}