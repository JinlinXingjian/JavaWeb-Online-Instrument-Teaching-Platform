<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<h1>Login Page</h1>
<form action="login" method="POST">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>

    <input type="submit" value="Login">
</form>
<form action="register" method="post">
    <label for="username">Username:</label>
    <input type="text" name="username" required><br><br>

    <label for="password">Password:</label>
    <input type="password" name="password" required><br><br>

    <label for="email">Email:</label>
    <input type="email" name="email" required><br><br>

    <label for="firstName">First Name:</label>
    <input type="text" name="firstName" required><br><br>

    <label for="lastName">Last Name:</label>
    <input type="text" name="lastName" required><br><br>

    <input type="submit" value="Register">
</form>

</body>
</html>
