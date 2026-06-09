const brouwerNaam = document.getElementById('brouwer-naam');
const bierTableBody = document.getElementById('bier-table-body');
const terugButton = document.getElementById('terug-button');
const navBiermandje = document.getElementById('nav-biermandje');

async function fetchBieren(brouwerId) {
    const uri = `/brouwers/${encodeURIComponent(brouwerId)}/bieren`;
    const url = new URL(uri, window.location.origin);
    console.log(url);
    return await fetch(url, {method: 'GET'})
        .then(response => {
            let body = response.json();
            if (!response.ok) {
                console.warn('Server response', body);
            } else {
                return body;
            }
        }).catch(console.error);
}

async function setBrouwerNaam(value) {
    brouwerNaam.textContent = value;
}

async function addToTable(bier) {
    const row = document.createElement('tr');
    row.insertCell().textContent = bier.naam;
    row.addEventListener('click', (e) => {
        e.preventDefault();
        const bierString = JSON.stringify(bier);
        sessionStorage.setItem('bier', bierString);
        window.location.href = '/bierdetails.html';
    })
    bierTableBody.appendChild(row);
}

async function biermandjeExists() {
    return null === sessionStorage.getItem("biermandje");
}

terugButton.addEventListener('click', (e) => {
    e.preventDefault();
    window.location.href = '/brouwers.html';
})

window.onload = async () => {
    const brouwer = JSON.parse(sessionStorage.getItem('brouwer'));
    await setBrouwerNaam(brouwer.naam + '(' + brouwer.gemeente + ')');
    const bieren = Object.values(await fetchBieren(brouwer.id));
    bieren.forEach(bier => addToTable(bier));
    navBiermandje.hidden = await biermandjeExists();
}