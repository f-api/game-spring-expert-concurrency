package com.example.concurrency.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StockResponse {
    private final Long id;
    private final int quantity;
}
