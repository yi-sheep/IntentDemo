package com.gaoxianglong.intentdemo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 显式intent按钮点击事件
        button_intent_1.setOnClickListener{
            val intent = Intent(this, Main2Activity::class.java) // 定义一个显式的intent，就是明确指定了是从当前活动到Main2Activity这个活动
            // 在java中是这样写的 Intent intent = new Intent(this,Main2Activity.class)
            // 对比可以看到 kotlin没有声明变量的类型，那是因为kotlin有类型推断，它知道这个是什么类型的
            // 还加就是没有使用new关键字，而是直接使用Intent()来进行实例化一个intent对象
            // 在kotlin指定一个类不是使用 类名.class 而是使用 类名::class.java
            startActivity(intent) // 启动intent
        }
        // 隐式intent按钮点击事件
        button_intent_2.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW) // 定义一个隐式的intent，就是没有明确的指向某一个APP
            intent.data = Uri.parse("https://www.baidu.com")
            // 在java中给intent设置data使用的是 intent.setData()
            // kotlin中将setData这个函数绑定给了变量data，所以我们可以直接通过intent调用变量data来设置uri
            // 还可以隐式跳转到可以打电话的APP
//            val intent = Intent(Intent.ACTION_DIAL)
//            intent.data = Uri.parse("tel:10086")
            startActivity(intent) // 启动intent
        }
        // 向Main2Activity传递数据
        button_intent_3.setOnClickListener {
            val data = editText.text.toString() // 获取到editText的文字
            val intent = Intent(this,Main2Activity::class.java)
            intent.putExtra("data",data) // 将要传递的数据以key：value 的形式存入到intent中
//            startActivity(intent) // 启动intent
            // 如果想要下一个活动回传数据给当前活动,就使用以下启动方式
            startActivityForResult(intent,1) // 第一个参数是intent，第二个参数是请求码是唯一的
        }
    }

    /**
     * 分析活动结果
     * 重新onActivityResult函数
     * 这个函数是从一个Activity回到当前Activity时调用
     * 用于解析那个活动返回的结果,然后根据所需获取其中的数据
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
}
