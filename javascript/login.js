const BASE_URL = "http://192.168.1.75:8080";

async function login() {
  const username = document.querySelector('#username').value;
  const password = document.querySelector('#pass').value;

  const response = await fetch(`${BASE_URL}/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ username, password })
  });

  const data = await response.json();
  localStorage.setItem("token", data.token);
  localStorage.setItem("role", data.role);

  if(data.role === "ROLE_ADMIN"){
    window.location.href = "/pages/dashboard.html";
  }else if(data.role === "ROLE_CASHIER"){
    window.location.href = ("/pages/cash.html")
  }else{
    window.alert("Unknown Role");
  }

  alert("Login success");
}