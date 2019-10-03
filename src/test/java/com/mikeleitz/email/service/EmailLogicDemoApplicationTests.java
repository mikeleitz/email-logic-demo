package com.mikeleitz.email.service;

import java.util.Arrays;
import com.mikeleitz.email.domain.InvalidEmailException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailLogicDemoApplicationTests {

  @Test
  public void contextLoads() {
  }

  @Test
  public void normalizeRecipient() {
    EmailServiceImpl emailService = new EmailServiceImpl();

    String recipient = emailService.normalizeRecipient("mleitz");
    Assert.assertEquals("mleitz", recipient);

    recipient = emailService.normalizeRecipient("m.l.e.i.t.z");
    Assert.assertEquals("mleitz", recipient);

    recipient = emailService.normalizeRecipient("mleitz+label");
    Assert.assertEquals("mleitz", recipient);

    recipient = emailService.normalizeRecipient("mleitz+");
    Assert.assertEquals("mleitz", recipient);

    recipient = emailService.normalizeRecipient("m.l.e.i.t.z");
    Assert.assertEquals("mleitz", recipient);
  }

  @Test
  public void normalizeDomain() {
    EmailServiceImpl emailService = new EmailServiceImpl();

    String domain = emailService.normalizeDomainName("example");
    Assert.assertEquals("example", domain);

    domain = emailService.normalizeDomainName("gmail");
    Assert.assertEquals("gmail", domain);

    domain = emailService.normalizeDomainName("googlemail");
    Assert.assertEquals("gmail", domain);
  }

  @Test
  public void countEmails() throws InvalidEmailException {
    EmailServiceImpl emailService = new EmailServiceImpl();

    Integer uniqueEmails = emailService.countDistinctEmails(Arrays.asList(new String[] { "mleitz@gmail.com"}));
    Assert.assertEquals((int) 1, (int) uniqueEmails);

    uniqueEmails = emailService.countDistinctEmails(Arrays.asList(new String[] { "mleitz@gmail.com", "mleitz@googlemail.com", "mleitz+label@gmail.com", "ml.e.itz@gmail.com", "ml.e.itz+label@gmail.com" }));
    Assert.assertEquals((int) 1, (int) uniqueEmails);

    uniqueEmails = emailService.countDistinctEmails(Arrays.asList(new String[] { "mleitz2@gmail.com", "mleitz@gmail.com", "mleitz@googlemail.com", "mleitz+label@gmail.com", "ml.e.itz@gmail.com", "ml.e.itz+label@gmail.com" }));
    Assert.assertEquals((int) 2, (int) uniqueEmails);
  }
}
