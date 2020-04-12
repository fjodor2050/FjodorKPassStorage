/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsoncreator;

import entity.Book;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author user
 */
@Provider 
@Produces("JSON/format")
public class BookWriter  implements MessageBodyWriter<Book> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Book.class.isAssignableFrom(type); 
    }

    @Override
    public long getSize(Book book, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
 
        long size;
        size = 
                + book.getId().toString().length() + 1
                + book.getCaption().length() + 1
                + book.getAuthor().length()+1
                + book.getText().length()+1
                + book.getDate().toString().length()+1;
         return size;
        
    }

    @Override
    public void writeTo(Book t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        
    }
    
}
