package com.csci318.microservice.analytics.Domain.Relations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeedbackEvent {
    private UUID id;
    private String eventName;
    private UUID userId;
    private UUID restaurantId;
    private int rating;
    private double averageRating;
}
