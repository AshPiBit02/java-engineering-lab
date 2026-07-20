<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Adder demo</title>
</head>
<body>
    <h2>Addition</h2>
    <form action="calculate" method="get">
    Number 1: <input type="number" name="num1" step="any"><br>
    Number 2: <input type="number" name="num2" step="any"><br>
    Result : <input type="text" value="<%= (String) request.getAttribute("result") %>" readonly><br>
    <input type="submit" name="operation" value="Add">
    <input type="submit" name="operation" value="Subtract">
    <input type="submit" name="operation" value="Multiply">
    <input type="submit" name="operation" value="Divide">
    </form>

</body>
</html>