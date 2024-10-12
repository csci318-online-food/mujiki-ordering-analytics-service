package com.csci318.microservice.analytics.Configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditDataConfig {
//    @Bean
//    public AuditorAware<String> auditorProvider() {
//        return () -> {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                return Optional.empty();
//            }
//
//            Object principal = authentication.getPrincipal();
//            if (principal instanceof UserDetails) {
//                return Optional.ofNullable(((UserDetails) principal).getUsername());
//            } else {
//                // Handle other cases if needed
//                return Optional.empty();
//            }
//        };
    }
