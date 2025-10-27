package com.javarush.baranov.testingplatform.util.entities;

import java.time.LocalDateTime;
import java.util.Map;

public record AttemptAttributes(
        Map<Long, Long> questionToAnswer,
        LocalDateTime completedAt
) {}