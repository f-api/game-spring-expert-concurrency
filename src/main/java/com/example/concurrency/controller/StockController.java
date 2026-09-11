package com.example.concurrency.controller;

import com.example.concurrency.dto.StockResponse;
import com.example.concurrency.service.StockService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @PostMapping("/stocks")
    public ResponseEntity<StockResponse> create(
            @RequestParam(defaultValue = "10") int quantity
    ) {
        StockResponse stock = stockService.create(quantity);
        return ResponseEntity.created(URI.create("/stocks/" + stock.getId())).body(stock);
    }

    @GetMapping("/stocks/{id}")
    public ResponseEntity<StockResponse> find(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(stockService.find(id));
    }

    @PostMapping("/stocks/{id}/decrease")
    public ResponseEntity<Void> decrease(
            @PathVariable Long id
    ) {
        stockService.decrease(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/stocks/{id}/decrease-with-lock")
    public ResponseEntity<Void> decreaseWithLock(
            @PathVariable Long id
    ) {
        stockService.decreaseWithLock(id);
        return ResponseEntity.noContent().build();
    }
}
