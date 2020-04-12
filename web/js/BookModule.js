
import {getHttp,postHttp} from './HttpModule.js';

export {listBooks, addNewBook};
   
    function listBooks(){
        getHttp("getListNewBooks")
              .then(function(data) {  // data содержит ответ сервера преобразованный в js объект 
                printListNewBooks(data); // запускается функция с параметром
                console.log('Request succeeded with JSON response', data);  //вывод в консоль для дебага
              });
    }
    function printListNewBooks(data){
        let content = document.getElementById('content');

        let cards = '';
        for(let i = 0; i < data.books.length; i++){
            cards +=
            `<div class="card w-30 m-3 d-flex justify-content-between" >
                <div class="card-body">
                    <h5 class="card-title">Сайт: ${data.books[i].caption}</h5>
                    <p class="card-text">Логин: ${data.books[i].author}</p>
                    <p class="card-text">Пароль: ${data.books[i].text}</p>
                    <p class="card-text">Время добавления: ${data.books[i].date}</p> 
                </div>
            </div>`;
        }
        content.innerHTML = cards;
        let title = document.createElement("h3");
        title.innerHTML = "Пароли";
        title.classList.add('w-100');
        title.classList.add('text-center');
        content.prepend(title);
    }
    function addNewBook(){
        let formNewBook =
                `<div class="card w-50">
                    <div class="card-body">
                      <h5 class="card-title w-100 text-center">Новые данные</h5>
                      <div class="card-text">
                        <div class="form-group">
                          <label for="caption">Сайт</label>
                          <input type="text" class="form-control" id="caption">
                        </div>
                        <div class="form-group">
                          <label for="author">Логин</label>
                          <input type="text" class="form-control" id="author">
                        </div>
                        <div class="form-group">
                          <label for="textBook">Пароль</label>
                          <input type="text" class="form-control" id="textBook">
                        </div>
                        
                        <button class="btn bg-primary w-100" type="button" id="btnAddBook">Добавить</button>
                      </div>
                    </div>
                </div>`;
        document.getElementById('content').innerHTML = formNewBook;
        document.getElementById('btnAddBook').onclick = function(){
            createBook();
        }
        function createBook(){
            let caption = document.getElementById('caption').value;
            let author = document.getElementById('author').value;
            let textBook = document.getElementById('textBook').value;
            let book = {
                'caption': caption,
                'author': author,
                'textBook': textBook
            }
            postHttp('addBook',book)
                    .then(function(response){
                      if(response.actionStatus === 'true'){
                          document.getElementById('info').innerHTML='Данные добавлены';
                      }else{
                          document.getElementById('info').innerHTML='Данные добавить не удалось';
                      } 
                      listBooks();
                    });
                
        }
    }



