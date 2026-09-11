package com.example.concurrency.controller;

import com.example.concurrency.dto.StockResponse;
import com.example.concurrency.service.VersionedStockService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VersionedStockController {
    private final VersionedStockService versionedStockService;

    @PostMapping("/versioned-stocks")
    public ResponseEntity<StockResponse> create(
            @RequestParam(defaultValue = "10") int quantity
    ) {
        StockResponse stock = versionedStockService.create(quantity);
        return ResponseEntity.created(URI.create("/versioned-stocks/" + stock.getId())).body(stock);
    }

    @GetMapping("/versioned-stocks/{id}")
    public ResponseEntity<StockResponse> find(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(versionedStockService.find(id));
    }

    @PostMapping("/versioned-stocks/{id}/decrease")
    public ResponseEntity<Void> decrease(
            @PathVariable Long id
    ) {
        try {
            versionedStockService.decrease(id);
            return ResponseEntity.noContent().build();
        } catch (ObjectOptimisticLockingFailureException conflict) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
