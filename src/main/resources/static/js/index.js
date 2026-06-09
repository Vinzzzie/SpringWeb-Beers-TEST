const beersAvailable = document.getElementById('beers-available');
const navBiermandje = document.getElementById('nav-biermandje');

async function fetchAantalBieren() {
    return await fetch('/bieren/aantal', {method: 'GET'})
        .then(response => {
            let body = response.json();
            if (!response.ok) {
                console.warn("Server response", body);
            } else {
                return body;
            }
        }).catch(console.error);
}

async function biermandjeExists() {
    return null === sessionStorage.getItem("biermandje");
}

window.onload = async () => {
    beersAvailable.textContent = await fetchAantalBieren();
    navBiermandje.hidden = await biermandjeExists();
}