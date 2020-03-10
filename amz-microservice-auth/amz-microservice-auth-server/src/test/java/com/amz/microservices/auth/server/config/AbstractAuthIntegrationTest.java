package com.amz.microservices.auth.server.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.amz.microservices.auth.dto.AuthDto;
import com.amz.microservices.auth.dto.AuthRequestDto;
import com.amz.microservices.auth.dto.CredentialsRequestDto;
import com.amz.microservices.auth.server.TestApplication;
import com.amz.microservices.common.object.enums.AccountType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.util.UUID;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@ActiveProfiles({"test", "it"})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class})
public abstract class AbstractAuthIntegrationTest {

  @Autowired
  protected MockMvc mockMvc;

  private ObjectMapper mapper;

  @Before
  public void init() {
    mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  protected String content(Object object) throws JsonProcessingException {
    return mapper.writeValueAsString(object);
  }

  protected <T> T toObject(String content, Class<T> valueTypeRef) throws IOException {
    return mapper.readValue(content, valueTypeRef);
  }

  protected void createCredential(String username, String password) throws Exception {
    final CredentialsRequestDto credentialsRequest = CredentialsRequestDto.builder()
        .userId(UUID.randomUUID())
        .username(username)
        .password(password)
        .accountType(AccountType.CUSTOMER)
        .build();

    mockMvc.perform(post("/credentials")
        .content(content(credentialsRequest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  protected AuthDto login(String username, String password) throws Exception {
    final AuthRequestDto authRequest = new AuthRequestDto(username, password);

    return toObject(mockMvc.perform(post("/login")
            .content(content(authRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(),
        AuthDto.class);
  }

}
