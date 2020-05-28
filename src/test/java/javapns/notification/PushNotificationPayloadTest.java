package javapns.notification;

import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class PushNotificationPayloadTest {

  private final PushNotificationPayload pushNotificationPayload = new PushNotificationPayload();

  @Test
  public void ensureMaximumPayloadIs4000Bytes() {

    assertThat(pushNotificationPayload.getMaximumPayloadSize(), is(4000));

  }

  @Test
  public void allowsToAddMediaAttachment() {

    pushNotificationPayload.addMedia("https://some.url.local/attachement");

    JSONObject payload = pushNotificationPayload.getPayload();
    JSONObject aps = payload.getJSONObject("aps");
    assertThat(aps.getInt("mutable-content"), is(1));
    assertThat(payload.getString("my-attachment"), is("https://some.url.local/attachement"));

  }

  @Test
  public void allowsToAddThreadId() {

    pushNotificationPayload.addThreadId("myThreadId");

    JSONObject payload = pushNotificationPayload.getPayload();
    JSONObject aps = payload.getJSONObject("aps");
    assertThat(aps.getString("thread-id"), is("myThreadId"));

  }

  @Test
  public void allowsToAddCategory() {

    pushNotificationPayload.addCategory("myCategory");

    JSONObject payload = pushNotificationPayload.getPayload();
    JSONObject aps = payload.getJSONObject("aps");
    assertThat(aps.getString("category"), is("myCategory"));

  }

  @Test
  public void allowsToTargetContentId() {

    pushNotificationPayload.addTargetContentId("myTargetContentId");

    JSONObject payload = pushNotificationPayload.getPayload();
    JSONObject aps = payload.getJSONObject("aps");
    assertThat(aps.getString("target-content-id"), is("myTargetContentId"));

  }

}
