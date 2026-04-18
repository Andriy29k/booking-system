const API = "http://localhost:8080/api/resources";

async function loadResources() {
    const res = await fetch(API);
    const data = await res.json();

    const container = document.getElementById("resources-list");
    container.innerHTML = "";

    data.forEach(resource => {
        const card = document.createElement("div");
        card.className = "card";

        card.innerHTML = `
            <div class="title">${resource.title}</div>
            <div class="description">${resource.description}</div>
            <div class="price">$${resource.pricePerDay} / day</div>
            <button class="button">Book</button>
        `;

        container.appendChild(card);
    });
}

loadResources();