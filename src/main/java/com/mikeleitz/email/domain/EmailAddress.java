package com.mikeleitz.email.domain;

import lombok.Data;
import lombok.NonNull;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * @author leitz@mikeleitz.com
 */
@Data
public class EmailAddress {
  @NonNull private String fullAddress;
  @NonNull private String recipient;
  @NonNull private String domainName;
  @NonNull private String topLevelDomain;

  public EmailAddress(@NonNull String fullAddress) throws InvalidEmailException {
    if (isValid(fullAddress)) {
      this.fullAddress = fullAddress;
      recipient = parseRecipient(fullAddress);
      domainName = parseDomain(fullAddress);
      topLevelDomain = parseTopLevelDomain(fullAddress);
    } else {
      throw new InvalidEmailException(fullAddress);
    }
  }

  public EmailAddress(@NonNull String recipient, @NonNull String domainName, @NonNull String topLevelDomain) throws InvalidEmailException {
    this.recipient = recipient;
    this.domainName = domainName;
    this.topLevelDomain = topLevelDomain;

    fullAddress = recipient + "@" + domainName + "." + topLevelDomain;

    if (!isValid(fullAddress)) {
      throw new InvalidEmailException(fullAddress);
    }
  }

  protected Boolean isValid(String fullAddress) {
    return EmailValidator.getInstance().isValid(fullAddress);
  }

  protected String parseRecipient(String fullAddress) {
    String recipient = fullAddress.substring(0, fullAddress.indexOf('@'));
    return recipient;
  }

  protected String parseDomain(String fullAddress) {
    String domain = fullAddress.substring(fullAddress.indexOf('@') + 1, fullAddress.lastIndexOf('.'));
    return domain;
  }

  protected String parseTopLevelDomain(String fullAddress) {
    String tld = fullAddress.substring(fullAddress.lastIndexOf('.') + 1, fullAddress.length());
    return tld;
  }
}
