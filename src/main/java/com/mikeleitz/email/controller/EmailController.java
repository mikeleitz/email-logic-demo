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

  /**
   * Web service that takes in a list of email addresses and returns an integer indicating the number of unique
   * email addresses.
   *
   * This service handles the Gmail style email authoring.
   *  1. All dots '.' in the recipient's name are ignored.
   *  2. All characters after and including plus '+' in the recipient's name are ignored.  e.g. mleitz+label@gmail.com
   *     is considered mleitz@gmail.com.
   *  3. Gmail has two different domains for its email: gmail.com and googlemail.com.  This service handle this
   *     case too.  e.g. mleitz@googlemail.com is considered mleitz@gmail.com.
   *
   * @param emails list of email address to process
   * @return the number of unique gmail addresses
   * @throws InvalidEmailException any email in the list that's not valid will reject the whole request
   */
  @PostMapping(path = "/unique",
      produces= MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Map<String, Object> countDistinctEmails(@RequestBody List<String> emails) throws InvalidEmailException {
    Map<String, Object> returnValue = new HashMap<>(1);

    returnValue.put("uniqueEmails", emailService.countDistinctEmails(emails));

    return returnValue;
  }
}
