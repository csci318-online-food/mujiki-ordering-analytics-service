package com.csci318.microservice.analytics.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csci318.microservice.analytics.DTOs.RatingsRestaurantCountDTOResponse;
import com.csci318.microservice.analytics.DTOs.RestaurantOrderStatusDTOResponse;
import com.csci318.microservice.analytics.Services.AnalyticsService;

@RestController
@RequestMapping("${api.endpoint.base-url}/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/ratings")
    public ResponseEntity<List<RatingsRestaurantCountDTOResponse>> getRestaurantCountsByRatings() {
        List<RatingsRestaurantCountDTOResponse> ratingsDTOResponse
            = analyticsService.getRestaurantCountsByRatings();
        return ResponseEntity.ok(ratingsDTOResponse);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<RestaurantOrderStatusDTOResponse>> getOrderStatusByRestaurants() {
        List<RestaurantOrderStatusDTOResponse> ordersDTOResponse
            = analyticsService.getOrderStatusByRestaurants();
        return ResponseEntity.ok(ordersDTOResponse);
    }
}
