const API = "http://localhost:8080/api";

const urlParams = new URLSearchParams(window.location.search);
const resourceId = urlParams.get("id");

async function loadResource() {
    const res = await fetch(`${API}/resources/${resourceId}`);
    const data = await res.json();

    document.getElementById("title").textContent = data.title;
    document.getElementById("description").textContent = data.description;
    document.getElementById("price").textContent = `${data.pricePerDay} $ / day`;

    const imagesDiv = document.getElementById("images");
    imagesDiv.innerHTML = "";

    if (data.images) {
        data.images.forEach(img => {
            const image = document.createElement("img");
            image.src = img;
            image.style.width = "100%";
            image.style.marginTop = "10px";
            imagesDiv.appendChild(image);
        });
    }
}

async function createBooking() {
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;

    const payload = {
        resourceId: resourceId,
        startDate: startDate,
        endDate: endDate
    };

    const res = await fetch(`${API}/bookings`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
    });

    const data = await res.json();

    document.getElementById("result").textContent =
        JSON.stringify(data, null, 2);
}

loadResource();