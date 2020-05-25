package javapns.notification.transmission;

/**
 * An event listener for monitoring progress of NotificationThreads
 *
 * @author Sylvain Pedneault
 */
public interface NotificationProgressListener {
  void eventAllThreadsStarted(NotificationThreads notificationThreads);

  void eventThreadStarted(NotificationThread notificationThread);

  void eventThreadFinished(NotificationThread notificationThread);

  void eventConnectionRestarted(NotificationThread notificationThread);

  void eventAllThreadsFinished(NotificationThreads notificationThreads);

  void eventCriticalException(NotificationThread notificationThread, Exception exception);
}
