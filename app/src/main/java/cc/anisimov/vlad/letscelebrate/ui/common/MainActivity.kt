package cc.anisimov.vlad.letscelebrate.ui.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cc.anisimov.vlad.letscelebrate.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}