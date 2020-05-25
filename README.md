## JavaPNS

Apple Push Notification Service Provider for Java

Fork of JavaPNS to include Maven support - http://code.google.com/p/javapns/

Java 11+ compatible

### Updates

Version 2.3.3 released!

#### 2.3.3 Changes
* Update dependencies: org.json and slf4j
* Little tweaks
* Replace deprecated SSLSession methods

#### 2.3.2 Changes
* 1.8 tweaks
* General cleanup and overhaul

#### 2.3.1 Changes
* PushNotificationBigPayload ```complex``` and ```fromJson``` methods fixed
* Fix to make trust store work on IBM JVM

#### 2.3 Changes
* iOS>=8 bigger notification payload support (2KB)
* iOS>=7 Silent push notifications support ("content-available":1)

### Installation through Central Maven Repository
javapns is available on the Central Maven Repository.
To use javapns in your project, please add the following dependency to your pom.xml file:
```
<dependency>
	<groupId>org.drjekyll</groupId>
	<artifactId>javapns</artifactId>
	<version>2.3.3</version>
</dependency>
```

### Cutting a Release

`mvn -Drelease=true ...`
