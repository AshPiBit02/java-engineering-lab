<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h2>
    Name of Student: <%= request.getParameter("fullname") %><br>
    Date of Birth: <%= request.getParameter("dob") %><br>
    Level: <%= request.getParameter("level") %><br>
    Faculty: <%= request.getParameter("faculty") %><br>
    </h2>

</body>
</html>