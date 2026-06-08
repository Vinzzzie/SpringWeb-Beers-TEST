const beersAvailable = document.getElementById('beers-available');

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

window.onload = async () => {
    beersAvailable.textContent = await fetchAantalBieren();
}