package com.csci318.microservice.analytics.Services.Impl;

import com.csci318.microservice.analytics.Constants.OrderStatus;
import com.csci318.microservice.analytics.Constants.Stores;
import com.csci318.microservice.analytics.Domain.Relations.FeedbackEvent;
import com.csci318.microservice.analytics.Domain.Relations.OrderStatusEvent;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class StreamProcessor {
    @Bean
    public Consumer<KStream<String, FeedbackEvent>> handleFeedbackEvent() {
        record Average(Long value, Integer count) { }

        return inputStream -> {
            // Generate RUNNING counts of restaurants by rounded ratings.
            KTable<Integer, Long> ratingCounts = inputStream
                .map((key, event) -> {
                    UUID restaurantId = event.getRestaurantId();
                    Integer rating = event.getRating();
                    return KeyValue.pair(restaurantId, rating);
                })
                .groupByKey(Grouped.with(Serdes.UUID(), Serdes.Integer()))
                // Calculate the totals and the counts.
                .aggregate(
                    () -> new Average(0L, 0),
                    (restaurantId, rating, current) -> {
                        return new Average(current.value + rating, current.count + 1);
                    },
                    Materialized.with(Serdes.UUID(), new JsonSerde<>(Average.class))
                )
                // Convert the tuple to an average.
                .mapValues((restaurantId, average) -> {
                    return (int)Math.round((double)average.value / average.count);
                })
                // Reverse the key/values. Group by average.
                .groupBy(
                    (restaurantId, average) -> {
                        return KeyValue.pair(average, restaurantId);
                    },
                    Grouped.with(Serdes.Integer(), Serdes.UUID())
                )
                // Count the number of restaurants for each average value.
                .count(
                    Materialized.<Integer, Long, KeyValueStore<Bytes, byte[]>>as(Stores.RATINGS)
                        .withKeySerde(Serdes.Integer())
                        .withValueSerde(Serdes.Long())
                );

            // Print data to console (not part of the stream processing logic).
            ratingCounts.toStream().
                print(Printed.<Integer, Long>toSysOut()
                    .withLabel("Total restaurants by rating")
                );
        };
    }

    @SuppressWarnings("unchecked")
    @Bean Consumer<KStream<String, OrderStatusEvent>> handleOrderStatusEvent() {
        return inputStream -> {
            // Generate RUNNING counts of orders for each restaurant by status.

            KTable<UUID, List<Long>> orderCounts = inputStream.map((key, event) -> {
                UUID restaurantId = event.getRestaurantId();
                return KeyValue.pair(restaurantId, event);
            })
            // Group all events by restaurant ID
            .groupByKey(
                Grouped.with(
                    Serdes.UUID(),
                    new JsonSerde<>(OrderStatusEvent.class)
            ))
            // Calculate the number of orders of each status.
            .aggregate(
                () -> new ArrayList<Long>(Collections.nCopies(OrderStatus.values().length, 0L)),
                (restaurantId, event, current) -> {
                    OrderStatus oldStatus = event.getOldStatus();
                    if (oldStatus != null) {
                        // This order has its status changed.
                        // Remove itself from the old status count.
                        int oldStatusIndex = oldStatus.ordinal();
                        current.set(
                            oldStatusIndex,
                            current.get(oldStatusIndex) - 1
                        );
                    }

                    // Increment the new status count.
                    OrderStatus status = event.getStatus();
                    int statusIndex = status.ordinal();
                    current.set(
                        statusIndex,
                        current.get(statusIndex) + 1
                    );

                    return current;
                },
                Materialized.<
                    UUID,
                    List<Long>,
                    KeyValueStore<Bytes, byte[]>
                >as(Stores.ORDERS)
                    .withKeySerde(Serdes.UUID())
                    .withValueSerde(
                        Serdes.<ArrayList<Long>, Long>ListSerde(
                            (Class<ArrayList<Long>>)new ArrayList<Long>().getClass(),
                            Serdes.Long()
                        )
                    )
            );

            // Print data to console (not part of the stream processing logic).
            orderCounts.toStream().
                print(Printed.<UUID, List<Long>>toSysOut()
                    .withLabel("Orders by restaurants")
                );
        };
    }
}
