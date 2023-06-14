package org.example.config;

import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("singleton")
@Data
public class UserAttempts {

    private Map<String, Integer> userAttemptsMap = new HashMap<>();

}
