package com.timetoast.statistics_service.member.adapter.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timetoast.statistics_service.member.domain.dto.SignUpInfo;
import com.timetoast.statistics_service.util.TestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminMemberControllerTest extends TestSupport {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("/signupInfo 조회 API")
    public void getSignUpInfo(){
        try{
            MvcResult result = mockMvc.perform(get("/statistics/api/v3/members/signupInfo")
                            .header("X-User-Id", 1L)
                            .header("X-User-Role", "STAFF"))
                    .andExpect(status().isOk())
                    .andReturn();

            String json = result.getResponse().getContentAsString();
            SignUpInfo actual = objectMapper.readValue(json, SignUpInfo.class);

            assertThat(actual.totalUserCount()).isGreaterThanOrEqualTo(0L);
            assertThat(actual.totalCreatorCount()).isGreaterThanOrEqualTo(0L);
        }catch (Exception ignored){

        }

    }


}