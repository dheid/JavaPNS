# JavaPNS

![Maven Verify](https://github.com/drjekyll-org/javapns/workflows/Maven%20Verify/badge.svg)

JavaPNS is a Java library to send notifications through the Apple Push Notification Service (APNS). It
allows to push notifications to iOS devices through APNS.

This is a modified fork of the [JavaPNS](https://github.com/mlaccetti/JavaPNS) fork from
[mlaccetti](https://github.com/mlaccetti). The original version comes from Sylvain Pedneault and is located
[here](http://code.google.com/p/javapns).

You'll find many examples on how to use this library [here](https://code.google.com/archive/p/javapns/wikis).

## Usage

JavaPNS is available on the Central Maven Repository. To use it in your project, please add the following dependency to your POM:

```xml
<dependency>
	<groupId>org.drjekyll</groupId>
	<artifactId>javapns</artifactId>
	<version>2.4.2</version>
</dependency>
```

## Development

To run the tests build a snapshot JAR, just run

    ./mvnw clean install

## Contributing

Please read [the contribution document](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/drjekyll-org/javapns/tags).

## License

This project is licensed under the LGPL License - see the [license](LICENSE.md) file for details.

## Release Notes

Version 2.4.1 released!

### 2.4.2

* Logging improvements (replace string concatenation)

### 2.4.1

* Deprecate PushNotificationBigPayload. Use PushNotificationPayload instead.

### 2.4.0

* Allow device tokens with more than 64 bytes
* Increase the maximum payload size to 4000 bytes
* Keep SSL connections alive
* Allow to add media attachment to PushNotificationPayload

### 2.3.4

* Make put method of Payload protected
* Make constructors of PushNotificationPayload protected
* Code cleanup
* Remove duplicate exceptions from getMessage in PushNotificationManager
* Remove throws clauses on methods for checked exceptions that will never be thrown

### 2.3.3

* Update dependencies: org.json and slf4j
* Little tweaks
* Replace deprecated SSLSession methods

### 2.3.2

* 1.8 tweaks
* General cleanup and overhaul

### 2.3.1

* PushNotificationBigPayload ```complex``` and ```fromJson``` methods fixed
* Fix to make trust store work on IBM JVM

### 2.3

* iOS>=8 bigger notification payload support (2 KB)
* iOS>=7 Silent push notifications support ("content-available":1)

