package com.timetoast.statistics_service.member.adapter.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.timetoast.statistics_service.member.domain.dto.MemberJoinDto;
import com.timetoast.statistics_service.member.port.in.MemberJoinUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MemberJoinStreamSubscriber implements StreamListener<String, MapRecord<String, String, String>> {

    private final MemberJoinUseCase memberJoinUseCase;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${stream.group.member-joined}")
    private String memberJoinedGroup;

    public MemberJoinStreamSubscriber(final MemberJoinUseCase memberJoinUseCase,
                                      final RedisTemplate<String, String> redisTemplate) {
        this.memberJoinUseCase = memberJoinUseCase;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        String payload = message.getValue().get("payload");
        try {
            objectMapper.registerModule(new JavaTimeModule());
            MemberJoinDto dto = objectMapper.readValue(payload, MemberJoinDto.class);
            memberJoinUseCase.saveSignUpState(dto);

            redisTemplate.opsForStream().acknowledge(memberJoinedGroup, message);
        } catch (JsonProcessingException e) {
            log.warn("Error parsing json", e);
        }
    }


}
