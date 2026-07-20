<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Failed Login</title>
</head>
<body>
    <h2>Not welcome</h2>
    <%
    String user=(String) request.getAttribute("username");
    String pass=(String) request.getAttribute("password");
    %>
    <h2>Invalid User: <%= user %></h2><br>
    <h4>OR</h4><br>
    <h2>Incorrect Password: <%= pass %></h2><br>
</body>
</html>