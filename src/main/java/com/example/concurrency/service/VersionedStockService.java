package com.example.concurrency.service;

import com.example.concurrency.dto.StockResponse;
import com.example.concurrency.entity.VersionedStock;
import com.example.concurrency.repository.VersionedStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class VersionedStockService {
    private final VersionedStockRepository versionedStockRepository;

    @Transactional
    public StockResponse create(
            int quantity
    ) {
        VersionedStock newStock = new VersionedStock(quantity);
        VersionedStock stock = versionedStockRepository.save(newStock);
        return new StockResponse(stock.getId(), stock.getQuantity());
    }

    @Transactional(readOnly = true)
    public StockResponse find(
            Long id
    ) {
        VersionedStock stock = versionedStockRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new StockResponse(stock.getId(), stock.getQuantity());
    }

    @Transactional
    public void decrease(
            Long id
    ) {
        VersionedStock stock = versionedStockRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        stock.decrease();
    }
}
