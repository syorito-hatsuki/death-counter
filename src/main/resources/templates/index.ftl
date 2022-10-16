<html lang="en">
<head>
    <title>Death Counter</title>
    <script>
        setInterval(async function () {
            try {
                const response = await fetch("http://${remoteIp}:1540/${player}")
                if (response.ok) {
                    document.getElementById("death").textContent = await response.json();
                } else {
                    document.getElementById("death").textContent = response.status.toString()
                }
            } catch (e) {
                document.getElementById("death").textContent = e
            }
        }, ${delay});
    </script>
</head>
<body>
<p id="death"></p>
</body>
</html>