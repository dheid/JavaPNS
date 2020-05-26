package javapns.notification;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.net.ssl.SSLSocket;

import java.net.Socket;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

public class ConnectionToNotificationServerTest {

  @Rule
  public final WireMockRule wireMockRule = new WireMockRule(options().dynamicHttpsPort());

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Mock
  private AppleNotificationServer appleNotificationServer;

  @InjectMocks
  private ConnectionToNotificationServer connectionToNotificationServer;

  @Test
  public void ensureSslSocketWillKeptAlive() throws Exception {

    given(appleNotificationServer.getNotificationServerHost()).willReturn("localhost");
    given(appleNotificationServer.getNotificationServerPort()).willReturn(wireMockRule.httpsPort());

    Socket sslSocket = connectionToNotificationServer.getSSLSocket();

    assertThat(sslSocket.getKeepAlive(), is(true));

  }


}
