package com.txwstudio.app.timetable.ui.Activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.txwstudio.app.timetable.R
import com.txwstudio.app.timetable.adapter.*
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setupToolBar()

        val tabLayout: TabLayout = findViewById(R.id.tabLayout_mainActivity)
        viewPager = findViewById(R.id.viewPager_mainActivity)

        viewPager.adapter = CourseViewerPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        val fab: FloatingActionButton = findViewById(R.id.fab_mainActivity)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuAdd -> {
                /* Get the current day and set it as default when adding the course. */
                val autoWeekday: Int = viewPager.currentItem
                val intent = Intent(this, CourseAddActivity::class.java)
                intent.putExtra("autoWeekday", autoWeekday)
                startActivity(intent)
                return true
            }
            R.id.menuMap -> {
                val intent = Intent()
                intent.setClass(this, CampusMapActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menuCalendar -> {
                gotoCalendar()
                return true
            }
            R.id.menuSettings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupToolBar() {
        setSupportActionBar(toolbar_mainActivity)
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            WEEKDAY_1 -> getString(R.string.tab_text_1)
            WEEKDAY_2 -> getString(R.string.tab_text_2)
            WEEKDAY_3 -> getString(R.string.tab_text_3)
            WEEKDAY_4 -> getString(R.string.tab_text_4)
            WEEKDAY_5 -> getString(R.string.tab_text_5)
            else -> "null"
        }
    }

    private fun gotoCalendar() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val pdfPath = prefs.getString("schoolCalendarPath", "")
        val uri = Uri.parse(pdfPath)

        val target = Intent(Intent.ACTION_VIEW)
        target.setDataAndType(uri, "application/pdf")
        target.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val intent = Intent.createChooser(target, java.lang.String.valueOf(R.string.pdfOpenWithMsg))
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, R.string.pdfNoAppMsg, Toast.LENGTH_LONG).show()
        } catch (e: SecurityException) {
            Toast.makeText(this, R.string.pdfFileNotFound, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, R.string.fileReadErrorMsg, Toast.LENGTH_LONG).show()
        }
    }
}