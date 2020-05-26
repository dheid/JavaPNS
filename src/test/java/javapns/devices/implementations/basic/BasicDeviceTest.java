package javapns.devices.implementations.basic;

import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import org.junit.Test;

public class BasicDeviceTest {

  @Test(expected = InvalidDeviceTokenFormatException.class)
  public void failIfTokenIsNull() throws InvalidDeviceTokenFormatException {
    BasicDevice.validateTokenFormat(null);
  }

  @Test
  public void allowTokenLongerThan64Bytes() throws InvalidDeviceTokenFormatException {
    BasicDevice.validateTokenFormat("looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong");
  }

}
