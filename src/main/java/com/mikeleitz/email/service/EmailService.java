package com.mikeleitz.email.service;

import java.util.List;
import com.mikeleitz.email.domain.InvalidEmailException;

/**
 * @author leitz@mikeleitz.com
 */
public interface EmailService {
  /**
   *
   * @param emails the list of emails to process
   * @return the number of unique email addresses considered by Gmail email rules
   * @throws InvalidEmailException
   */
  Integer countDistinctEmails(List<String> emails) throws InvalidEmailException;
}
