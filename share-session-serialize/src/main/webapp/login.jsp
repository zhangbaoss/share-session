<html>
<body>
<h2>Hello World!</h2>

<form action="login" method="post">
<input type="text" name="username" id="username"></input>
<input type="text" name="password" value="<%= session.getAttribute(request.getParameter("username")) %>"></input>
<br><input type="submit" value="login">
</form>
</body>
</html>
