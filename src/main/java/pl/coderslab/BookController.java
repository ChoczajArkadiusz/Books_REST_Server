package pl.coderslab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public List<Book> getAll(){
        return bookService.getList();
    }

    @PostMapping("/")
    public String postById(@RequestBody Book book) {
        bookService.add(book);
        return "";
    }


    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id){
        Book book = bookService.getById(id);
        return book;
    }

    @PutMapping("/{id}")
    public String putById(@PathVariable Long id, @RequestBody Book book){
        bookService.update(id, book);
        return "";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id){
        bookService.delete(id);
        return "";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "{hello: World}";
    }

    @RequestMapping("/helloBook")
    public Book helloBook() {
        return new Book(1L, "9788324631766", "Thinking in Java", "Bruce Eckel", "Helion", "programming");
    }




}