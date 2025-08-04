package com.postech.saboresconectados.core.domain.entities;

import com.postech.saboresconectados.core.domain.entities.enumerators.CuisineType;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.domain.exceptions.UserNotRestaurantOwnerException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
public class Restaurant {
    private UUID id;
    private String name;
    private String address;
    private CuisineType cuisineType;
    private Map<DayOfWeek, DailySchedule> businessHours;
    private User owner;
    private LocalDateTime lastUpdated;

    private Restaurant(
            UUID id, String name, String address,
            CuisineType cuisineType, Map<DayOfWeek, DailySchedule> businessHours, User owner,
            LocalDateTime lastUpdated) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.businessHours = businessHours;
        this.setOwner(owner);
        this.lastUpdated = lastUpdated;
    }

    public void setOwner(User owner) {
        if (!owner.getUserType().equals(UserType.RESTAURANT_OWNER)) {
            throw new UserNotRestaurantOwnerException();
        }
        this.owner = owner;
    }

    public static class RestaurantBuilder {
        public Restaurant build() {
            return new Restaurant(
                    this.id, this.name, this.address,
                    this.cuisineType, this.businessHours, this.owner,
                    this.lastUpdated);
        }
    }
}
