
import {postHttp,getHttp} from './HttpModule.js';
import {listBooks} from './BookModule.js';
import {printNewCustomerForm} from './ReaderModule.js';

export {showLogin, logout};

function showLogin(){
  document.getElementById('content').innerHTML = 
   `<div class="w-100 d-flex justify-content-center">
     <div class="card border-primary p-2" style="max-width: 30rem;">
        <div class="card-header text-center">Введите логин и пароль</div>
        <div class="card-body">
          <p class="card-text d-flex justify-content-between">Логин: <input class="ml-2" type="text" id="login"></p>
          <p class="card-text d-flex justify-content-between">Пароль: <input class="ml-2" type="text" id="password"></p>
          <p class="card-text"><button class="btn btn-light w-100" type="button" id="btnEnter">Войти</button</p>
        </div>
        <p class="card-text d-flex justify-content-center"><a href="#" id="newCustomer">Зарегистрироваться</a></p>
      </div>
    </div>`;
  document.getElementById('btnEnter').onclick = function(){
    auth();
  };
  document.getElementById('newCustomer').addEventListener('click',printNewCustomerForm);
}

function auth(){
    let login = document.getElementById('login').value;
    let password = document.getElementById('password').value;
    let data = {
        'login': login,
        'password': password
    }
    postHttp('loginJson',data)
          .then(function(response){ // response содержит ответ сервера преобразованный в js объект 
            if(response.authStatus === 'true'){
              localStorage.setItem('token',response.token);
              localStorage.setItem('user',JSON.stringify(response.user));
              listBooks();
              document.getElementById('info').innerHTML = 'Вы вошли как '+response.user.login;
              document.getElementById('showLogin').style.display = 'none';
              document.getElementById('sysout').style.display = 'block';
              document.getElementById('addNewBook').style.display = 'block';
              document.getElementById('authUser').innerHTML = 'Привет, '+ response.user.login;
            }else{
              document.getElementById('info').innerHTML = 'Войти не удалось';
              printLoginForm();
              document.getElementById('showLogin').style.display = 'block';
              document.getElementById('sysout').style.display = 'none';
              document.getElementById('addNewBook').style.display = 'none';
              document.getElementById('authUser').innerHTML = '';
            }
            console.log('Request succeeded with JSON response', response);  
          })
}

function logout(){
    getHttp('logoutJson')
          .then(function(response){  // response содержит ответ сервера преобразованный в js объект 
            if(response.authStatus === 'false'){
              if(localStorage.getItem('token') !== null){
                  localStorage.removeItem('token');
              }
              if(localStorage.getItem('user') !== null){
                  localStorage.removeItem('user');
              }
              listBooks();
              document.getElementById('info').innerHTML = 'Вы вышли';
              document.getElementById('showLogin').style.display = 'block';
              document.getElementById('sysout').style.display = 'none';
              document.getElementById('addNewBook').style.display = 'none';
              document.getElementById('authUser').innerHTML = '';
            }else{
              document.getElementById('info').innerHTML = 'Ошибка при выходе пользователя';
            }
            console.log('Request succeeded with JSON response', response);  
          })
}