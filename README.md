# Quota
Quota widget for Android

![Quota](https://github.com/asantibanez/Quota/blob/master/QuotaExample/captures/quota_preview.png)

To use, just add QuotaLayout to your XML layout file

```XML
<com.andressantibanez.android.quota.QuotaLayout
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  app:quotaTitle="Sales"
  app:quotaCompliedAmount="3300"
  app:quotaTotalAmount="10000"
  app:quotaCompliedColor="#ff6600"
  app:quotaTotalColor="#ccc"/>
```

To add Quota custom attributes you must add the following definition to the root view
```XML
xmlns:app="http://schemas.android.com/apk/res-auto"
```

You can customize colors for complied and total amounts with attributes ***quotaCompliedColor*** and ***quotaTotalColor***.


License
=======
Copyright 2015 Andrés Santibáñez

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
