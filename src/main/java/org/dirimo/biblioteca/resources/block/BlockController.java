package org.dirimo.biblioteca.resources.block;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("Block")
public class BlockController {

    private final BlockService blockService;

    @GetMapping("/")
    public List<Block> list() {
        return blockService.list();
    }

    @GetMapping("/{id}")
    public Block getById(@PathVariable Long id) {
        return blockService.getById(id)
                .orElseThrow(() -> new RuntimeException("Blocco non trovato"));
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Block create(@RequestBody String data) {
        return blockService.create(data);
    }

    @GetMapping("valid/")
    public ResponseEntity<Map<String, Object>> checkValid() {
        boolean isValid = blockService.isChainValid();
        Map<String, Object> response = new HashMap<>();
        response.put("valid", isValid);
        response.put("message", isValid ? "La blockchain è valida" : "La blockchain NON è valida");

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/")
    public void delete(){
        blockService.resetBlockchain();
    }
}
