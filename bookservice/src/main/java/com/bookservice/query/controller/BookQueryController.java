package com.bookservice.query.controller;

import com.bookservice.query.model.BookResponseModel;
import com.bookservice.query.queries.GetAllBookQuery;
import com.bookservice.query.queries.GetBookQuery;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookQueryController {
    @Autowired
    private QueryGateway queryGateway;

//    private Logger logger = org.slf4j.LoggerFactory.getLogger(BookserviceApplication.class);

    @GetMapping("/{bookId}")
    public BookResponseModel getBook(@PathVariable String bookId) {
        GetBookQuery getBookQuery = new GetBookQuery();
        getBookQuery.setBookId(bookId);

        BookResponseModel model = queryGateway.query(getBookQuery, ResponseTypes.instanceOf(BookResponseModel.class)).join();

        return model;
    }

    @GetMapping()
    public List<BookResponseModel> getAllBooks() {
        GetAllBookQuery getAllBookQuery = new GetAllBookQuery();
        List<BookResponseModel> modelList = queryGateway.query(getAllBookQuery, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();

//        logger.info("Book list: " + modelList.toString());

        return modelList;
    }
}
