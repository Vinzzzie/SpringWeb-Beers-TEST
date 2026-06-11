const dangerAlert = document.getElementById('danger-alert');
const infoAlert = document.getElementById('info-alert');
const bestelIdLabel = document.getElementById("bestel-id");
const navBiermandje = document.getElementById('nav-biermandje');

async function biermandjeExists() {
    return null === sessionStorage.getItem("biermandje");
}

window.onload = async () => {
    const bestelId = sessionStorage.getItem('bestelId');
    infoAlert.hidden = false;
    bestelIdLabel.textContent = bestelId;
    navBiermandje.hidden = await biermandjeExists();
}