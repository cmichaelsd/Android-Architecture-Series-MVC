# MVC DEMO - Android Architecture Series Part 1

### Overview
MVC is an outdated architecture pattern for Android officially since 2017.
The architecture is based on solutions that were used for web applications however the nature of Android SDK makes this architecture unstable for a few reasons:
- You can not separate the controller logic from the view logic, they will always end up co-existing.
- Because controller and view logic can not be separated testing can not properly be performed.
- There is a limitation to how far you can separate concerns.