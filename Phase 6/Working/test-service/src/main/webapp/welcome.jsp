<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Logged In</title>
</head>
<body>
    <h2>Welcome Page</h2>
    <% 
    String user=(String) request.getAttribute("username");
    String pass=(String) request.getAttribute("password");
    %>

    Hello, <%= user%><br>
    Your Password is : <%= pass%>
</body>
</html>