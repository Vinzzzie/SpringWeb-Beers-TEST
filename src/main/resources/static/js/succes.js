const dangerAlert = document.getElementById('danger-alert');
const infoAlert = document.getElementById('info-alert');
const bestelIdLabel = document.getElementById("bestel-id");
const navBiermandje = document.getElementById('nav-biermandje');

window.onload = async () => {
    const bestelId = sessionStorage.getItem('bestelId');
    infoAlert.hidden = false;
    bestelIdLabel.textContent = bestelId;
    navBiermandje.hidden = null === sessionStorage.getItem("biermandje");
}