<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>
    <h1>
        This is <%= request.getAttribute("name") %> 
        who is from <%= request.getAttribute("city") %> 
        and is <%= request.getAttribute("profession") %> by profession.
    </h1>
</body>
</html>
