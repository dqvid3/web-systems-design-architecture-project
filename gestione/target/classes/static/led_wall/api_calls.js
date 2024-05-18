function updateTime() {
    const currentTime = new Date();
    let [day, month, year] = currentTime.toLocaleDateString("default", { day: "2-digit", month: "2-digit", year: "numeric" }).split('/');
    document.getElementById('date').innerText = `${day}/${month}/${year}`;
    document.getElementById('time').innerText = currentTime.toLocaleTimeString();
    setTimeout(updateTime, 1000);
}

function getWeatherIconCode(weatherCode) {
    console.log(weatherCode)
    //https://www.nodc.noaa.gov/archive/arc0021/0002199/1.1/data/0-data/HTML/WMO-CODE/WMO4677.HTM
    if (weatherCode === 0 || weatherCode === 1 ) return '../imgs/meteo/sole.png';
    if (weatherCode === 2 || weatherCode === 3) return '../imgs/meteo/nuvoloso.png';
    if ((weatherCode > 19 && weatherCode < 28) || (weatherCode >= 50 && weatherCode <= 69)) return '../imgs/meteo/pioggia.png';
    if (weatherCode === 29 || (weatherCode >= 91 && weatherCode <= 99)) return '../imgs/meteo/temporale.png';
    if (weatherCode >= 70  && weatherCode <= 79) return '../imgs/meteo/neve.png';
    return '../imgs/meteo/sole.png';
}

function updateMeteo() {
    const url = 'https://api.open-meteo.com/v1/forecast?latitude=38.12&longitude=13.36&current=temperature_2m,relative_humidity_2m,is_day,weather_code,surface_pressure,wind_speed_10m&timezone=auto&forecast_days=1';
    createXHR(url, (xhr) => {
        const weatherData = JSON.parse(xhr.responseText)
        const { temperature_2m, wind_speed_10m, relative_humidity_2m, weather_code } = weatherData.current;
        const { temperature_2m: tempUnit, wind_speed_10m: windUnit, relative_humidity_2m: humidityUnit } = weatherData.current_units;
        document.getElementById('temp').innerText = `${temperature_2m} ${tempUnit}`;
        document.getElementById('vento').innerText = `${wind_speed_10m} ${windUnit}`;
        document.getElementById('umidita').innerText = `${relative_humidity_2m} ${humidityUnit}`;
        document.getElementById('stato').src = getWeatherIconCode(weather_code);
    }, (error) => {
        console.error("Errore nel caricamento del meteo:", error);
    });
    setTimeout(updateMeteo, 15 * 60 * 1000);
}

function updateNews() {
    const url = 'https://newsapi.org/v2/top-headlines?sources=google-news-it&apiKey=f820844ed39c45a39a1e1fcc631eb872';
    createXHR(url, (xhr) => {
        const newsData = JSON.parse(xhr.responseText)
        const articles = newsData.articles;
        const notizieContainer = document.getElementById('notizie');
        articles.forEach(article => {
            notizieContainer.innerHTML += `<span style="color:darkcyan">${article.author}</span> - ${article.title} | `;
        });
        const styleElement = document.getElementById('scrollAnimation');
        notizieContainer.style.width = `${notizieContainer.scrollWidth}px`;
        styleElement.sheet.cssRules[0].style.animationDuration = `${notizieContainer.scrollWidth / 120}s`;
    }, (error) => {
        console.error("Errore nel caricamento delle notizie:", error);
    });
    setTimeout(updateNews, 15 * 60 * 1000);
}