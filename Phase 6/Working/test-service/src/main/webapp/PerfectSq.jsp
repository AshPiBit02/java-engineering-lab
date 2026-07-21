<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Perfect Square Checker</title>
</head>
<body>
    <h1>Enter a number to check if it is Percet Square</h1>
    <form action="perfectSQ" method="get">
        Input: <input type="number" name="num" value="<%= request.getParameter("num") != null ? request.getParameter("num") : "" %>"><br>
        Result: <%= request.getAttribute("result")!=null? request.getAttribute("result"):"" %><br>
        <input type="submit" value="Check">
    </form>
</body>
</html>