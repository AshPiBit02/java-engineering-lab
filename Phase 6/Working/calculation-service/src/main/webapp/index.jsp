<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Full Name</title>
</head>
<body>
    <!-- <h2>Full Name: <%=request.getparameter("firstName") %> <%= request.getparameter("lastName")%></h2> -->
    <h2>Full Name: <%= request.getParameter("firstName") %> <%= request.getParameter("lastName") %></h2>

</body>
</html>