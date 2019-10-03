package com.mikeleitz.email.service;

import java.util.List;
import com.mikeleitz.email.domain.InvalidEmailException;

/**
 * @author leitz@mikeleitz.com
 */
public interface EmailService {
  Integer countDistinctEmails(List<String> emails) throws InvalidEmailException;
}
