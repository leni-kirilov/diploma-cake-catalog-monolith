<!doctype html>
<html>

<head>
  <meta name="layout" content="main"/>
  <title>Cake catalog</title>
</head>

<body>

<div id="content" role="main">
  <section class="row colset-2-its">
    <h1>Welcome to Cake Catalog monolith</h1>

    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>

    <h2>Login</h2>
    <g:form action="login" controller="login">
      <g:textField name="email" value="Enter email"/>
      <g:passwordField name="password" value="Enter password"/>
      <g:submitButton name="login"/>
    </g:form>

    <h2>Registration</h2>
    <g:link controller="portalUser" action="create">Not registered yet? Register a new user</g:link>
  </section>
</div>

</body>
</html>
