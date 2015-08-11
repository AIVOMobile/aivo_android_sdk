package com.gosu.gosudemo;

import java.io.File;
import java.net.URLEncoder;

import vn.mog.app360.sdk.App360SDK;
import vn.mog.app360.sdk.InitListener;
import vn.mog.app360.sdk.scopedid.ScopedUser;
import vn.mog.app360.sdk.scopedid.SessionManager;
import vn.mog.app360.sdk.scopedid.SessionService;
import vn.mog.app360.sdk.scopedid.SessionManager.SessionCallback;

import com.gosu.mobile.sdk.DialogLogin.OnLoginListener;
import com.gosu.mobile.sdk.DialogLogout.OnLogoutListener;
import com.gosu.mobile.sdk.DialogPayment.OnPaymentListener;
import com.gosu.mobile.sdk.DialogPaymentOauth.OnPaymentOauthListener;
import com.gosu.mobile.sdk.Gosu;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class MainActivity extends Activity {

	Gosu mGosu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);
	
		final TextView tv_username = (TextView) this
				.findViewById(R.id.txt_username);
		tv_username.setText("UserName: ");
		
		final EditText edit_orderid = (EditText) this
				.findViewById(R.id.edit_orderid);

		final TextView tv_result = (TextView) this
				.findViewById(R.id.txt_result);
		tv_result.setText("Result: ");
		
		final Button btn_login = (Button) findViewById(R.id.btn_login);

		final Button btn_logout = (Button) findViewById(R.id.btn_logout);

		final Button btn_payment = (Button) findViewById(R.id.btn_payment);
		
		final Button btn_payment_oauth = (Button) findViewById(R.id.btn_payment_oauth);

		final Button btn_invite = (Button) findViewById(R.id.btn_facebook_invite);

		final Button btn_share = (Button) findViewById(R.id.btn_facebook_share);
		
		final Button btn_change_acc = (Button) findViewById(R.id.btn_change_account);
		
		final Button bt_charge_oauth = (Button)findViewById(R.id.btn_charge_oauth);

		btn_logout.setVisibility(View.INVISIBLE);
		btn_payment.setVisibility(View.INVISIBLE);
		btn_payment_oauth.setVisibility(View.INVISIBLE);
		btn_invite.setVisibility(View.INVISIBLE);
		btn_share.setVisibility(View.INVISIBLE);
		bt_charge_oauth.setVisibility(View.INVISIBLE);
		

		mGosu = new Gosu(this);

//		mGosu.login(new OnLoginListener() {
//			
//			@Override
//			public void onLoginSuccessful(String UserId, String UserName,
//					int EnablePayment, String accesstoken) {
//				tv_username.setText("UserName: " + UserName+"\nUserId:" +UserId +"\nEnablePayment:" + EnablePayment +"\nAccess Token: " + accesstoken);
//				btn_login.setVisibility(View.INVISIBLE);
//				btn_logout.setVisibility(View.VISIBLE);
//				btn_payment.setVisibility(View.VISIBLE);
//				btn_payment_oauth.setVisibility(View.VISIBLE);
//				btn_invite.setVisibility(View.VISIBLE);
//				btn_share.setVisibility(View.VISIBLE);
//				bt_charge_oauth.setVisibility(View.VISIBLE);
//				
//			}
//			
//			@Override
//			public void onLoginFailed() {
//				// TODO Auto-generated method stub
//				
//			}
//		});

		findViewById(R.id.btn_login).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mGosu.login(new OnLoginListener() {
					
					@Override
					public void onLoginSuccessful(String UserId, String UserName,
							int EnablePayment, String accesstoken) {
						tv_username.setText("UserName: " + UserName+"\nUserId:" +UserId +"\nEnablePayment:" + EnablePayment +"\nAccess Token: " + accesstoken);
						btn_login.setVisibility(View.INVISIBLE);
						btn_logout.setVisibility(View.VISIBLE);
						btn_payment.setVisibility(View.VISIBLE);
						btn_payment_oauth.setVisibility(View.VISIBLE);
						btn_invite.setVisibility(View.VISIBLE);
						btn_share.setVisibility(View.VISIBLE);
						bt_charge_oauth.setVisibility(View.VISIBLE);
						
					}
					
					@Override
					public void onLoginFailed() {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});

		findViewById(R.id.btn_logout).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mGosu.logout(new OnLogoutListener() {

					@Override
					public void onLogoutSuccessful() {
						tv_username.setText("UserName: ");
						btn_login.setVisibility(View.VISIBLE);
						btn_logout.setVisibility(View.INVISIBLE);
						btn_payment.setVisibility(View.INVISIBLE);
						btn_payment_oauth.setVisibility(View.INVISIBLE);
						btn_invite.setVisibility(View.INVISIBLE);
						btn_share.setVisibility(View.INVISIBLE);
						bt_charge_oauth.setVisibility(View.INVISIBLE);

					}

					@Override
					public void onLogoutFailed() {

					}
				});
			}
		});

		findViewById(R.id.btn_payment).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						mGosu.payment("serverid","orderid",new OnPaymentListener() {

							@Override
							public void onPaymentSuccessful(String Message) {}
							
							@Override
							public void onPaymentFailed(String Message,
									int ErrorCode) {}
							@Override
							public void onLinkAccountFailed(String UserId,
									String UserName, String AccessToken) {
								tv_username.setText("UserName: " + UserName+"\nUserId:" +UserId +"\nAccess Token: " + AccessToken);
								btn_login.setVisibility(View.INVISIBLE);
								btn_logout.setVisibility(View.VISIBLE);
								btn_payment.setVisibility(View.VISIBLE);
								btn_payment_oauth.setVisibility(View.VISIBLE);
								btn_invite.setVisibility(View.VISIBLE);
								btn_share.setVisibility(View.VISIBLE);
								bt_charge_oauth.setVisibility(View.VISIBLE);
							}

							@Override
							public void onLinkAccountSuccessful(String UserId,
									String UserName, String AccessToken) {
								tv_username.setText("UserName: " + UserName+"\nUserId:" +UserId +"\nAccess Token: " + AccessToken);
								btn_login.setVisibility(View.INVISIBLE);
								btn_logout.setVisibility(View.VISIBLE);
								btn_payment.setVisibility(View.VISIBLE);
								btn_payment_oauth.setVisibility(View.VISIBLE);
								btn_invite.setVisibility(View.VISIBLE);
								btn_share.setVisibility(View.VISIBLE);
								bt_charge_oauth.setVisibility(View.VISIBLE);
							}

						});
 
					}
				});
		
		
		findViewById(R.id.btn_payment_oauth).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String orderid = edit_orderid.getText().toString();
				String paydes = URLEncoder.encode("");
				//mGosu.payment_oauth(serviceid, servicekey, orderid, orderinfor, amount, listener);//m328.Qakpzw2zMK0857gU4COI
				//mGosu.payment_oauth("m379.DTPsgpRElG45aKqq9GtP2L8il8jWEp", "tUKjtux13qDnuJu8xT8m7OkogSDKOOPvJJltbT1u4SusXsIfu6",orderid,"product_0", 6, new OnPaymentOauthListener() {
				//mGosu.payment_oauth("m328.Qakpzw2zMK0857gU4COI", "P2VfzMpeRh8K86bKh5kwbMiGy",orderid,"product_0", 6, new OnPaymentOauthListener() {	
				mGosu.payment_oauth("m300.zr3HvCpPccoSKUBAmMKdD34Z9xrrqQ", "CyaWlhXbcf7Y7TuldJWLv8cPRbpNfe",orderid,"product_0", 6, new OnPaymentOauthListener() {
					@Override
					public void onPaymentSuccessful(String Message) {
						
						tv_result.setText(Message);
					}
					
					@Override
					public void onPaymentFailed(String Message, int ErrorCode) {
						tv_result.setText(Message+" / "+ErrorCode);
						
					}

					@Override
					public void onLinkAccountSuccessful(String UserId,
							String UserName, String AccessToken) {
						tv_username.setText("UserName: " + UserName+"\nUserId:" +UserId +"\nAccess Token: " + AccessToken);
						btn_login.setVisibility(View.INVISIBLE);
						btn_logout.setVisibility(View.VISIBLE);
						btn_payment.setVisibility(View.VISIBLE);
						btn_payment_oauth.setVisibility(View.VISIBLE);
						btn_invite.setVisibility(View.VISIBLE);
						btn_share.setVisibility(View.VISIBLE);
						bt_charge_oauth.setVisibility(View.VISIBLE);
						
					}


					@Override
					public void onLinkAccountFailed(String UserId,
							String UserName, String AccessToken) {
						tv_username.setText("UserName: " + UserName+"\nUserId:" +UserId +"\nAccess Token: " + AccessToken);
						btn_login.setVisibility(View.INVISIBLE);
						btn_logout.setVisibility(View.VISIBLE);
						btn_payment.setVisibility(View.VISIBLE);
						btn_payment_oauth.setVisibility(View.VISIBLE);
						btn_invite.setVisibility(View.VISIBLE);
						btn_share.setVisibility(View.VISIBLE);
						bt_charge_oauth.setVisibility(View.VISIBLE);
						
					}
				});
				
			}
			
		});

		findViewById(R.id.btn_facebook_invite).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						mGosu.InviteFacebook();
					}
				});

		findViewById(R.id.btn_facebook_share).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						File pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
						String[] listOfPicture = pictures.list();
						try {
							String imagename = listOfPicture[1];
							mGosu.shareFacebook("file://"+pictures.toString()+"/"+imagename+"/51.png");
						} catch (Exception e) {
							e.printStackTrace();
						}
						

					}
				});
		findViewById(R.id.btn_change_account).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mGosu.logout(new OnLogoutListener() {
					
					@Override
					public void onLogoutSuccessful() {
						mGosu.login(new OnLoginListener() {
							
							@Override
							public void onLoginSuccessful(String UserId, String UserName,
									int EnablePayment, String accesstoken) {
								tv_username.setText("UserName: " + UserName+"\nUserId:" +UserId +"\nEnablePayment:" + EnablePayment +"\nAccess Token: " + accesstoken);
								btn_login.setVisibility(View.INVISIBLE);
								btn_logout.setVisibility(View.VISIBLE);
								btn_payment.setVisibility(View.VISIBLE);
								btn_payment_oauth.setVisibility(View.VISIBLE);
								btn_invite.setVisibility(View.VISIBLE);
								btn_share.setVisibility(View.VISIBLE);
								bt_charge_oauth.setVisibility(View.VISIBLE);
								
							}
							
							@Override
							public void onLoginFailed() {
								// TODO Auto-generated method stub
								
							}
						});
						;
						
					}
					
					@Override
					public void onLogoutFailed() {
						// TODO Auto-generated method stub
						
					}
				});
				
			}
		});
		findViewById(R.id.btn_charge_oauth).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						mGosu.ChargeOauth(new OnPaymentOauthListener() {
							
							@Override
							public void onPaymentSuccessful(String Message) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onPaymentFailed(String Message, int ErrorCode) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onLinkAccountSuccessful(String UserId,
									String UserName, String AccessToken) {
								tv_username.setText("UserName: " + UserName+"\nUserId:" +UserId +"\nAccess Token: " + AccessToken);
								btn_login.setVisibility(View.INVISIBLE);
								btn_logout.setVisibility(View.VISIBLE);
								btn_payment.setVisibility(View.VISIBLE);
								btn_payment_oauth.setVisibility(View.VISIBLE);
								btn_invite.setVisibility(View.VISIBLE);
								btn_share.setVisibility(View.VISIBLE);
								bt_charge_oauth.setVisibility(View.VISIBLE);
								
							}

							@Override
							public void onLinkAccountFailed(String UserId,
									String UserName, String AccessToken) {
								tv_username.setText("UserName: " + UserName+"\nUserId:" +UserId +"\nAccess Token: " + AccessToken);
								btn_login.setVisibility(View.INVISIBLE);
								btn_logout.setVisibility(View.VISIBLE);
								btn_payment.setVisibility(View.VISIBLE);
								btn_payment_oauth.setVisibility(View.VISIBLE);
								btn_invite.setVisibility(View.VISIBLE);
								btn_share.setVisibility(View.VISIBLE);
								bt_charge_oauth.setVisibility(View.VISIBLE);
								
							}
						});

						//mGosu.SyncAccount();
					}
				});
	}
}
