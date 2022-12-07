package com.ludosupreme.utils

import android.content.Context
import android.graphics.*


class ImageHelper {

    private fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int): Bitmap? {
        val output = Bitmap.createBitmap(
            bitmap.width, bitmap
                .height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        val roundPx = pixels.toFloat()
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    fun convertDrawableToBitmap(
        context: Context,
        drawable: Int,
    ): Bitmap? {
        val icon = BitmapFactory.decodeResource(
            context.resources,
            drawable
        )

        return icon
    }

    // Custom method to create rounded bitmap from a rectangular bitmap
    fun getRoundedBitmap(srcBitmap: Bitmap, cornerRadius: Int): Bitmap? {
        // Initialize a new instance of Bitmap
        val dstBitmap = Bitmap.createBitmap(
            srcBitmap.width,  // Width
            srcBitmap.height,  // Height
            Bitmap.Config.ARGB_8888 // Config
        )

        /*
            Canvas
                The Canvas class holds the "draw" calls. To draw something, you need 4 basic
                components: A Bitmap to hold the pixels, a Canvas to host the draw calls (writing
                into the bitmap), a drawing primitive (e.g. Rect, Path, text, Bitmap), and a paint
                (to describe the colors and styles for the drawing).
        */
        // Initialize a new Canvas to draw rounded bitmap
        val canvas = Canvas(dstBitmap)

        // Initialize a new Paint instance
        val paint = Paint()
        paint.setAntiAlias(true)

        /*
            Rect
                Rect holds four integer coordinates for a rectangle. The rectangle is represented by
                the coordinates of its 4 edges (left, top, right bottom). These fields can be accessed
                directly. Use width() and height() to retrieve the rectangle's width and height.
                Note: most methods do not check to see that the coordinates are sorted correctly
                (i.e. left <= right and top <= bottom).
        */
        /*
            Rect(int left, int top, int right, int bottom)
                Create a new rectangle with the specified coordinates.
        */
        // Initialize a new Rect instance
        val rect = Rect(0, 0, srcBitmap.width, srcBitmap.height)

        /*
            RectF
                RectF holds four float coordinates for a rectangle. The rectangle is represented by
                the coordinates of its 4 edges (left, top, right bottom). These fields can be
                accessed directly. Use width() and height() to retrieve the rectangle's width and
                height. Note: most methods do not check to see that the coordinates are sorted
                correctly (i.e. left <= right and top <= bottom).
        */
        // Initialize a new RectF instance
        val rectF = RectF(rect)

        /*
            public void drawRoundRect (RectF rect, float rx, float ry, Paint paint)
                Draw the specified round-rect using the specified paint. The roundrect will be
                filled or framed based on the Style in the paint.

            Parameters
                rect : The rectangular bounds of the roundRect to be drawn
                rx : The x-radius of the oval used to round the corners
                ry : The y-radius of the oval used to round the corners
                paint : The paint used to draw the roundRect
        */
        // Draw a rounded rectangle object on canvas
        canvas.drawRoundRect(rectF, cornerRadius.toFloat(), cornerRadius.toFloat(), paint)

        /*
            public Xfermode setXfermode (Xfermode xfermode)
                Set or clear the xfermode object.
                Pass null to clear any previous xfermode. As a convenience, the parameter passed
                is also returned.

            Parameters
                xfermode : May be null. The xfermode to be installed in the paint
            Returns
                xfermode
        */
        /*
            public PorterDuffXfermode (PorterDuff.Mode mode)
                Create an xfermode that uses the specified porter-duff mode.

            Parameters
                mode : The porter-duff mode that is applied

        */paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))

        /*
            public void drawBitmap (Bitmap bitmap, float left, float top, Paint paint)
                Draw the specified bitmap, with its top/left corner at (x,y), using the specified
                paint, transformed by the current matrix.

                Note: if the paint contains a maskfilter that generates a mask which extends beyond
                the bitmap's original width/height (e.g. BlurMaskFilter), then the bitmap will be
                drawn as if it were in a Shader with CLAMP mode. Thus the color outside of the
                original width/height will be the edge color replicated.

                If the bitmap and canvas have different densities, this function will take care of
                automatically scaling the bitmap to draw at the same density as the canvas.

            Parameters
                bitmap : The bitmap to be drawn
                left : The position of the left side of the bitmap being drawn
                top : The position of the top side of the bitmap being drawn
                paint : The paint used to draw the bitmap (may be null)
        */
        // Make a rounded image by copying at the exact center position of source image
        canvas.drawBitmap(srcBitmap, 0f, 0f, paint)

        // Free the native object associated with this bitmap.
        srcBitmap.recycle()

        // Return the circular bitmap
        return dstBitmap
    }

    // Custom method to add a border around rounded bitmap
    fun addBorderToRoundedBitmap(
        srcBitmap: Bitmap,
        cornerRadius: Int,
        borderWidth: Int,
        borderColor: Int
    ): Bitmap? {
        // We will hide half border by bitmap
        var borderWidth = borderWidth
        borderWidth *= 2

        // Initialize a new Bitmap to make it bordered rounded bitmap
        val dstBitmap = Bitmap.createBitmap(
            srcBitmap.width + borderWidth,  // Width
            srcBitmap.height + borderWidth,  // Height
            Bitmap.Config.ARGB_8888 // Config
        )

        // Initialize a new Canvas instance
        val canvas = Canvas(dstBitmap)

        // Initialize a new Paint instance to draw border
        val paint = Paint()
        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth.toFloat()
        paint.isAntiAlias = true

        // Initialize a new Rect instance
        val rect = Rect(
            borderWidth / 2,
            borderWidth / 2,
            dstBitmap.width - borderWidth / 2,
            dstBitmap.height - borderWidth / 2
        )

        // Initialize a new instance of RectF;
        val rectF = RectF(rect)

        // Draw rounded rectangle as a border/shadow on canvas
        canvas.drawRoundRect(rectF, cornerRadius.toFloat(), cornerRadius.toFloat(), paint)

        // Draw source bitmap to canvas
        canvas.drawBitmap(srcBitmap, (borderWidth / 2).toFloat(), (borderWidth / 2).toFloat(), null)

        /*
            public void recycle ()
                Free the native object associated with this bitmap, and clear the reference to the
                pixel data. This will not free the pixel data synchronously; it simply allows it to
                be garbage collected if there are no other references. The bitmap is marked as
                "dead", meaning it will throw an exception if getPixels() or setPixels() is called,
                and will draw nothing. This operation cannot be reversed, so it should only be
                called if you are sure there are no further uses for the bitmap. This is an advanced
                call, and normally need not be called, since the normal GC process will free up this
                memory when there are no more references to this bitmap.
        */srcBitmap.recycle()

        // Return the bordered circular bitmap
        return dstBitmap
    }


    // Custom method to add a border around rounded bitmap
    fun removeBorderToRoundedBitmap(
        srcBitmap: Bitmap,
        cornerRadius: Int,
    ): Bitmap? {
        // We will hide half border by bitmap
        var borderWidth = 0
        borderWidth *= 2

        // Initialize a new Bitmap to make it bordered rounded bitmap
        val dstBitmap = Bitmap.createBitmap(
            srcBitmap.width + borderWidth,  // Width
            srcBitmap.height + borderWidth,  // Height
            Bitmap.Config.ARGB_8888 // Config
        )

        // Initialize a new Canvas instance
        val canvas = Canvas(dstBitmap)

        // Initialize a new Paint instance to draw border
        val paint = Paint()
        paint.color = Color.TRANSPARENT
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth.toFloat()
        paint.isAntiAlias = true

        // Initialize a new Rect instance
        val rect = Rect(
            borderWidth / 2,
            borderWidth / 2,
            dstBitmap.width - borderWidth / 2,
            dstBitmap.height - borderWidth / 2
        )

        // Initialize a new instance of RectF;
        val rectF = RectF(rect)

        // Draw rounded rectangle as a border/shadow on canvas
        canvas.drawRoundRect(rectF, cornerRadius.toFloat(), cornerRadius.toFloat(), paint)

        // Draw source bitmap to canvas
        canvas.drawBitmap(srcBitmap, (borderWidth / 2).toFloat(), (borderWidth / 2).toFloat(), null)

        /*
            public void recycle ()
                Free the native object associated with this bitmap, and clear the reference to the
                pixel data. This will not free the pixel data synchronously; it simply allows it to
                be garbage collected if there are no other references. The bitmap is marked as
                "dead", meaning it will throw an exception if getPixels() or setPixels() is called,
                and will draw nothing. This operation cannot be reversed, so it should only be
                called if you are sure there are no further uses for the bitmap. This is an advanced
                call, and normally need not be called, since the normal GC process will free up this
                memory when there are no more references to this bitmap.
        */srcBitmap.recycle()

        // Return the bordered circular bitmap
        return dstBitmap
    }


}