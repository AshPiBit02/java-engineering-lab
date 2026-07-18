<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Simple Calculator</title>
</head>
<body>
    <h2>Addition of Two Numbers</h2>
    <form action="calculate" method="get">
        Number 1:<input type="number" step="any" name="num1" value="${param.num1}"><br><br>
        Number 2:<input type="number" step="any" name="num2" value="${param.num2}"><br><br>
        Result  :<input type="text" name="result" value="${result}" readonly><br><br>
        <input type="submit" value="add">
    </form>
</body>
</html>