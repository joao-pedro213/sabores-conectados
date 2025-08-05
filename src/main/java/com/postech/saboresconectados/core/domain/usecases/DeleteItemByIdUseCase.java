package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.gateways.ItemGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeleteItemByIdUseCase {
    private final ItemGateway itemGateway;

    public static DeleteItemByIdUseCase build(ItemGateway itemGateway) {
        return new DeleteItemByIdUseCase(itemGateway);
    }

    public void execute(UUID id) {
        this.itemGateway.deleteById(id);
    }
}
