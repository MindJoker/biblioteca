package org.dirimo.biblioteca.resources.stock;

import lombok.RequiredArgsConstructor;
import org.dirimo.biblioteca.resources.book.BookRepository;
import org.dirimo.biblioteca.resources.book.Book;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final BookRepository bookRepository;

    public List<Stock> getAllStocks(){
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(Long id){
        return stockRepository.findById(id);
    }

    public Stock addStock(Stock stock)
    {
        Long bookId = stock.getBook().getId();
        Book fullBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Libro con Id " + bookId + " non trovato"));

        stock.setBook(fullBook);

        return stockRepository.save(stock);
    }

    public Stock updateStock (Long id, Stock stock)
    {
        stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock Id" + id + "non trovato"));
        stock.setId(id);
        return stockRepository.save(stock);
    }

    public void deleteStock(Long id)
    {
        stockRepository.deleteById(id);
    }

    public Optional<Stock> findByBookId(Long bookId) {
        return stockRepository.findByBookId(bookId);
    }
}


