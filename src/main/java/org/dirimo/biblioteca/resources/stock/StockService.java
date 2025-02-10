package org.dirimo.biblioteca.resources.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public List<StockEntity> getAllStocks(){
        return stockRepository.findAll();
    }

    public Optional<StockEntity> getStockById(Long id){
        return stockRepository.findById(id);
    }

    public StockEntity addStock(StockEntity stock)
    {
        return stockRepository.save(stock);
    }

    public StockEntity updateStock (Long id, StockEntity updatedStock)
    {
        StockEntity stock = stockRepository.findById(id)
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
