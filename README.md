# Chargie
**This project has been failed! Only for documentation**

Chargie is an Android app which optimizes the charging process. You can set a desired power level and as soon as power is plugged in, it turns all unecessary components (e.g. WiFi, BT) off and kills all background processes until the desired power level is reached. Afterwards the previous state is restored.

## Why it failed?
Android 6 is pretty restrictrive about what apps can and cannot perfom. The following list comprises a list of all restrictions causing the failure of this app:

* Switching to airplane mode is not allowed
* Getting a list of running tasks is not possible
* Reducing performane (as done by Android's power saving mode) is not possible

## What have we learned?
*Figure out if the API matches the design requirements before doing the actual implementation!*
