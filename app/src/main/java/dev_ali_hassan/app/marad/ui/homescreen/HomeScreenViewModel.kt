package dev_ali_hassan.app.marad.ui.homescreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev_ali_hassan.app.marad.R
import dev_ali_hassan.app.marad.model.MaradRukun
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(): ViewModel() {


    fun provideTempData() =
        listOf<MaradRukun>(
            MaradRukun("ركن الاستقبال", R.drawable.quran_icon, 1),
            MaradRukun("ركن القران الكريم", R.drawable.quran_icon, 2),
            MaradRukun("ركن  العلم الشرعي", R.drawable.quran_icon, 3),
            MaradRukun("ركن التخطيط وادارة الوقت", R.drawable.quran_icon, 4),
            MaradRukun("ركن كيف يتعلم العالم الان", R.drawable.quran_icon, 5),
            MaradRukun("ركن البلوتوث الدعوي", R.drawable.quran_icon, 6)
        )


}