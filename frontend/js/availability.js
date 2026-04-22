const API = "http://localhost:8080/api";

async function loadResources() {
    const res = await fetch(`${API}/resources`);
    const data = await res.json();

    const select = document.getElementById("resourceSelect");

    data.forEach(r => {
        const option = document.createElement("option");
        option.value = r.id;
        option.textContent = r.title;
        select.appendChild(option);
    });
}

async function checkAvailability() {
    const resourceId = document.getElementById("resourceSelect").value;
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;

    if (!startDate || !endDate) {
        document.getElementById("result").textContent = "Select dates first";
        return;
    }

    const url = `${API}/resources/${resourceId}/availability?start=${startDate}&end=${endDate}`;

    try {
        const res = await fetch(url);
        const data = await res.json();

        if (data.available) {
            document.getElementById("result").textContent = "✅ Available";
            document.getElementById("result").style.color = "green";
        } else {
            document.getElementById("result").textContent = "❌ Not available";
            document.getElementById("result").style.color = "red";
        }

    } catch (err) {
        document.getElementById("result").textContent = "Error checking availability";
    }
}

loadResources();