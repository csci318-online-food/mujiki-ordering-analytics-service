package com.csci318.microservice.analytics.DTOs;

import com.csci318.microservice.analytics.Constants.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.SortedMap;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestaurantOrderStatusDTOResponse {
    private UUID restaurantId;
    private SortedMap<OrderStatus, Long> orderStatuses;
}
