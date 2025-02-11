package org.dirimo.biblioteca.resources.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public List<Stock> getAllStocks(){
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(Long id){
        return stockRepository.findById(id);
    }

    public Stock addStock(Stock stock)
    {
        return stockRepository.save(stock);
    }

    public Stock updateStock (Long id, Stock updatedStock)
    {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock Id" + id + "non trovato"));

        stock.setBook(updatedStock.getBook());
        stock.setAvCopies(updatedStock.getAvCopies());
        stock.setTotCopies(updatedStock.getTotCopies());

        return stockRepository.save(stock);
    }

    public void deleteStock(Long id)
    {
        stockRepository.deleteById(id);
    }
}
