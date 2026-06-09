const brouwerTableBody = document.getElementById('brouwer-table-body');
const terugButton = document.getElementById('terug-button');
const navBiermandje = document.getElementById('nav-biermandje');

async function fetchBrouwers() {
    return await fetch('/brouwers', {method: 'GET'})
        .then(response => {
            let body = response.json();
            if (!response.ok) {
                console.warn("Server response", body);
            } else {
                return body;
            }
        }).catch(console.error);
}

async function addToTable(brouwer) {
    const row = document.createElement('tr');
    row.insertCell().textContent = brouwer.naam;
    row.insertCell().textContent = brouwer.gemeente;
    row.addEventListener('click', async (e) => {
        e.preventDefault();
        const brouwerString = JSON.stringify(brouwer);
        sessionStorage.setItem("brouwer", brouwerString);
        window.location.href = "/bierlijst.html";
    });
    brouwerTableBody.appendChild(row);
}

async function biermandjeExists() {
    return null === sessionStorage.getItem("biermandje");
}

terugButton.addEventListener('click', (e) => {
    e.preventDefault();
    window.location.href = '/index.html';
})

window.onload = async () => {
    const brouwers = await fetchBrouwers();
    brouwers.forEach(brouwer => addToTable(brouwer));
    navBiermandje.hidden = await biermandjeExists();
}

