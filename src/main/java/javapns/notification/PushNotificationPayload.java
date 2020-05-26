package javapns.notification;

import javapns.notification.exceptions.PayloadAlertAlreadyExistsException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.IllegalFormatException;
import java.util.List;

/**
 * A payload compatible with the Apple Push Notification Service.
 *
 * @author Maxime Peron
 * @author Sylvain Pedneault
 */
public class PushNotificationPayload extends Payload {

  private static final Logger logger = LoggerFactory.getLogger(PushNotificationPayload.class);

  /* Maximum total length (serialized) of a payload */
  private static final int MAXIMUM_PAYLOAD_LENGTH = 4000;

  public static final String ALERT = "alert";

  /* The application Dictionary */
  private JSONObject apsDictionary;

  /**
   * Create a default payload with a blank "aps" dictionary.
   */
  protected PushNotificationPayload() {
    this.apsDictionary = new JSONObject();
    try {
      JSONObject payload = getPayload();
      if (!payload.has("aps")) {
        payload.put("aps", this.apsDictionary);
      }
    } catch (JSONException e) {
      logger.error(e.getMessage(), e);
    }
  }

  /**
   * Construct a Payload object from a JSON-formatted string.
   * If an aps dictionary is not included, one will be created automatically.
   *
   * @param rawJSON a JSON-formatted string (ex: {"aps":{"alert":"Hello World!"}} )
   * @throws JSONException thrown if a exception occurs while parsing the JSON string
   */
  protected PushNotificationPayload(String rawJSON) {
    super(rawJSON);
    try {
      JSONObject payload = getPayload();
      this.apsDictionary = payload.getJSONObject("aps");
      if (this.apsDictionary == null) {
        this.apsDictionary = new JSONObject();
        payload.put("aps", this.apsDictionary);
      }
    } catch (JSONException e) {
      logger.error(e.getMessage(), e);
    }
  }

  /**
   * Create a payload and immediately add an alert message, a badge and a sound.
   *
   * @param alert the alert message
   * @param badge the badge
   * @param sound the name of the sound
   */
  public PushNotificationPayload(String alert, int badge, String sound) {
    this();
    if (alert != null) {
      addAlert(alert);
    }
    addBadge(badge);
    if (sound != null) {
      addSound(sound);
    }
  }

  /**
   * Create a pre-defined payload with a simple alert message.
   *
   * @param message the alert's message
   * @return a ready-to-send payload
   */
  public static PushNotificationPayload alert(String message) {
    if (message == null) {
      throw new IllegalArgumentException("Alert cannot be null");
    }
    PushNotificationPayload payload = complex();
    try {
      payload.addAlert(message);
    } catch (JSONException e) {
      // empty
    }
    return payload;
  }

  /**
   * Create a pre-defined payload with a badge.
   *
   * @param badge the badge
   * @return a ready-to-send payload
   */
  public static PushNotificationPayload badge(int badge) {
    PushNotificationPayload payload = complex();
    try {
      payload.addBadge(badge);
    } catch (JSONException e) {
      // empty
    }
    return payload;
  }

  /**
   * Create a pre-defined payload with a sound name.
   *
   * @param sound the name of the sound
   * @return a ready-to-send payload
   */
  public static PushNotificationPayload sound(String sound) {
    if (sound == null) {
      throw new IllegalArgumentException("Sound name cannot be null");
    }
    PushNotificationPayload payload = complex();
    try {
      payload.addSound(sound);
    } catch (JSONException e) {
      logger.error(e.getMessage(), e);
    }
    return payload;
  }

  /**
   * Create a pre-defined payload with a simple alert message, a badge and a sound.
   *
   * @param message the alert message
   * @param badge   the badge
   * @param sound   the name of the sound
   * @return a ready-to-send payload
   */
  public static PushNotificationPayload combined(String message, int badge, String sound) {
    if (message == null && badge < 0 && sound == null) {
      throw new IllegalArgumentException("Must provide at least one non-null argument");
    }
    PushNotificationPayload payload = complex();
    try {
      if (message != null) {
        payload.addAlert(message);
      }
      if (badge >= 0) {
        payload.addBadge(badge);
      }
      if (sound != null) {
        payload.addSound(sound);
      }
    } catch (JSONException e) {
      logger.error(e.getMessage(), e);
    }
    return payload;
  }

  /**
   * Create a special payload with a useful debugging alert message.
   *
   * @return a ready-to-send payload
   */
  public static PushNotificationPayload test() {
    PushNotificationPayload payload = complex();
    payload.setPreSendConfiguration(1);
    return payload;
  }

  /**
   * Create an empty payload which you can configure later.
   * This method is usually used to create complex or custom payloads.
   * Note: the payload actually contains the default "aps"
   * dictionary required by APNS.
   *
   * @return a blank payload that can be customized
   */
  public static PushNotificationPayload complex() {
    return new PushNotificationPayload();
  }

  /**
   * Create a PushNotificationPayload object from a preformatted JSON payload.
   *
   * @param rawJSON a JSON-formatted string representing a payload (ex: {"aps":{"alert":"Hello World!"}} )
   * @return a ready-to-send payload
   * @throws JSONException if any exception occurs parsing the JSON string
   */
  public static PushNotificationPayload fromJSON(String rawJSON) {
    return new PushNotificationPayload(rawJSON);
  }

  /**
   * Add a badge.
   *
   * @param badge a badge number
   */
  public void addBadge(int badge) {
    logger.debug("Adding badge [{}]", badge);
    put("badge", badge, this.apsDictionary, true);
  }

  /**
   * Add a sound.
   *
   * @param sound the name of a sound
   */
  public void addSound(String sound) {
    logger.debug("Adding sound [{}]", sound);
    put("sound", sound, this.apsDictionary, true);
  }

  /**
   * Add a media attachment.
   *
   * @param mediaUrl the URL of the media
   */
  public void addMedia(String mediaUrl) {
    logger.debug("Adding mediaUrl [{}]", mediaUrl);
    put("mutable-content", 1, this.apsDictionary, false);
    put("my-attachment", mediaUrl, getPayload(), false);
  }

  /**
   * Add a simple alert message.
   * Note: you cannot add a simple and a custom alert in the same payload.
   *
   * @param alertMessage the alert's message
   */
  public void addAlert(String alertMessage) {
    String previousAlert = getCompatibleProperty(ALERT, String.class, "A custom alert (\"%s\") was already added to this payload");
    logger.debug(
      "Adding alert [{}]{}",
      alertMessage,
      previousAlert != null ? " replacing previous alert [" + previousAlert + "]" : ""
    );
    put(ALERT, alertMessage, this.apsDictionary, false);
  }

  /**
   * Get the custom alert object, creating it if it does not yet exist.
   *
   * @return the JSON object defining the custom alert
   * @throws JSONException if a simple alert has already been added to this payload
   */
  private JSONObject getOrAddCustomAlert() {
    JSONObject alert = getCompatibleProperty(ALERT, JSONObject.class, "A simple alert (\"%s\") was already added to this payload");
    if (alert == null) {
      alert = new JSONObject();
      put(ALERT, alert, this.apsDictionary, false);
    }
    return alert;
  }

  /**
   * Get the value of a given property, but only if it is of the expected class.
   * If the value exists but is of a different class than expected, an
   * exception is thrown.
   * <p>
   * This method simply invokes the other getCompatibleProperty method with the root aps dictionary.
   *
   * @param <T>              the property value's class
   * @param propertyName     the name of the property to get
   * @param expectedClass    the property value's expected (required) class
   * @param exceptionMessage the exception message to throw if the value is not of the expected class
   * @return the property's value
   */
  private <T> T getCompatibleProperty(String propertyName, Class<T> expectedClass, String exceptionMessage) {
    return getCompatibleProperty(propertyName, expectedClass, exceptionMessage, this.apsDictionary);
  }

  /**
   * Get the value of a given property, but only if it is of the expected class.
   * If the value exists but is of a different class than expected, an
   * exception is thrown.
   * <p>
   * This method is useful for properly supporting properties that can have a simple
   * or complex value (such as "alert")
   *
   * @param <T>              the property value's class
   * @param propertyName     the name of the property to get
   * @param expectedClass    the property value's expected (required) class
   * @param exceptionMessage the exception message to throw if the value is not of the expected class
   * @param dictionary       the dictionary where to get the property from
   * @return the property's value
   */
  @SuppressWarnings("unchecked")
  private static <T> T getCompatibleProperty(
    String propertyName,
    Class<T> expectedClass,
    String exceptionMessage,
    JSONObject dictionary
  ) {
    Object propertyValue = null;
    try {
      propertyValue = dictionary.get(propertyName);
    } catch (Exception e) {
      // empty
    }
    if (propertyValue == null) {
      return null;
    }
    if (propertyValue.getClass() == expectedClass) {
      return (T) propertyValue;
    }
    try {
      throw new PayloadAlertAlreadyExistsException(String.format(exceptionMessage, propertyValue));
    } catch (IllegalFormatException e) {
      throw new PayloadAlertAlreadyExistsException(exceptionMessage);
    }

  }

  /**
   * Create a custom alert (if none exist) and add a body to the custom alert.
   *
   * @param body the body of the alert
   * @throws JSONException if the custom alert cannot be added because a simple alert already exists
   */
  public void addCustomAlertBody(String body) {
    put("body", body, getOrAddCustomAlert(), false);
  }

  /**
   * Create a custom alert (if none exist) and add a custom text for the right button of the popup.
   *
   * @param actionLocKey the title of the alert's right button, or null to remove the button
   * @throws JSONException if the custom alert cannot be added because a simple alert already exists
   */
  public void addCustomAlertActionLocKey(String actionLocKey) {
    Object value = actionLocKey != null ? actionLocKey : JSONObject.NULL;
    put("action-loc-key", value, getOrAddCustomAlert(), false);
  }

  /**
   * Create a custom alert (if none exist) and add a loc-key parameter.
   *
   * @param locKey The loc-key property value
   * @throws JSONException if the custom alert cannot be added because a simple alert already exists
   */
  public void addCustomAlertLocKey(String locKey) {
    put("loc-key", locKey, getOrAddCustomAlert(), false);
  }

  /**
   * Create a custom alert (if none exist) and add sub-parameters for the loc-key parameter.
   *
   * @param args The loc-args parameter
   * @throws JSONException if the custom alert cannot be added because a simple alert already exists
   */
  public void addCustomAlertLocArgs(List<?> args) {
    put("loc-args", args, getOrAddCustomAlert(), false);
  }

  /**
   * Sets the content available.
   *
   * @param available Should the content be available?
   */
  public void setContentAvailable(boolean available) {
    if (available) {
      put("content-available", 1, this.apsDictionary, false);
    } else {
      remove("content-available", this.apsDictionary);
    }
  }

  /**
   * Return the maximum payload size in bytes.
   * For APNS payloads, this method returns 4000.
   *
   * @return the maximum payload size in bytes (4000)
   */
  @Override
  public int getMaximumPayloadSize() {
    return MAXIMUM_PAYLOAD_LENGTH;
  }

  @Override
  void verifyPayloadIsNotEmpty() {
    if (getPreSendConfiguration() != 0) {
      return;
    }

    if (toString().equals("{\"aps\":{}}")) {
      throw new IllegalArgumentException("Payload cannot be empty");
    }
  }
}
