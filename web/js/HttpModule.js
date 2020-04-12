/*  fetch немножко параноик и по умолчанию не 
*   передаёт куки от сайта, на который 
*   отправляется запрос (а идентификатор 
*   сессии хранится в куке PHPSESSID). 
*   За передачу кук и заголовков авторизации 
*   отвечает опция credentials, которая может 
*   иметь одно из следующих значений:
*     'omit' (по умолчанию) — не передавать куки и заголовки авторизации;
*     'same-origin' — передавать, только если домен, на который 
*       отправляется запрос, совпадает с доменом текущего сайта 
*       (точнее, origin; сложный случай, но текущего вопроса не касается, 
*       так что не буду его подробно описывать);
*     'include' — передавать.
*/
export {getHttp,postHttp};

function getHttp(url){
    return fetch(url,{
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        },
                        method: 'GET',
                        credentials: 'include',
                     })
          .then(status)  
          .then(json)  
          .catch(function(error) { //срабатывает при ошибке пришедшей с сервера
            console.log('Request failed', error);  
          });
}

function postHttp(url,data){
    return fetch(url,{
                        headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                        },
                        method: 'POST',
                        credentials: 'include',
                        body: JSON.stringify(data)
                    })
          .then(status)  
          .then(json)  
          .catch(function(error) { //срабатывает при ошибке пришедшей с сервера
            console.log('Request failed', error);  
          });
}
//Возвращает ожидание при статусе ответа 200 - 300, иначе возвращает ошибку,
// которую прислал сервер в ответе
function status(response) {  
  if (response.status >= 200 && response.status < 300) {  
    return Promise.resolve(response)  
  } else {  
    return Promise.reject(new Error(response.statusText))  
  }  
}

// Получает ответ в формате Json и формирует из него js объект
function json(response) {  
  return response.json();
};

