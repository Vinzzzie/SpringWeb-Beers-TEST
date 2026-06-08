const brouwerLijst = document.getElementById('brouwer-lijst');

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

async function addToList(brouwer) {
    const item = document.createElement('li');
    item.classList.add('li-hover');
    item.textContent = brouwer.naam + '' +brouwer.gemeente;
    item.addEventListener('click', (e) => {
        e.preventDefault();

        console.log(brouwer);
    })
    brouwerLijst.appendChild(item);
}

window.onload = async () => {
    const brouwers = await fetchBrouwers();
    brouwers.forEach(brouwer => addToList(brouwer));
}