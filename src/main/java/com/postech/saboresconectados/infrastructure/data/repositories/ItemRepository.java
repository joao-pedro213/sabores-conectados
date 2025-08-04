package com.postech.saboresconectados.infrastructure.data.repositories;

import com.postech.saboresconectados.infrastructure.data.models.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<ItemModel, UUID> {
}
