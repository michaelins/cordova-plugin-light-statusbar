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

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class LightStatusBar extends CordovaPlugin {

    private static final String TAG = "LightStatusBar";

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false otherwise.
     */
    @Override
    public boolean execute(final String action, final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {

        LOG.v(TAG, "Executing action: " + action);
        final Activity activity = this.cordova.getActivity();
        final Window window = activity.getWindow();

        if ("enable".equals(action)) {
            this.cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        int uiOptions = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                        window.getDecorView().setSystemUiVisibility(uiOptions);
                    }
                }
            });
            return true;
        }

        if ("disable".equals(action)) {
            this.cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        int uiOptions = window.getDecorView().getSystemUiVisibility();
                        uiOptions &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                        window.getDecorView().setSystemUiVisibility(uiOptions);
                    }
                }
            });
            return true;
        }
        return false;
    }
}
