package com.postech.saboresconectados.core.restaurant.domain.entity;

import com.postech.saboresconectados.core.restaurant.domain.entity.enumerator.CuisineType;
import com.postech.saboresconectados.core.user.domain.entity.UserEntity;
import com.postech.saboresconectados.core.user.domain.entity.enumerator.UserType;
import com.postech.saboresconectados.core.restaurant.domain.exception.UserNotRestaurantOwnerException;
import com.postech.saboresconectados.core.restaurant.valueobject.DailySchedule;
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
public class RestaurantEntity {
    private UUID id;
    private UserEntity owner;
    private String name;
    private String address;
    private CuisineType cuisineType;
    private Map<DayOfWeek, DailySchedule> businessHours;
    private LocalDateTime lastUpdated;

    private RestaurantEntity(
            UUID id, UserEntity owner, String name,
            String address, CuisineType cuisineType, Map<DayOfWeek, DailySchedule> businessHours,
            LocalDateTime lastUpdated) {
        this.id = id;
        this.setOwner(owner);
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.businessHours = businessHours;
        this.lastUpdated = lastUpdated;
    }

    public void setOwner(UserEntity owner) {
        if (!owner.getUserType().equals(UserType.RESTAURANT_OWNER)) {
            throw new UserNotRestaurantOwnerException();
        }
        this.owner = owner;
    }

    public static class RestaurantEntityBuilder {
        public RestaurantEntity build() {
            return new RestaurantEntity(
                    this.id, this.owner, this.name, this.address,
                    this.cuisineType, this.businessHours,
                    this.lastUpdated);
        }
    }
}
