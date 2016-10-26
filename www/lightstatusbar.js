/*
 *
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

/* global cordova */

var exec = require('cordova/exec');

var LightStatusBar = {

    isSupported: function (success) {
      exec(success, null, "LightStatusBar", "isSupported", []);
    },

    setStatusBarColor: function (hexString) {
      if (hexString.charAt(0) !== "#") {
        hexString = "#" + hexString;
      }

      if (hexString.length === 4) {
        var split = hexString.split("");
        hexString = "#" + split[1] + split[1] + split[2] + split[2] + split[3] + split[3];
      }

      exec(null, null, "LightStatusBar", "setStatusBarColor", [hexString]);
    },

    enable: function () {
        // dark text ( to be used on a light background )
        exec(null, null, "LightStatusBar", "enable", []);
    },

    disable: function () {
        // light text ( to be used on a dark background )
        exec(null, null, "LightStatusBar", "disable", []);
    }

};

module.exports = LightStatusBar;
