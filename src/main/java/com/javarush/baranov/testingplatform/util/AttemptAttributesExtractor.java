package com.javarush.baranov.testingplatform.util;

import com.javarush.baranov.testingplatform.util.entities.AttemptAttributes;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AttemptAttributesExtractor {

    public AttemptAttributes extract(HttpServletRequest req) {
        Map<Long, Long> answerMap = new HashMap<>();

        Map<String, String[]> parameterMap = req.getParameterMap();

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String paramName = entry.getKey();

            if (paramName.startsWith("question_")) {
                String questionIdStr = paramName.substring("question_".length());
                Long questionId = Long.parseLong(questionIdStr);

                String[] selectedAnswerIds = entry.getValue();
                if (selectedAnswerIds != null && selectedAnswerIds.length > 0) {
                    Long selectedAnswerId = Long.parseLong(selectedAnswerIds[0]);
                    answerMap.put(questionId, selectedAnswerId);
                }
            }
        }
        return new AttemptAttributes(answerMap, LocalDateTime.now());
    }
}
