package com.csci318.microservice.analytics.Services;

import com.csci318.microservice.analytics.DTOs.RatingsRestaurantCountDTOResponse;
import com.csci318.microservice.analytics.DTOs.RestaurantOrderStatusDTOResponse;

import java.util.List;

public interface AnalyticsService {
    public List<RatingsRestaurantCountDTOResponse> getRestaurantCountsByRatings();
    public List<RestaurantOrderStatusDTOResponse> getOrderStatusByRestaurants();
}
