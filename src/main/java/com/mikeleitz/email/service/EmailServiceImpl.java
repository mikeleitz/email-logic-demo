package com.mikeleitz.email.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.mikeleitz.email.domain.EmailAddress;
import com.mikeleitz.email.domain.InvalidEmailException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author leitz@mikeleitz.com
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

  @Override
  public Integer countDistinctEmails(@NonNull List<String> emails) throws InvalidEmailException {
    Integer returnValue = null;

    log.debug("Started counting distinct emails from list of [{}] emails.", emails.size());

    Set<String> uniqueEmailAddresses = new HashSet<>();
    for (String emailAddressString : emails) {
      EmailAddress emailAddress = new EmailAddress(emailAddressString);
      EmailAddress normalizeGmailAddress = normalizeGmailAddress(emailAddress);

      uniqueEmailAddresses.add(normalizeGmailAddress.getFullAddress());
    }

    returnValue = uniqueEmailAddresses.size();

    log.debug("Completed counting distinct emails from list of [{}] emails: [{}] of them are unique.", emails.size(), returnValue);

    return returnValue;
  }

  protected EmailAddress normalizeGmailAddress(@NonNull EmailAddress emailAddress) throws InvalidEmailException {
    log.trace("Started normalizing email address [{}].", emailAddress.getFullAddress());

    String normalizedRecipient = normalizeRecipient(emailAddress.getRecipient());
    String normalizedDomainName = normalizeDomainName(emailAddress.getDomainName());
    String topLevelDomain = emailAddress.getTopLevelDomain();

    EmailAddress normalizedEmailAddress = new EmailAddress(normalizedRecipient, normalizedDomainName, topLevelDomain);

    log.debug("Completed normalizing email address [{}].  Result: [{}].", emailAddress, normalizedDomainName);

    return normalizedEmailAddress;
  }

  protected String normalizeRecipient(@NonNull String recipient) {
    log.trace("Started normalizing email recipient [{}].  Removing all [.] characters and all characters after [+].", recipient);

    // Remove all dots in the recipient.
    String recipientResult = recipient.chars()
        .mapToObj(i -> (char) i)
        .filter(c -> c != '.')
        .collect(StringBuilder::new,
            StringBuilder::appendCodePoint,
            StringBuilder::append)
        .toString();

    // Remove everything after and including the +
    recipientResult = recipientResult.chars()
        .mapToObj(i -> (char) i)
        .takeWhile(c -> c != '+')
        .collect(StringBuilder::new,
            StringBuilder::appendCodePoint,
            StringBuilder::append)
        .toString();

    log.debug("Completed normalizing email recipient [{}].  Result: [{}]", recipient, recipientResult);

    return recipientResult;
  }

  protected String normalizeDomainName(@NonNull String domainName) {
    log.trace("Started checking to see if domain name [{}] equals googlemail.", domainName);

    String domainResult = domainName;
    if (StringUtils.equalsIgnoreCase("googlemail", domainResult)) {
      domainResult = "gmail";
      log.debug("Domain name [{}] matches googlemail.  This synonymous with gmail.", domainName);
    } else {
      log.trace("Domain name [{}] doesn't match googlemail.", domainName);
    }

    return domainResult;
  }
}
