package javapns.notification;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class PushNotificationPayloadTest {

  private final PushNotificationPayload pushNotificationPayload = new PushNotificationPayload();

  @Test
  public void ensureMaximumPayloadIs4000Bytes() {

    assertThat(pushNotificationPayload.getMaximumPayloadSize(), is(4000));

  }
}
