# EzPermission [ ![Download](https://jitpack.io/v/FarshadTahmasbi/EzPermission.svg) ](https://jitpack.io/#FarshadTahmasbi/EzPermission/0.1.4)

A tiny and easy to use kotlin library for managing android runtime permissions

---

# NOTE:
This library is no longer maintained, Android standard api does the magic:
https://developer.android.com/training/permissions/requesting

---

![](sample-gif.gif)

## Gradle setup

Make sure you have jitpack repository in the project level build.gradle:
  
  	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Then add dependency to the module:

	dependencies {
	        implementation 'com.github.FarshadTahmasbi:EzPermission:0.1.4'
	}


## How to use

It's simple, pass all permissions you want to ask for,
the result will be divided into: granted, denied and permanently denied!

    EzPermission.with(context)
        .permissions(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        .request { granted, denied, permanentlyDenied ->
            //Here you can check results...
        }

### ⭐️ If you liked it support me with your stars!
	
## License

    Copyright 2019 Farshad Tahmasbi
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.    

            
