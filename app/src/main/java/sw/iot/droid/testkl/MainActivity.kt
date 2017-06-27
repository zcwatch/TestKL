package sw.iot.droid.testkl

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.appcompat.R.attr.popupTheme
import android.text.InputFilter
import android.text.InputType
import android.view.Gravity
import android.widget.Toast
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.*
import org.jetbrains.anko.sdk25.coroutines.*


class MainActivity : AppCompatActivity() {
    companion object static {
        val ID_TOOLBAR: Int = 1
        val ID_USER_EDIT: Int = 2
        val ID_PSD_EDIT: Int = 3
        val ID_BTN_LOGIN: Int = 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //message.text = "Hello Kotlin!"

        val myFilter = InputFilter { source, start, end, dest, dstart, dend ->
            var str: CharSequence? = null

            if (source.length > 4) {
                str = source.subSequence(0, 4)
            }

            str
        }
        val myFilters = Array(1) { myFilter }

        relativeLayout {
            var mUserEdit = editText {
                id = ID_USER_EDIT
                hint = "请输入用户名"
                maxLines=1
                maxWidth = 40
            }.lparams {
                width = matchParent
                height = wrapContent
                margin = dip(8)
                //centerInParent()
            }

            var mPwdEdit = editText {
                id= ID_PSD_EDIT
                hint="请输入密码"
                maxLines = 1
                //inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                //maxWidth = 16
                filters = myFilters
            }.lparams {
                width = matchParent
                height = wrapContent
                margin = dip(8)
                below(ID_USER_EDIT)
            }

            var mButton = button("Hello") {
                id = ID_BTN_LOGIN

                onClick {
                    val username = mUserEdit.text.toString()
                    val password = mPwdEdit.text.toString()
                    toast("Hello " + username)
                }
            }.lparams {
                width = wrapContent
                height = wrapContent
                padding = dip(8)
                below(ID_PSD_EDIT)
                //gravity = Gravity.CENTER_HORIZONTAL;
                centerHorizontally()
            }

        }
    }

    fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }
}
