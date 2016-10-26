/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/
package com.github.michaelins.lightstatusbar;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.json.JSONException;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class LightStatusBar extends CordovaPlugin {

	private static final String TAG = "LightStatusBar";

	/**
	 * Executes the request and returns PluginResult.
	 *
	 * @param action
	 *            The action to execute.
	 * @param args
	 *            JSONArry of arguments for the plugin.
	 * @param callbackContext
	 *            The callback id used when calling back into JavaScript.
	 * @return True if the action was valid, false otherwise.
	 */
	@Override
	public boolean execute(final String action, final CordovaArgs args, final CallbackContext callbackContext)
			throws JSONException {

		LOG.v(TAG, "Executing action: " + action);
		final Activity activity = this.cordova.getActivity();
		final Window window = activity.getWindow();

		if ("isSupported".equals(action)) {
			this.cordova.getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || SystemBarTintManager.IsMiuiV6Plus()) {
						callbackContext.success("true");
					} else {
						callbackContext.success("false");
					}
				}
			});
			return true;
		}

		if ("setStatusBarColor".equals(action)) {
			this.cordova.getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					try {
					  webView.getView().setFitsSystemWindows(true);
						if (SystemBarTintManager.IsMiuiV6Plus()) {
							// MIUI6+ light status bar
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
								setTranslucentStatus(window, true);
							}
							SystemBarTintManager tintManager = new SystemBarTintManager(activity);
							tintManager.setStatusBarTintEnabled(true);
							tintManager.setStatusBarTintColor(Color.parseColor(args.getString(0)));
							tintManager.setStatusBarDarkMode(true, activity);
						} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
							// 6.0+ light status bar
							window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
							window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
							int uiOptions = window.getDecorView().getSystemUiVisibility()
									| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
							window.getDecorView().setSystemUiVisibility(uiOptions);
							setStatusBarBackgroundColor(args.getString(0));
						} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
							setStatusBarBackgroundColor(args.getString(0));
						}
					} catch (JSONException ignore) {
						LOG.e(TAG, "Invalid hexString argument, use f.i. '#777777'");
					}
				}
			});
			return true;
		}

		if ("enable".equals(action)) {
			this.cordova.getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
				}
			});
			return true;
		}

		if ("disable".equals(action)) {
			this.cordova.getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
				}
			});
			return true;
		}
		return false;
	}

	private void setStatusBarBackgroundColor(final String colorPref) {
		if (colorPref != null && !colorPref.isEmpty()) {
			final Window window = cordova.getActivity().getWindow();
			// Method and constants not available on all SDKs but we want to
			// be able to compile this code with any SDK
			window.clearFlags(0x04000000); // SDK 19:
											// WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.addFlags(0x80000000); // SDK 21:
											// WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			try {
				// Using reflection makes sure any 5.0+ device will work
				// without having to compile with SDK level 21
				window.getClass().getDeclaredMethod("setStatusBarColor", int.class).invoke(window,
						Color.parseColor(colorPref));
			} catch (IllegalArgumentException ignore) {
				LOG.e(TAG, "Invalid hexString argument, use f.i. '#999999'");
			} catch (Exception ignore) {
				// this should not happen, only in case Android removes this
				// method in a version > 21
				LOG.w(TAG, "Method window.setStatusBarColor not found for SDK level " + Build.VERSION.SDK_INT);
			}
		}
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void setTranslucentStatus(Window win, boolean on) {
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
}
