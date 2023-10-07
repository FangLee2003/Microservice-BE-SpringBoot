package com.bookservice.query.projection;

import com.bookservice.command.data.Book;
import com.bookservice.command.data.BookRepository;
import com.bookservice.query.model.BookResponseModel;
import com.bookservice.query.queries.GetAllBookQuery;
import com.bookservice.query.queries.GetBookQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {
    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public BookResponseModel handle(GetBookQuery getBookQuery) {
        Book book = bookRepository.getReferenceById(getBookQuery.getBookId());
        BookResponseModel model = new BookResponseModel();
        BeanUtils.copyProperties(book, model);

        return model;
    }

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBookQuery getAllBookQuery) {
        List<Book> bookList = bookRepository.findAll();
        List<BookResponseModel> modelList = new ArrayList<>();

        bookList.forEach(book -> {
            BookResponseModel model = new BookResponseModel();
            BeanUtils.copyProperties(book, model);

            modelList.add(model);
        });

        return modelList;
    }
}
