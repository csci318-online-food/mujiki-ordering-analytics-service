package com.csci318.microservice.analytics.Domain.Relations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

import com.csci318.microservice.analytics.Constants.OrderStatus;

import io.micrometer.common.lang.Nullable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderStatusEvent {
    private UUID id;
    private String eventName;
    private UUID orderId;
    private UUID userId;
    private UUID restaurantId;
    private OrderStatus status;
    @Nullable
    private OrderStatus oldStatus;
    @Nullable
    private Double totalPrice;
    private LocalDateTime changeTime;
}
