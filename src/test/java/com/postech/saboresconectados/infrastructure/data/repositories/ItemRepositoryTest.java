package com.postech.saboresconectados.infrastructure.data.repositories;

import com.postech.saboresconectados.infrastructure.data.models.ItemModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = {"/infrastructure/data/repositories/item-test-sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class ItemRepositoryTest {
    @Autowired
    ItemRepository repository;

    private static UUID RESTAURANT_ID = UUID.fromString("faa47066-ea9d-4dab-94b1-4f25db411a4e");

    @Test
    void shouldFindAllByRestaurantId() {
        List<ItemModel> foundItems = this.repository.findAllByRestaurantId(RESTAURANT_ID);

        assertThat(foundItems).hasSize(3);
    }
}
