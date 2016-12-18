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

    <h2>Login</h2>
    <g:form action="login" controller="login">
      <g:textField name="email">Enter  username/email :</g:textField>
      <g:textField name="password">Enter password:</g:textField>
      <g:submitButton name="login"/>
    </g:form>

    %{--TODO mask password--}%

    <h2>Registration</h2>
    <g:link controller="portalUser" action="create">Not registered yet? Register a new user</g:link>
  </section>
</div>

</body>
</html>
