package javapns.devices.implementations.basic;

import javapns.devices.Device;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;

import java.sql.Timestamp;

/**
 * This class is used to represent a device (iPhone)
 *
 * @author Maxime Peron
 */
public class BasicDevice implements Device {

  /*
   * An id representing a particular device.
   *
   * Note that this is a local reference to the device,
   * which is not related to the actual device UUID or
   * other device-specific identification. Most of the
   * time, this deviceId should be the same as the token.
   */
  private String deviceId;

  /* The device token given by Apple Server, hexadecimal form, 64bits length */
  private String token;

  /* The last time a device registered */
  private Timestamp lastRegister;

  public BasicDevice() {
    // empty
  }

  /**
   * Default constructor.
   *
   * @param token The device token
   */
  public BasicDevice(String token) throws InvalidDeviceTokenFormatException {
    this(token, true);
  }

  private BasicDevice(String token, boolean validate) throws InvalidDeviceTokenFormatException {
    this.deviceId = token;
    this.token = token;
    try {
      this.lastRegister = new Timestamp(System.currentTimeMillis());
    } catch (RuntimeException e) {
      // empty
    }

    if (validate) {
      validateTokenFormat(token);
    }
  }

  /**
   * Constructor
   *
   * @param id    The device id
   * @param token The device token
   */
  BasicDevice(String id, String token, Timestamp register) throws InvalidDeviceTokenFormatException {
    this.deviceId = id;
    this.token = token;
    this.lastRegister = register;

    validateTokenFormat(token);

  }

  public static void validateTokenFormat(String token) throws InvalidDeviceTokenFormatException {
    if (token == null) {
      throw new InvalidDeviceTokenFormatException("Device token may not be null");
    }
  }

  public void validateTokenFormat() throws InvalidDeviceTokenFormatException {
    validateTokenFormat(token);
  }

  /**
   * Getter
   *
   * @return the device id
   */
  @Override
  public String getDeviceId() {
    return deviceId;
  }

  /**
   * Setter
   *
   * @param id the device id
   */
  @Override
  public void setDeviceId(String id) {
    this.deviceId = id;
  }

  /**
   * Getter
   *
   * @return the device token
   */
  @Override
  public String getToken() {
    return token;
  }

  /**
   * Setter the device token
   *
   * @param token The device token
   */
  @Override
  public void setToken(String token) {
    this.token = token;
  }

  /**
   * Getter
   *
   * @return the last register
   */
  @Override
  public Timestamp getLastRegister() {
    return lastRegister;
  }

  @Override
  public void setLastRegister(Timestamp lastRegister) {
    this.lastRegister = lastRegister;
  }
}
