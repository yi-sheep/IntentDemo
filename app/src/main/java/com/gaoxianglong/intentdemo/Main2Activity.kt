package com.gaoxianglong.intentdemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // 返回按钮的点击事件
        button.setOnClickListener {
            val intent = Intent()
            intent.putExtra("data_2","数据回传成功")
            setResult(Activity.RESULT_OK,intent) // 第一个参数：向上一个活动返回处理结果，第二个参数：intent
            finish() // 摧毁当前Activity
        }
        val data = intent.getStringExtra("data") // 通过intent这个绑定了getIntent的变量调用getStringExtra函数传入key
        textView.text = data // 将获取到的数据设置给TextView
    }
}
