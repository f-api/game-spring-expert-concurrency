package com.example.concurrency.repository;

import com.example.concurrency.entity.VersionedStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionedStockRepository extends JpaRepository<VersionedStock, Long> {
}
