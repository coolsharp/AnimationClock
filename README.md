
# Animation Clock
A library of view widgets that makes it easy to display numbers and clock animations in Android apps.


![Screenshot](device-2018-12-26-162804.2018-12-26%2016_32_10.gif)


## How do I get it?
Add the following to your build.gradle(Project) file.
```
allprojects {	
     repositories {	
         ...	
         maven {	
             url  "https://dl.bintray.com/your-bintray-username/maven"	
         }	
     }	
 }
```

Add the following to your build.gradle(Module) file.
```
implementation 'com.coolsharp.animationclock:animation-clockview:0.1'
```

Add the following to your layout file.
```
<com.coolsharp.animationclock.ClockView
      android:id="@+id/clockView"
      android:layout_width="232dp"
      android:layout_height="50dp"
      android:background="#EEEEEE"
      android:layout_marginTop="10dp" app:layout_constraintTop_toTopOf="parent"
      app:layout_constraintEnd_toEndOf="parent" android:layout_marginEnd="8dp"
      app:layout_constraintStart_toStartOf="parent" android:layout_marginStart="8dp"
      android:layout_marginBottom="10dp" app:layout_constraintBottom_toBottomOf="parent"/>
```

Add the following to your view file.
```
override fun onCreate(savedInstanceState: Bundle?) {
   super.onCreate(savedInstanceState)
   setContentView(R.layout.activity_main)
   var clockView = findViewById<ClockView>(R.id.clockView)

   var milisecond = 60

   object : CountDownTimer(60000, 1000) {

       override fun onTick(millisUntilFinished: Long) {
           clockView.setTime(milisecond)
           milisecond--
       }

       override fun onFinish() {
           clockView.setTime(0)
       }
   }.start()
}
```

## License
Animation Clock is licenced under the MIT license. So, feel free to use this utility and the source code.
