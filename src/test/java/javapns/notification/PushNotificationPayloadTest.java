package javapns.notification;

import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.json.JSONObject;
import org.junit.Test;


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
  public void allowsToAddTargetContentId() {

    pushNotificationPayload.addTargetContentId("myTargetContentId");

    JSONObject payload = pushNotificationPayload.getPayload();
    JSONObject aps = payload.getJSONObject("aps");
    assertThat(aps.getString("target-content-id"), is("myTargetContentId"));

  }

  @Test
  public void allowsToSetCustomAlertTitle() {

    pushNotificationPayload.addCustomAlertTitle("customAlertTitle");

    JSONObject payload = pushNotificationPayload.getPayload();
    JSONObject aps = payload.getJSONObject("aps");
    JSONObject alert = aps.getJSONObject("alert");
    assertThat(alert.getString("title"), is("customAlertTitle"));

  }

  @Test
  public void allowsToSetCustomAlertSubtitle() {

    pushNotificationPayload.addCustomAlertSubtitle("customAlertSubTitle");

    JSONObject payload = pushNotificationPayload.getPayload();
    JSONObject aps = payload.getJSONObject("aps");
    JSONObject alert = aps.getJSONObject("alert");
    assertThat(alert.getString("subtitle"), is("customAlertSubTitle"));

  }

  @Test
  public void allowsToSetCustomAlertLaunchImage() {

    pushNotificationPayload.addCustomAlertLaunchImage("customAlertLaunchImage");

    JSONObject payload = pushNotificationPayload.getPayload();
    JSONObject aps = payload.getJSONObject("aps");
    JSONObject alert = aps.getJSONObject("alert");
    assertThat(alert.getString("launch-image"), is("customAlertLaunchImage"));

  }

  @Test
  public void allowsToSetCustomAlertTitleLocKey() {

    pushNotificationPayload.addCustomAlertTitleLocKey("customAlertTitleLocKey");

    JSONObject payload = pushNotificationPayload.getPayload();
    JSONObject aps = payload.getJSONObject("aps");
    JSONObject alert = aps.getJSONObject("alert");
    assertThat(alert.getString("title-loc-key"), is("customAlertTitleLocKey"));

  }

  @Test
  public void allowsToSetCustomAlertTitleLocArgs() {

    pushNotificationPayload.addCustomAlertTitleLocArgs(singleton("customAlertTitleLocArgs"));

    JSONObject payload = pushNotificationPayload.getPayload();
    JSONObject aps = payload.getJSONObject("aps");
    JSONObject alert = aps.getJSONObject("alert");
    assertThat(alert.get("title-loc-args"), is(singleton("customAlertTitleLocArgs")));

  }

  @Test
  public void allowsToSetCustomAlertSubtitleLocKey() {

    pushNotificationPayload.addCustomAlertSubtitleLocKey("customAlertSubtitleLocKey");

    JSONObject payload = pushNotificationPayload.getPayload();
    JSONObject aps = payload.getJSONObject("aps");
    JSONObject alert = aps.getJSONObject("alert");
    assertThat(alert.getString("subtitle-loc-key"), is("customAlertSubtitleLocKey"));

  }

  @Test
  public void allowsToSetCustomAlertSubtitleLocArgs() {

    pushNotificationPayload.addCustomAlertSubtitleLocArgs(singleton("customAlertSubtitleLocArgs"));

    JSONObject payload = pushNotificationPayload.getPayload();
    JSONObject aps = payload.getJSONObject("aps");
    JSONObject alert = aps.getJSONObject("alert");
    assertThat(alert.get("subtitle-loc-args"), is(singleton("customAlertSubtitleLocArgs")));

  }

}
