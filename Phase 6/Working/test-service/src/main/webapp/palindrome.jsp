<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Palindrome Checker</title>
</head>
<body>
    <form action="palindromeChecker" method="post">
        Input String: <input type="text" name="input"><br>
        Result: <%= request.getAttribute("result")!=null? request.getAttribute("result"):"" %><br>
        <input type="submit" value="Check">
    </form>
</body>
</html>