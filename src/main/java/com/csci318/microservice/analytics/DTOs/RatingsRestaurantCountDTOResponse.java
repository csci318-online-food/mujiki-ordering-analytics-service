package com.csci318.microservice.analytics.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RatingsRestaurantCountDTOResponse {
    private int rating;
    private long restaurants;
}
