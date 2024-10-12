package com.csci318.microservice.analytics.Services.Impl;

import com.csci318.microservice.analytics.Constants.OrderStatus;
import com.csci318.microservice.analytics.Constants.Stores;
import com.csci318.microservice.analytics.DTOs.RatingsRestaurantCountDTOResponse;
import com.csci318.microservice.analytics.DTOs.RestaurantOrderStatusDTOResponse;
import com.csci318.microservice.analytics.Services.AnalyticsService;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    private final InteractiveQueryService interactiveQueryService;

    public AnalyticsServiceImpl(InteractiveQueryService interactiveQueryService) {
        this.interactiveQueryService = interactiveQueryService;
    }

    public List<RatingsRestaurantCountDTOResponse> getRestaurantCountsByRatings() {
        List<RatingsRestaurantCountDTOResponse> response = new ArrayList<>();
        KeyValueIterator<Integer, Long> all = this.<Integer, Long>getKSStore(Stores.RATINGS).all();
        while (all.hasNext()) {
            KeyValue<Integer, Long> kvp = all.next();

            RatingsRestaurantCountDTOResponse dto = new RatingsRestaurantCountDTOResponse();
            dto.setRating(kvp.key);
            dto.setRestaurants(kvp.value);
            response.add(dto);
        }
        response.sort((left, right) -> {
            return Integer.compare(left.getRating(), right.getRating());
        });
        return response;
    }

    public List<RestaurantOrderStatusDTOResponse> getOrderStatusByRestaurants() {
        List<RestaurantOrderStatusDTOResponse> response = new ArrayList<>();
        KeyValueIterator<UUID, List<Long>> all =
            this.<UUID, List<Long>>getKSStore(Stores.ORDERS).all();
        while (all.hasNext()) {
            KeyValue<UUID, List<Long>> kvp = all.next();

            SortedMap<OrderStatus, Long> orderStatuses = new TreeMap<>();
            for (int i = 0; i < kvp.value.size(); ++i) {
                orderStatuses.put(OrderStatus.values()[i], kvp.value.get(i));
            }

            RestaurantOrderStatusDTOResponse dto = new RestaurantOrderStatusDTOResponse();
            dto.setRestaurantId(kvp.key);
            dto.setOrderStatuses(orderStatuses);
            response.add(dto);
        }
        return response;
    }

    private <TKey, TValue> ReadOnlyKeyValueStore<TKey, TValue> getKSStore(final String id) {
        return this.interactiveQueryService.getQueryableStore(id, QueryableStoreTypes.keyValueStore());
    }
}
