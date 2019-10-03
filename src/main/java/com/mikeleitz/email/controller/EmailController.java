package com.mikeleitz.email.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mikeleitz.email.domain.InvalidEmailException;
import com.mikeleitz.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leitz@mikeleitz.com
 */
@Slf4j
@RestController
@RequestMapping("/email")
public class EmailController {

  private EmailService emailService;

  public EmailController(@Autowired EmailService emailService) {
    this.emailService = emailService;
  }

  @PostMapping(path = "/unique",
      produces= MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Map<String, Object> countDistinctEmails(@RequestBody List<String> emails) throws InvalidEmailException {
    Map<String, Object> returnValue = new HashMap<>(1);

    returnValue.put("uniqueEmails", emailService.countDistinctEmails(emails));

    return returnValue;
  }
}
