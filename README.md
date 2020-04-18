# IntentDemo

这是将Android第一行代码第二版翻译成kotlin的第二个项目，是从第二章开始的，当前项目使用kotlin演示了Intent的显式跳转、Intent的隐式跳转、Intent的向下/向上传递数据。

Intent的显式跳转:

显式Intent就是指定从活动A跳转到活动B。

在布局中添加一个button，用于Activity跳转，然后在MainActivity中编写如下代码.
```kotlin
/**
 * 这段代码放到onCreate函数中
 */

// 显式intent按钮点击事件
button_intent_1.setOnClickListener{
    val intent = Intent(this, Main2Activity::class.java) // 定义一个显式的intent，就是明确指定了是从当前活动到Main2Activity这个活动
    // 在java中是这样写的 Intent intent = new Intent(this,Main2Activity.class)
    // 对比可以看到 kotlin没有声明变量的类型，那是因为kotlin有类型推断，它知道这个是什么类型的
    // 还加就是没有使用new关键字，而是直接使用Intent()来进行实例化一个intent对象
    // 在kotlin指定一个类不是使用 类名.class 而是使用 类名::class.java
    startActivity(intent) // 启动intent
}
```
Intent的隐式跳转:

隐式Intent就是没有指定向哪里跳转，只是给了一个需求，在启动后，如果手机里有能满足这个需求的APP就会出来接收这个intent。

还是在布局中添加一个button，用于隐式跳转，然后继续在MainActivity的onCreate函数中添加这个button的点击事件。
```kotlin
// 隐式intent按钮点击事件
button_intent_2.setOnClickListener {
    val intent = Intent(Intent.ACTION_VIEW) // 定义一式的intent，就是没有明确的指向某一个APP
    intent.data = Uri.parse("https://www.baidu.com")
    // 在java中给intent设置data使用的是 intent.setData()
    // kotlin中将setData这个函数绑定给了变量data，所以我以直接通过intent调用变量data来设置uri
    // 还可以隐式跳转到可以打电话的APP
//  val intent = Intent(Intent.ACTION_DIAL)
//  intent.data = Uri.parse("tel:10086")
    startActivity(intent) // 启动intent
}
```

Intent的向下传递数据:

再添加一个button，和一个editText,用于传递数据。
在MainActivity的onCreate函数中添加这个button的点击事件。
```kotlin
// 向Main2Activity传递数据
button_intent_3.setOnClickListener {
    val data = editText.text.toString() // 获取到editText的文字
    val intent = Intent(this,Main2Activity::class.java)
    intent.putExtra("data",data) // 将要传递的数据以key：value 的形式存入到intent中
    startActivity(intent) // 启动intent
}
```

在MainActivity的onCreate函数中获取数据

```kotlin
val data = intent.getStringExtra("data") // 通过intent这个绑定了getIntent的变量调用getStringExtra函数传入key
textView.text = data // 将获取到的数据设置给TextView
```

Intent的向上回传数据:
还是使用上一个传递数据的button点击事件，在里面跟换启动方式
```kotlin
// 向Main2Activity传递数据
button_intent_3.setOnClickListener {
    val data = editText.text.toString() // 获取到editText的文字
    val intent = Intent(this,Main2Activity::class.java)
    intent.putExtra("data",data) // 将要传递的数据以key：value 的形式存入到intent中
//  startActivity(intent) // 启动intent
    // 如果想要下一个活动回传数据给当前活动,就使用以下启动方式
    startActivityForResult(intent,1) // 第一个参数是intent，第二个参数是请求码是唯一的
}
```
接着在Main2Activity添加一个button用于返回第一个活动，随便回传数据
```kotlin
// 返回按钮的点击事件
button.setOnClickListener {
    val intent = Intent()
    intent.putExtra("data_2","数据回传成功")
    setResult(Activity.RESULT_OK,intent) // 第一个参数：向上一个活动返回处理结果，第二个参数：intent
    finish() // 摧毁当前Activity
}
```
最后需要在MainActivity中重写一个方法，分析回传的结果
```kotlin
/**
 * 分析活动结果
 * 重新onActivityResult函数
 * 这个函数是从一个Activity回到当前Activity时调用
 * 用于解析那个活动返回的结果,然后根据所需获取其中的数据
 */
override fun onActivityResult(requestCode: Int, resultCode: Int, data:Intent?) {
    when (requestCode) { // 这里需要分清楚requestCode/resultCode这两单词有点像，对于英语不好的我，就搞混了
        1->{
            if (resultCode == Activity.RESULT_OK) {
                val dataReturn = data?.getStringExtra("data_2") // 通过key获取到回传的数据，这里有有一个kotlin的语法
                // data?表示这一句需要在data不为null的时候才执行
                Toast.makeText(this,dataReturn,Toast.LENGTH_SHORT).show()
            }
        }
    }
    super.onActivityResult(requestCode, resultCode, data)
}
```

好了，这一节到这里就结束了。没懂的话多看两次，最重要的是自己去编写改写，这样理解会很深刻。
