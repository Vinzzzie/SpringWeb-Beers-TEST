const bierNaam = document.getElementById('bier-naam');
const bierAlcohol = document.getElementById('bier-alcohol');
const bierPrijs = document.getElementById('bier-prijs');
const addBier = document.getElementById('add-bier');
const terugButton = document.getElementById('terug-button');
const navBiermandje = document.getElementById('nav-biermandje');
const dangerAlert = document.getElementById('danger-alert');
const infoAlert = document.getElementById('info-alert');

async function voegToe(bier) {
    const biermandje = null===sessionStorage.getItem('biermandje') ? [] :
        JSON.parse(sessionStorage.getItem('biermandje'));
    const hasBier = biermandje.some(b => b.id===bier.id);
    if (!hasBier) {
        biermandje.push(bier);
        infoAlert.hidden = false;
    } else {
        dangerAlert.hidden = false;
    }
    sessionStorage.setItem("biermandje", JSON.stringify(biermandje));

}

async function biermandjeExists() {
    return null === sessionStorage.getItem("biermandje");
}

addBier.addEventListener('click', async (e )=> {
    e.preventDefault();
    const bier = JSON.parse(sessionStorage.getItem('bier'));
    await voegToe(bier);
    navBiermandje.hidden = await biermandjeExists();
});

terugButton.addEventListener('click', (e) => {
    e.preventDefault();
    window.location.href = '/bierlijst.html';
});

window.onload = async () => {
    const bier = JSON.parse(sessionStorage.getItem('bier'));
    bierNaam.textContent = bier.naam;
    bierAlcohol.textContent = bier.alcohol + '%vol.';
    bierPrijs.textContent = '€' + bier.prijs;
    navBiermandje.hidden = await biermandjeExists();
}