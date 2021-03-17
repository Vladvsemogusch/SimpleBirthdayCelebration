package cc.anisimov.vlad.letscelebrate.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream


object FileUtils {
    fun storeScreenShot(bm: Bitmap?, fileName: String, context: Context): File {
        val dir = File(context.filesDir.absolutePath)
        if (!dir.exists())
            dir.mkdirs()
        val file = File(context.filesDir.absolutePath, fileName)
        try {
            val fOut = FileOutputStream(file)
            bm?.compress(Bitmap.CompressFormat.JPEG, 10, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file
    }

    fun shareImage(file: File, context: Context): Boolean {
        val uri: Uri = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".provider",
            file
        );
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_SUBJECT, "")
        intent.putExtra(Intent.EXTRA_TEXT, "")
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        try {
            startActivity(context, Intent.createChooser(intent, "Share Birthday"), null)
        } catch (e: ActivityNotFoundException) {
            return false
        }
        return true
    }
}