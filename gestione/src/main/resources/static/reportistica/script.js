function setDateRange(range) {
    const now = new Date();
    let startDate, endDate;
    let tzoffset = (new Date()).getTimezoneOffset() * 60000;

    switch (range) {
        case 'Oggi':
            startDate = new Date(now.setHours(0, 0, 0, 0));
            endDate = new Date(now.setHours(23, 59, 59, 999));
            break;
        case 'Ieri':
            startDate = new Date(now.setDate(now.getDate() - 1));
            startDate.setHours(0, 0, 0, 0);
            endDate = new Date(now.setHours(23, 59, 59, 999));
            break;
        case 'Settimana corrente':
            const dayOfWeek = now.getDay();
            startDate = new Date(now.setDate(now.getDate() - dayOfWeek + (dayOfWeek === 0 ? -6 : 1)));
            startDate.setHours(0, 0, 0, 0);
            endDate = new Date(now.setDate(now.getDate() + 6));
            endDate.setHours(23, 59, 59, 999);
            break;
        case 'Mese corrente':
            startDate = new Date(now.getFullYear(), now.getMonth(), 1);
            endDate = new Date(now.getFullYear(), now.getMonth() + 1, 0, 23, 59, 59, 999);
            break;
        case 'Anno corrente':
            startDate = new Date(now.getFullYear(), 0, 1);
            endDate = new Date(now.getFullYear(), 11, 31, 23, 59, 59, 999);
            break;
        case 'custom':
            document.getElementById('customDates').style.display = 'flex';
            return;
        default:
            startDate = new Date();
            endDate = new Date();
    }
    console.log(startDate);
    console.log(endDate);
    document.getElementById('startDate').value = new Date(startDate - tzoffset).toISOString().slice(0, 16);
    document.getElementById('endDate').value = new Date(endDate - tzoffset).toISOString().slice(0, 16);
    document.getElementById('customDates').style.display = 'none';
}

window.onload = function () {
    setDateRange(document.getElementById('dateRange').value)
    console.log(document.getElementById('startDate').value, document.getElementById('endDate').value)
}

function setDefaultParams() {
    document.getElementById('dateRange').value = 'Oggi';
    setDateRange('Oggi');
    document.getElementById('cartelloneName').value = '%';
    document.getElementById('operator').value = 'COUNT';
    document.getElementById('sortOrder').value = 'ASC';
    document.getElementById('minViews').value = 0;
    document.getElementById('limit').value = 1000;
}

function updateIcon() {
    let formato = document.getElementById('formato').value;
    document.getElementById('icon').src = "imgs/reportistica/icon_" + formato + ".svg"
}
