<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Full Name</title>
</head>
<body>
    <h2>Full Name: <%= request.getParameter("firstName") %> <%= request.getParameter("lastName") %></h2>
    <a href="index.html" name="back"></a>

</body>
</html>