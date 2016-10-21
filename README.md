---
title: Light Statusbar
description: Plugin for Cordova (or PhoneGap) 3.0+ to enable light status bar on Android 6.0+.
---
<!---
# license: Licensed to the Apache Software Foundation (ASF) under one
#         or more contributor license agreements.  See the NOTICE file
#         distributed with this work for additional information
#         regarding copyright ownership.  The ASF licenses this file
#         to you under the Apache License, Version 2.0 (the
#         "License"); you may not use this file except in compliance
#         with the License.  You may obtain a copy of the License at
#
#           http://www.apache.org/licenses/LICENSE-2.0
#
#         Unless required by applicable law or agreed to in writing,
#         software distributed under the License is distributed on an
#         "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
#         KIND, either express or implied.  See the License for the
#         specific language governing permissions and limitations
#         under the License.
-->

# cordova-plugin-statusbar

LightStatusbar
======

> The `LightStatusbar` object can enable/disable light status bar on Android 6.0+.

## Installation

    cordova plugin add https://github.com/michaelins/cordova-plugin-light-statusbar.git

Methods
-------
This plugin defines global `LightStatusBar` object.

Although in the global scope, it is not available until after the `deviceready` event.

    document.addEventListener("deviceready", onDeviceReady, false);
    function onDeviceReady() {
        console.log(LightStatusBar);
    }

- LightStatusBar.enable
- LightStatusBar.disable

LightStatusBar.enable
=================

On Android 6.0+, make the statusbar suitable for light background(dark font/icon color).

    LightStatusBar.enable();

Description
-----------

On Android 6.0+, make the statusbar suitable for light background(dark font/icon color).


Supported Platforms
-------------------

- Android 6.0+

Quick Example
-------------

    LightStatusBar.enable();

LightStatusBar.disable
=================

On Android 6.0+, make the statusbar suitable for dark background(light font/icon color).

    LightStatusBar.disable();

Description
-----------

On Android 6.0+, make the statusbar suitable for dark background(light font/icon color).


Supported Platforms
-------------------

- Android 6.0+

Quick Example
-------------

    LightStatusBar.disable();