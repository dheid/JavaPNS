package javapns.notification;

import org.json.JSONException;

/**
 * @deprecated The maximum payload length of PushNotificationPayload has been changed to 4000. Use {@link PushNotificationPayload instead}
 */
@Deprecated
public class PushNotificationBigPayload extends PushNotificationPayload {

  private PushNotificationBigPayload() {
    super();
  }

  private PushNotificationBigPayload(final String rawJSON) throws JSONException {
    super(rawJSON);
  }

  /**
   * @deprecated Use {@link PushNotificationPayload#complex}
   */
  @Deprecated
  public static PushNotificationBigPayload complex() {
    return new PushNotificationBigPayload();
  }

  /**
   * @deprecated Use {@link PushNotificationPayload#fromJSON(String)}
   */
  @Deprecated
  public static PushNotificationBigPayload fromJSON(final String rawJSON) throws JSONException {
    return new PushNotificationBigPayload(rawJSON);
  }

}
