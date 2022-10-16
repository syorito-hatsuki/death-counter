<html lang="en">
<head>
    <title>Death Counter</title>
    <script>
        setInterval(async function () {
            const response = await fetch("http://${remoteIp}:1540/${player}")
            const text = await response;
            if (text.ok) {
                document.getElementById("death").textContent = await text.json();
            } else {
                document.getElementById("death").textContent = text.status.toString()
            }
        }, ${delay});
    </script>
</head>
<body>
<p id="death"></p>
</body>
</html>