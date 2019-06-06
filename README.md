# EasyPermission
[![](https://jitpack.io/v/farshadtahmasbi/easypermission.svg)](https://jitpack.io/#farshadtahmasbi/easypermission)

A tiny and easy to use kotlin library for managing android runtime permissions

![](sample-gif.gif)

## Gradle setup

Add JitPack repository to build.gradle root level

	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
  
Then add this to build.gradle in app module
  
  	dependencies {
	        implementation 'com.github.farshadtahmasbi:easypermission:0.1.1'
	}

## How to use

    EasyPermission.with(this)
        .permissions(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        .request { granted, denied, permanentlyDenied ->
            //Here you can check results...
        }
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

            
