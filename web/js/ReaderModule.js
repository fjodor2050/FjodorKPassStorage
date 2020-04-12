import {getHttp,postHttp} from './HttpModule.js';
import {listBooks} from './BookModule.js';

export {getReaders,printNewCustomerForm};

function getReaders(){
   getHttp("getListCustomers")
        .then(function(data) {  // data содержит ответ сервера преобразованный в js объект 
            if(data.authStatus === 'true'){
                printListCustomers(data); // запускается функция с параметром
                console.log('Request succeeded with JSON response', data);  //вывод в консоль для дебага 
            }else{
                document.getElementById('info').innerHTML = 'У вас нет прав, авторизуйтесь';
            }
            
          })
}
function printListCustomers(data){
    let content = document.getElementById('content');
    let cards = '';
        
        for(let i=0;i<data.customers.length;i++){
            let listAddress = '';
            for(let j = 0; j < data.customers[i].address.length; j++){
                listAddress+=data.customers[i].address[j].cantry;
                listAddress+=' ';
            }
            cards +=
            `<div class="card w-30 m-3 d-flex justify-content-between" style="max-width:300px">
                <div class="card-body">
                    <h5 class="card-title">${data.customers[i].firstname} ${data.customers[i].lastname}</h5>
                    <p class="card-text">Телефон: ${listAddress}</p>
                </div>
            </div>`;
        }
    content.innerHTML = cards;
}
function printNewCustomerForm(){
    let cards = 
                `<div class="card w-50">
                    <div class="card-body">
                      <h5 class="card-title w-100 text-center">Новый пользователь</h5>
                      <div class="card-text">
                        <div class="form-group">
                          <label for="firstname">Имя</label>
                          <input type="text" class="form-control" id="firstname">
                        </div>
                        <div class="form-group">
                          <label for="lastname">Фамилия</label>
                          <input type="text" class="form-control" id="lastname">
                        </div>
                        <div class="form-group">
                          <label for="cantry">Телефон</label>
                          <input type="text" class="form-control" id="cantry">
                        </div>
                        <div class="form-group">
                          <label for="login">Логин</label>
                          <input type="text" class="form-control" id="login">
                        </div>
                        <div class="form-group">
                          <label for="password">Пароль</label>
                          <input type="text" class="form-control" id="password">
                        </div>
                        <button class="btn bg-primary w-100" type="button" id="btnAddCustomer">Добавить</button>
                      </div>
                    </div>
                </div>`;
    document.getElementById('content').innerHTML = cards;
    document.getElementById('btnAddCustomer').onclick = function(){
    createCustomer();
  };
}
function createCustomer(){
    let firstname = document.getElementById('firstname').value;
    let lastname = document.getElementById('lastname').value;
    let cantry = document.getElementById('cantry').value;
    let login = document.getElementById('login').value;
    let password = document.getElementById('password').value;
    let customer = {
       "firstname": firstname,
       "lastname": lastname,
       "cantry": cantry,
       "login": login,
       "password": password
    };
    postHttp('createCustomerJson',customer)
          .then(function(response){ // response содержит ответ сервера преобразованный в js объект 
            if(response.actionStatus === 'true'){
              localStorage.setItem('user',response.user);
              document.getElementById('info').innerHTML = 'Новый пользователь добавлен, теперь войдите в систему';
            }else{
              document.getElementById('info').innerHTML = 'Ошибка при добавлении пользователя';
            }
            listBooks();
            console.log('Request succeeded with JSON response', response);  
          })

}

