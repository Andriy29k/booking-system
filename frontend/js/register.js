const API = "http://localhost:8080/api";

async function registerUser() {
    const payload = {
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };

    try {
        const res = await fetch(`${API}/auth/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (!res.ok) {
            throw new Error("Registration failed");
        }

        const data = await res.json();

        document.getElementById("result").textContent =
            "✅ Account created successfully";

        console.log(data);

    } catch (err) {
        document.getElementById("result").textContent =
            "❌ Error during registration";
    }
}