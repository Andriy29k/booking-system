const API = "http://localhost:8080/api";

// тимчасово (поки без JWT)
const userId = 1;

async function loadBookings() {
    const res = await fetch(`${API}/bookings/user/${userId}`);
    const data = await res.json();

    const container = document.getElementById("bookings-list");
    container.innerHTML = "";

    data.forEach(b => {
        const card = document.createElement("div");
        card.className = "card";

        card.innerHTML = `
            <div class="title">${b.resource.title}</div>

            <div class="description">
                ${b.startDate} → ${b.endDate}
            </div>

            <div class="price">
                ${b.totalPrice} $
            </div>

            <div>
                Status: <b>${b.status}</b>
            </div>

            <button class="button" onclick="cancelBooking(${b.id})">
                Cancel
            </button>
        `;

        container.appendChild(card);
    });
}

async function cancelBooking(id) {
    const res = await fetch(`${API}/bookings/${id}`, {
        method: "DELETE"
    });

    if (res.ok) {
        loadBookings();
    } else {
        alert("Failed to cancel booking");
    }
}

loadBookings();