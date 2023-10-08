package com.example.porterduffcolorfilter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DrawView(this))
    }

    internal inner class DrawView(context: Context?) : View(context) {
        private var paints: Array<Paint?>
        private var paintBorder: Paint

        private var bitmap: Bitmap

        private var size = 200

        private var mode = PorterDuff.Mode.SRC
        private var colorSrc = intArrayOf(
            Color.WHITE, Color.LTGRAY,
            Color.GRAY, Color.DKGRAY,
            Color.BLACK
        )

        init {
            if (Build.VERSION.SDK_INT >= 11) {
                setLayerType(LAYER_TYPE_SOFTWARE, null)
            }

            bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background)
            bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true)

            paints = arrayOfNulls(colorSrc.size)
            for (i in colorSrc.indices) {
                val paint = Paint(Paint.ANTI_ALIAS_FLAG)
                paint.colorFilter = PorterDuffColorFilter(colorSrc[i], mode)
                paints[i] = paint
            }

            paintBorder = Paint()
            paintBorder.style = Paint.Style.STROKE
            paintBorder.strokeWidth = 3f
            paintBorder.color = Color.BLACK
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)

            canvas!!.translate(0f, 200f)
            val delta = ((width - size * paints.size)
                    / (paints.size + 1))

            for (i in paints.indices) {
                canvas.translate(delta.toFloat(), 0f)
                canvas.drawBitmap(bitmap, 0f, 0f, paints[i])
                canvas.drawRect(0f, 0f, size.toFloat(), size.toFloat(), paintBorder)
                canvas.translate(size.toFloat(), 0f)
            }
        }
    }
}