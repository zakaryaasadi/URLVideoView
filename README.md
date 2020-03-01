# URLVideoView

A powerful video downloading and caching library with spinner and refresh button for Android



## Gradle Dependency

- build.gradle (Project.Your_App)
 ``` gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 ```
 - build.gradle (Module.app)
 ``` gradle
dependencies {
    implementation 'com.github.zakaryaasadi:URLVideoView:0.0.2'
 }
 ```
## Permission
- Mainfests

 ```xml
<uses-permission android:name="android.permission.INTERNET"/>       
```

## Usage
- Xml

 ```xml
    <com.zak.URLVideoView
        android:id="@+id/video"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        app:SpinnerStyle="ThreeBounce"
        app:SpinnerColor="@color/colorAccent"
        app:progressbar_height="2dp"
        tools:ignore="MissingConstraints" />    
```
 
- Java

 ```java
onCreate(Bundle savedInstanceState) {
      ...
      URLVideoView videoView = findViewById(R.id.video);
      videoView.start("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4");
}

onStop() {
        if(!videoView.isPause()){
            videoView.pause();
        }
        super.onStop();
    }
    
onRestart() {
        if(videoView != null){
            videoView.resume();
        }
        super.onRestart();
    }
```


- Java with events

 ```java
videoView = findViewById(R.id.video);
        videoView
                .setOnCachedListener(new OnCachedListener() {
                    @Override
                    public void onCached(String path) {
                        
                    }
                })
                .setOnCompletionListener(new OnCompletionListener() {
                    @Override
                    public void onCompletion() {
                        
                    }
                })
                .setOnErrorListener(new OnErrorListener() {
                    @Override
                    public void onError(Exception e) {
                        
                    }
                })
                .start("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4");
```


## Style

Style | Preview
------------     |   -------------
RotatingPlane    | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/RotatingPlane.gif' alt='RotatingPlane' width="90px" height="90px"/>
DoubleBounce     | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/DoubleBounce.gif' alt='DoubleBounce' width="90px" height="90px"/>
Wave             | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/Wave.gif' alt='Wave' width="90px" height="90px"/>
WanderingCubes   | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/WanderingCubes.gif' alt='WanderingCubes' width="90px" height="90px"/>
Pulse            | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/Pulse.gif' alt='Pulse' width="90px" height="90px"/>
ChasingDots      | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/ChasingDots.gif' alt='ChasingDots' width="90px" height="90px"/>
ThreeBounce      | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/ThreeBounce.gif' alt='ThreeBounce' width="90px" height="90px"/>
Circle           | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/Circle.gif' alt='Circle' width="90px" height="90px"/>
CubeGrid         | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/CubeGrid.gif' alt='CubeGrid' width="90px" height="90px"/>
FadingCircle     | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/FadingCircle.gif' alt='FadingCircle' width="90px" height="90px"/>
FoldingCube      | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/FoldingCube.gif' alt='FoldingCube' width="90px" height="90px"/>
RotatingCircle   | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/RotatingCircle.gif' alt='RotatingCircle' width="90px" height="90px"/>
