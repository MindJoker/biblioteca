package org.dirimo.biblioteca.resources.stock;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    @GetMapping
    public List<StockEntity> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/{id}")
    public Optional<StockEntity> getStockById(@PathVariable Long id) {
        return stockService.getStockById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockEntity addStock(@RequestBody StockEntity stock) {
        return stockService.addStock(stock);
    }

    @PutMapping("/{id}")
    public StockEntity updateStock(@PathVariable Long id, @RequestBody StockEntity updatedStock) {
        return stockService.updateStock(id, updatedStock);
    }

    @DeleteMapping("/{id}")
    public void deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
    }
}
