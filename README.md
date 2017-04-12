Android开发入门
===

### 环境搭建
#### 搭建JDK环境
* 下载并安装[JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)目前最新的React Native需要1.8的JDK环境  

* 配置Java环境变量（windows）
  
为了配置JDK的系统变量环境，我们需要设置三个系统变量，分别是`JAVA_HOME`，`Path`和`CLASSPATH`。下面是这三个变量的设置方法。

*JAVA_HOME*  
先设置这个系统变量名称，变量值为JDK在你电脑上的安装路径：`C:\Program Files\Java\jdk1.8.0_101`。创建好后则可以利用`%JAVA_HOME%`作为JDK安装目录的统一引用路径。
 
*Path*  
PATH属性已存在，可直接编辑，在原来变量后追加：`;%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin` 。
 
*CLASSPATH*   
设置系统变量名为：`CLASSPATH`  变量值为：`.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar` 。
注意变量值字符串前面有一个"."表示当前目录，设置`CLASSPATH` 的目的，在于告诉Java执行环境，在哪些目录下可以找到您所要执行的Java程序所需要的类或者包。

* 配置Java环境变量(Mac)

编辑~目录下的`.bash_profile文件`，追加以下内容：  

```
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH
```
#### 搭建Android环境	
* 下载并安装[Android Studio](https://developer.android.com/studio/index.html)
* 下载并安装Android SDK  
当前环境下各个版本的用户占有率  
![](http://7xntab.com1.z0.glb.clouddn.com/image/share/apipercent.jpeg)
* 配置Android环境变量

```
export ANDROID_HOME=~/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
export ANDROID_SDK=~/Library/Android/sdk
```
使用 `source ~/.bash_profile` 可以使环境变量立即生效

### 搭建工程

打开Android Studio，选择*Start a new Android Studio project*，输入Application name, 点next进入到平台选择页面，选择Phone and Tablet，这里可以看到各个版本的覆盖率，最新版本的SDK功能最丰富，但是覆盖率也最低。如果使用最新版本的React Native(0.44)，需要Android 6.0的SDK(API23)。最原始的版本，需要Android 4.1的SDK(API21)。

[AppDemo](https://github.com/dlutfrank/AppDemo.git)

#### 工程目录结构
Andorid视图和Project视图之间的切换，一般使用Android视图，在查看一些生成的lib文件时候，会用到Project视图,下面的内容基于Android视图描述。

* manifests目录  
AndroidManifest.xml，清单文件，应用程序的入口点，需要的权限，程序中包含的组件。

* java目录  
源文件目录，测试目录，Android测试目录

* res目录  
资源目录，`layout`布局，`drawable`图片，`mipmap`icon，`value`字符串，颜色，样式等等。

* Gradle Scripts
构建脚本目录，每个project和module都有对应的build.gradle文件与之对应。

### 调试
* 打开USB调试  
Settings > Developer options，在设备上启用 USB debugging。  

点击工具栏的Run为运行程序，点击Debug为调试程序。

已经运行的程序，可以选择Attach debugger to Android process 

### 基本开发流程
#### 常用到的组件

* Activity  
生命周期，页面跳转

* layout  
LinerLayout，RelativeLayout，FrameLayout

* View
ViewGroup View
gravity layout_gravity

* Intent  
组件之间传递信息

* Service

[更多内容](https://developer.android.com/index.html)

#### module创建

Android library(可以带资源，打包为aar)

Java library(打包为jar)

#### 引入第三方资源
* 引入远程第三方资源  
_私有库资源_  
需要在工程的build.gradle中加入私有库地址。  

```
allprojects {
	repositories {
		maven { url "http://mvn.ms.netease.com/nexus/content/repositories/netease-lede/" }
		maven { url "$rootDir/../RN/node_modules/react-native/android" }
	}
}
```

_私有库 & 公共库_  
在对应模块的build.gradle中引入第三方库

```
dependencies {
    testCompile 'org.mockito:mockito-core:2.2.9'
    compile 'org.greenrobot:eventbus:3.0.0'
    provided 'com.jakewharton:butterknife:8.4.0'
}
```

* 引入本地资源

```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile files('libs/foo.jar', 'libs/bar.jar')
    provided project(path: ':util')
    compile project(path: ':common')
    debugCompile project(path: ':my-library-module', configuration: 'debug')
    releaseCompile project(path: ':my-library-module', configuration: 'release')
}

```

### 兼容性处理

#### 程序兼容性处理

API Level来进行版本控制

```
android:minSdkVersion="integer"
android:targetSdkVersion="integer" //默认值同min
android:maxSdkVersion="integer"
```

低于min，程序无法安装，低于target，按照兼容模式的主题运行，高于target，不考虑兼容模式

如果minSdkVersion版本低于API函数的版本，则需要使用TargetApi的方式来保证编译通过。同时要使用兼容代码保证在最低版本上运行。

```
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
private String getInstance(Bundle bundle) {
    String result = null;
    if (bundle != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            result = bundle.getString(ID, "99010");
        } else {
            result = bundle.getString(ID);
        }
    }
    return result;
}
```

一般通过Build类来进行判断，VERSION包含当前系统版本信息，其中就包含SDK的版本信息，用SDK_INT来表示。[VERSION_CODES](https://developer.android.com/reference/android/os/Build.VERSION_CODES.html)中包含的是一系列版本常量。不同版本的特性可以[看这里](https://developer.android.com/guide/topics/manifest/uses-sdk-element.html)。

#### 打包兼容性处理

Android Studio使用Gradle来进行构建，Gradle Plugin的版本必须和Gradle的版本匹配，不然会构建失败，Gradle Plugin的版本和Gralde版本对应关系参考[Android Plugin for Gradle Release Notes](https://developer.android.com/studio/releases/gradle-plugin.html)。

更多关于Gradle相关信息，参考[Gradle版本管理](http://hucaihua.cn/2016/09/27/Gradle_upgrade/)

### 打包发布

渠道控制

```
  productFlavors {
    free {
      applicationId 'com.example.myapp.free'
    }
	
    test {
    	applicationIdSuffix '.test'
    }
  }
```

* 生成Android签名证书

如果你已经有签名证书可以绕过此步骤。 
签名APK需要一个证书用于为APP签名，生成签名证书可以Android Studio以可视化的方式生成，也可以使用终端采用命令行的方式生成。

`keytool -genkey -alias android.keystore -keyalg RSA -validity 20000 -keystore android.keystore `

-alias keystore的别名  
-keyalg 使用的算法
-validity 有效天数

查看keystore信息

`keytool -list -v -keystore android.keystore`

debug-release和签名控制

```
    signingConfigs {
        release {
            storeFile new File(file(".").getAbsolutePath() + "/../keystore/com.swx.keystore").getAbsoluteFile()
            storePassword System.getenv("KSTOREPWD")
            keyAlias 'release.key.keystore'
            keyPassword System.getenv("KSTOREPWD")
        }
    }

    buildTypes {
        release {
            minifyEnabled true
            shrinkResources false
            zipAlignEnabled true
            signingConfig signingConfigs.release
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
```

* 签名注意事项

>
应用升级：当系统安装应用的更新时，它会比较新版本和现有版本中的证书。如果证书匹配，则系统允许更新。如果您使用不同的证书签署新版本，则必须为应用分配另一个软件包名称 - 在此情况下，用户将新版本作为全新应用安装。
>
应用模块化：Android 允许通过相同证书签署的多个 APK 在同一个进程中运行（如果应用请求这样），以便系统将它们视为单个应用。通过此方式，您可以在模块中部署您的应用，且用户可以独立更新每个模块。
>
通过权限共享代码/数据：Android 提供基于签名的权限执行，以便应用可以将功能展示给使用指定证书签署的另一应用。通过使用同一个证书签署多个 APK 并使用基于签名的权限检查功能，您的应用可采用安全的方式共享代码和数据。

#### 手动签名 
 
* 使用 zipalign 优化未签署的 APK：

`zipalign -v -p 4 my-app-unaligned.apk my-app.apk`
 
* 通过 apksigner 使用您的私钥签署 APK：

`apksigner sign --ks my-release-key.jks my-app.apk`

* 验证 APK 是否已签署：

`apksigner verify my-app.apk`

#### 使用网易测试服务

[自动分发API](http://app.hz.netease.com/docs/api_doc)

### 参考文档

[Building Your First App](https://developer.android.com/training/basics/firstapp/creating-project.html)

[User Guide](https://developer.android.com/studio/intro/index.html)

