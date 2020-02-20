# Titanium Firebase Dynamic Links

## Building

Simply run `appc run -p [ios|android] --build-only` which will compile and package your module.

## Install

To use your module locally inside an app you can copy the zip file into the app root folder and compile your app.
The file will automatically be extracted and copied into the correct `modules/` folder.

## Project Usage

Register your module with your application by editing `tiapp.xml` and adding your module.
Example:
```xml
<modules>
  <module version="1.0.0" platform="android">com.pmitkowski.firebase.dynamiclinks</module>
</modules>
```
When you run your project, the compiler will combine your module along with its dependencies
and assets into the application.

## Example Usage

```js
var Firebase = require('com.pmitkowski.firebase.dynamiclinks');
Firebase.addEventListener('deeplink:new', (params) => {
  // handle received link
});
Firebase.addEventListener('deeplink:error', (error) => {
    console.error(error);
});
Firebase.handleDeepLink();
```
