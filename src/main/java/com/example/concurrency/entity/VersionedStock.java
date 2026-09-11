package com.example.concurrency.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "versioned_stocks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VersionedStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @Version
    private Long version;

    public VersionedStock(
            int quantity
    ) {
        this.quantity = quantity;
    }

    public void decrease() {
        quantity--;
    }
}
