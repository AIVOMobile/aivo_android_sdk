Other languages: [Vietnamese](README_VI.md)

**Get Started**

GOSU Mobile Game SDK is the simplest way to integrate user and payment for
your game in GOSU system. This SDK provides solutions for payment
methods such as: SMS, Card.



**Steps to integrate SDK:**

​1. Install & import SDK into your project

​2. Configure SDK

​3. Integrate SDK

​4. Run SDK samples

<hr/>

# 1. Install & import SDK into your project
**1.1 Install SDK**
<ul>
	<li>Download GOSU mobile game SDK for Android here: 
	https://github.com/GOSUMobile/android_gosu_game_sdk/archive/master.zip </li>
	<li>If you use any git tool, you could GIT CLONE here: 
	https://github.com/GOSUMobile/android_gosu_game_sdk.git</li>

</ul>

**1.2 Import SDK to your project**
<br/>
Copy the below JAR files into libs folder of your project as picture:
<ul>
	<li>GosuSDK.jar</li>
	<li>google-play-services.jar</li>
	<li>libGoogleAnalyticsServices.jar</li>
	<li>app360sdk.jar</li>
	<li>gson-2.3.jar</li>
	<li>retrofit-1.8.0.jar</li>
	<br/>
	**As below picture**
</ul>
![add](https://github.com/GOSUMobile/android_gosu_game_sdk/blob/master/docs/images/import_sdk_screenshot.jpg)
<br/>
<br/>
# 2. Configure SDK
**2.1 Configure the file \<AndroidMainfest.xml\>**
- Open the file \<AndroidMainfest.xml\> in your project Android.
- Add following to configure for permission:
``` xml
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
	<uses-permission android:name="android.permission.INTERNET" />
	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	<uses-permission android:name="android.permission.READ_PHONE_STATE"/> 
	<uses-permission android:name="android.permission.GET_ACCOUNTS" />
	<uses-permission android:name="android.permission.WAKE_LOCK" />
	<uses-permission android:name="android.permission.VIBRATE" />
	<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
```
- Add permission for GCM notification
``` xml
	<permission android:name="<your_package_name>.permission.C2D_MESSAGE" 
				android:protectionLevel="signature" />
    <uses-permission android:name="<your_package_name>.permission.C2D_MESSAGE" />
	<receiver
        android:name="com.google.android.gcm.GCMBroadcastReceiver"
        android:permission="com.google.android.c2dm.permission.SEND" >
        <intent-filter>
            <action android:name="com.google.android.c2dm.intent.RECEIVE" />
            <action android:name="com.google.android.c2dm.intent.REGISTRATION" />
            <category android:name="<your_package_name>" />
        </intent-filter>
    </receiver>
    <service android:name="com.gusu.mobile.sdk.GCMIntentService" /> 
```
**2.2 Configure properties file**
- Copy the files */libs/app360.properties* and */clients/[client number]/gosu.properties* into assets folder in your project as 
below picutre

![add](https://github.com/GOSUMobile/android_gosu_game_sdk/blob/master/docs/images/assets_folder.jpg)

<br/>
# 3. Integrate SDK
Create an instance of Gosu class to call API methods in SDK
``` java
Gosu mGosu = new Gosu();
```

**3.1 Sign-in GOSU ID**

- Process view diagram
	
	![add](https://github.com/GOSUMobile/android_gosu_game_sdk/blob/master/docs/images/gosu_sdk_login.png)

Once calling API, it will return to Game Client user's information or failed.<br> 

- Calling API
``` java
	mGosu.login(new OnLoginListener(){
		@Override
	    public void onLoginSuccessful(String userId, String userName,int enablePayment, String access_token) {
		}

		@Override
	    public void onLoginFailed() {
	    }
	});
```
- The method *onLoginSuccessful(userID,userName,enablePayment)* will be invoked when login successfully
``` 
	{
		-UserID: 
			Recommend use this info, it will be easy to link account for gamers those who has ever played as guest
		-Username: You can use this info as gamer ID, but this info could not link account
		-EnablePayment: 
			0: using Google Payment
			1: using GOSU Payment
		-Access_token: this parameter is used to verify authorize/authentication if you need it
	}
```
- The method *onLoginFailed()* will be invoked when login failed

**Notes**
If you want to verify user authorized or authenticated, Please refer to below process view

![add](https://github.com/GOSUMobile/android_gosu_game_sdk/blob/master/docs/images/gosu_sdk_login2.png)

```
The below API is used for verifying user's authorization and authentication

URL: https://id.gosu.vn/auth/profile
Paramenter:access_token
Method: GET
Return: JSON as below:
{
 "UserID":"9120000001",
 "UserName":"testuser",
 "FullName":"Johnson Le",
 "Birthday":"11/11/1999",
 "Email":"testuser@gosu.vn"
}
```

**3.2 Sign-out GOSU ID**
- Calling API
``` java
	mGosu.logout(new OnLogoutListener(){
		@Override
	    public void onLogoutSuccessful() {

		}

		@Override
	    public void onLogoutFailed() {

	    }
	});
```
- The method *onLogoutSuccessful()* will be invoked when logout successfully

- The method *onLogoutFailed()* will be invoked when logout failed

**3.4 Payment**

- Process view diagram:

![add](https://github.com/GOSUMobile/android_gosu_game_sdk/blob/master/docs/images/gosu_sdk_payment.png)

Note: Your server game must provide us an **API to push money** to game.

- Calling API
``` java
	mGosu.payment(gameServerID, orderID,new OnPaymentListener(){
		@Override
	    public void onPaymentSuccessful(String Message) {

		}

		@Override
	    public void onPaymentFailed(String Message, int ErrorCode) {

	    }		
	}); 

```
```
	/**
	* API's parameters:
	*
	* @gameServerID: this is ID of your Game Server
	* @orderID: code of transaction that your system (Game Server) generate to record transaction's information
	/*	
```
- The method *onPaymentSuccessful(message)* will be invoked when payment successfully
```
	message: successful message
```
- The method *onPaymentFailed(message, errorCode)* will be invoked when payment failed
```
	message: failed message
	errorCode: code of error as below table

	Error Code  | Message
	------------| -----------------------------------------------
	 -7			| Expected game parameter
	 -6			| User is invalid
	 -5			| Actor in game is not existed
	 -4			| No. of GOSU exchange is invalid
	 -1			| Payment method is invalid
	 -2			| Account balance is not sufficient for payment
	 -888		| System error
	 -999		| mWork payment error
	 1			| Payment is successful

```

**3.5 SNS-Facebook**

3.5.1 Invite friends

``` java
	mGosu.inviteFacebook();
```

3.5.2 Share on wall

``` java
	mGosu.shareFacebook(String imagepath);
```
```
	/**
	* API's parameters:
	*
	* @imagepath: the path of the image which would like to share to your friends
	/
```

<br/>
<br/>
# 4. Run SDK Sample

Once you download SDK, you can import *samples* folder into Development IDE to view, build, and run Demo