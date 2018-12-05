<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
  
    <h1>Web Checkers</h1>
    
    <div class="navigation">
      <a href="/">My Home</a>
      <#if currentPlayer??>
        <a href="/signout">Sign Out</a>
      <#else>
        <a href="/signin">Sign in</a>
      </#if>
    </div>
    
    <div class="body">
      <p>Welcome to the world of online Checkers.</p>
      <p>There are ${numPlayers} player(s) online.</p>
      <#if error??>
        <p style="color: red;">${error}</p>
      </#if>
      <#if currentPlayer??>
        <p>You are signed in as: ${currentPlayer.getName()}</p>
        <p>Other Players:</p>
        <#list players as player>
          <#if player.getName() == currentPlayer.getName()>
          <#else>
          <p>Status: ${player} | Name: <a href="/game?id=${player.getName()}">${player.getName()}</a></p>
          </#if>
          </#list>
          <p>Last Game:</p>
          <#if game??>
            <p>Game: <a href="/replay/game?gameID=1">${game.getRedPlayer().getName()} vs. ${game.getWhitePlayer().getName()}</a>
                     </#if>
                     <#else>
        <p>You are not signed in!</p>
      </#if>
    </div>
    
  </div>
</body>
</html>
