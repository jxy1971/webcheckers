<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
  
    <h1>Sign In Page</h1>
    
    <div class="navigation">
      <a href="/">My Home</a>
    </div>
    
    <div class="body">
      <form action="/signin" method="POST">
        Sign In
        <#if signInFail??>
          <br/>
          <p>${signInFail}</p>
        </#if>
        <br/>
        <input name="username" placeholder="Username">
        <br/>
        <button type="submit">Go!</button>
      </form>
    </div>
    
  </div>
</body>
</html>
