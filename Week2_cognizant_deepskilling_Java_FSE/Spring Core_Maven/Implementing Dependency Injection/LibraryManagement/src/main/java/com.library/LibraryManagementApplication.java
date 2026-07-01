package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LibraryManagementApplication {

    public static void main(String[] args) {

        // Load Spring container
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        // Get Service Bean
        BookService bookService = (BookService) context.getBean("bookService");

        // Test DI
        bookService.addBook("Java Spring DI Example");
    }
}