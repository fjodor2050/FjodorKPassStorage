
import {listBooks, addNewBook} from './BookModule.js';
import {getReaders,printNewCustomerForm} from './ReaderModule.js';
import {showLogin, logout} from './AuthModule.js';

//navigation menu - нажатие на кнопку "Новые книги"
document.getElementById('showNewBooks').onclick=function (){
    toogleActiveLink("showNewBooks"); // делает текст нажатой ссылки жирным (css класс "active")
    listBooks();
};
document.getElementById('showReaders').onclick=function (){
    toogleActiveLink("showReaders");
    getReaders();
};
document.getElementById('addNewBook').onclick=function (){
    toogleActiveLink("addNewBook");
    addNewBook();
};
document.getElementById('addNewCustomerForm').onclick=function (){
    toogleActiveLink("addNewCustomerForm");
    addNewCustomerForm();
};
document.getElementById('sysout').onclick=function (){
    toogleActiveLink("sysout");
    logout();
};
document.getElementById('showLogin').onclick=function (){
    toogleActiveLink("showLogin");
    showLogin();
};

function toogleActiveLink(elementId){
    let activeElement = document.getElementById(elementId);
    let passiveElements = document.getElementsByClassName('nav-link');
    for(let i = 0;i < passiveElements.length; i++){
        if(activeElement === passiveElements[i]){
            passiveElements[i].classList.add("active");
        }else{
            if(passiveElements[i].classList.contains('active')){
                passiveElements[i].classList.remove('active');
            }
        }
    }
}
authUserMenu();
function authUserMenu(){
    let user = null;
    if(localStorage.getItem('user') !== null){
        user = JSON.parse(localStorage.getItem('user'));
        document.getElementById('showLogin').style.display = 'none';
        document.getElementById('sysout').style.display = 'block';
        document.getElementById('addNewBook').style.display = 'block';
        document.getElementById('authUser').innerHTML = 'Привет, '+user.login;
        
    }else{
        document.getElementById('showLogin').style.display ='block' ;
        document.getElementById('sysout').style.display = 'none';
        document.getElementById('addNewBook').style.display = 'none';
        document.getElementById('authUser').innerHTML = '';
    }
    
}






