package com.example.concurrency.service;

import com.example.concurrency.dto.StockResponse;
import com.example.concurrency.entity.Stock;
import com.example.concurrency.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    @Transactional
    public StockResponse create(
            int quantity
    ) {
        Stock newStock = new Stock(quantity);
        Stock stock = stockRepository.save(newStock);
        return new StockResponse(stock.getId(), stock.getQuantity());
    }

    @Transactional(readOnly = true)
    public StockResponse find(
            Long id
    ) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new StockResponse(stock.getId(), stock.getQuantity());
    }

    @Transactional
    public void decrease(
            Long id
    ) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        stock.decrease();
    }

    @Transactional
    public void decreaseWithLock(
            Long id
    ) {
        Stock stock = stockRepository.findLockedById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        stock.decrease();
    }
}
