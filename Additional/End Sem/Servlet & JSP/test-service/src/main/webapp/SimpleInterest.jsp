<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h2>
        <% 
        double principle=Double.parseDouble(request.getParameter("p"));
        double time=Double.parseDouble(request.getParameter("t"));
        double rate=Double.parseDouble(request.getParameter("r"));
        double si=(principle*time*rate)/100;
        %>

        Principe: $<%= principle %><br>
        Time: <%= time %><br>
        Rate: <%= rate %><br>
        Simple Interest: $<%= si %><br>
    </h2>
</body>
</html>