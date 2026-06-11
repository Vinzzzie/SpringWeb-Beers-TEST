const mandjeTabelBody   = document.getElementById("mandje-tabel-body");
const navBiermandje     = document.getElementById('nav-biermandje');

const bestelFormulier   = document.getElementById("bestel-formulier");
const naamInput         = document.getElementById('naam-input');
const straatInput       = document.getElementById('straat-input');
const huisnummerInput   = document.getElementById('huisnummer-input');
const postcodeInput     = document.getElementById('postcode-input');
const gemeenteInput     = document.getElementById('gemeente-input');

const terugButton = document.getElementById('terug-button');

async function getBierMandje() {
    return null === sessionStorage.getItem('biermandje') ? [] :
        JSON.parse(sessionStorage.getItem('biermandje'));
}

async function addToTable(bier) {
    const row = document.createElement("tr");
    row.insertCell().textContent = bier.naam;
    row.insertCell().textContent = "€" + bier.prijs;
    mandjeTabelBody.appendChild(row);
}

async function addTotal(total) {
    let row = document.createElement("tr");
    row.classList.add("table-info");
    const cell1 = row.insertCell();
    cell1.textContent = "Total:"
    cell1.classList.add("fs-5");
    const cell2 = row.insertCell();
    cell2.textContent = '€' + total;
    cell2.classList.add("fs-5");
    mandjeTabelBody.appendChild(row);
}

async function stuurBestelling(bestelling) {
    const uri = '/bestellingen';
    const url = new URL(uri, window.location.origin);
    return await fetch(url, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(bestelling)
    }).then(response => {
        let body = response.json();
        if (!response.ok) {
            console.warn('Server response', body);
            return null;
        } else {
            return body;
        }
    }).catch(console.error);
}

async function biermandjeExists() {
    return null === sessionStorage.getItem("biermandje");
}

bestelFormulier.addEventListener('submit', async (event) => {
    event.preventDefault();
    bestelFormulier.va
    if (!bestelFormulier.checkValidity()) {
        event.stopPropagation();
        bestelFormulier.classList.add('was-validated');
    } else {
        const biermandje = await getBierMandje();
        const requestBody = {
            'bierIds':      biermandje.map((bier) => bier.id),
            'naam':         naamInput.value,
            'straat':       straatInput.value,
            'huisnummer':   huisnummerInput.value,
            'postcode':     postcodeInput.value,
            'gemeente':     gemeenteInput.value,
        }
        bestelFormulier.reset();
        bestelFormulier.classList.remove('was-validated');
        const bestelId = await stuurBestelling(requestBody);
        if (!bestelId || bestelId === 0) {
            dangerAlert.hidden = false;
        } else {
            sessionStorage.clear();
            sessionStorage.setItem('bestelId', bestelId);
            window.location.href = "/succes.html";
        }
    }
})

terugButton.addEventListener('click', async (event) => {
    event.preventDefault();
    sessionStorage.clear();
    window.location.href = "/index.html";
})

window.onload = async () => {
    const bierMandje = await getBierMandje();
    let totaalPrijs = 0;
    bierMandje.forEach(bier => {
        totaalPrijs += bier.prijs;
        addToTable(bier);
    });
    await addTotal(totaalPrijs);
    navBiermandje.hidden = await biermandjeExists();
}