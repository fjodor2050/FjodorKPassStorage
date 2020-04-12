<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="row justify-content-center">
    <h1>Вход в систему</h1>
    <form action="login" onsubmit="false" method="POST">
      <div class="form-group">
        <label for="login">Логин:</label>
        <input type="text" class="form-control" id="login" name="login" aria-describedby="emailHelp" placeholder="Enter email">
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" name="password" placeholder="Password">
      </div>
      <button type="button" id="enter" class="btn btn-primary">Войти</button>
    </form>
    <h4>У вас нет логина? <a href="newReader">Зарегистрируйтесь</a></h4>

</div>