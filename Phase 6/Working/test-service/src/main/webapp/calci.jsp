<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Adder demo</title>
</head>
<body>
    <h2>Addition</h2>
    Number 1: <input type="number" name="num1">
    Number 2: <input type="number" name="num2">
    Result : <input type="text" value="<%= request.getAttribute("result") %>" readonly>

</body>
</html>