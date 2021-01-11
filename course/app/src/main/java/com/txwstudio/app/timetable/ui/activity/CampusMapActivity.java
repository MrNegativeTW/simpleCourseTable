package com.txwstudio.app.timetable.ui.activity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ortiz.touchview.TouchImageView;
import com.txwstudio.app.timetable.R;
import com.txwstudio.app.timetable.Util;

import static com.txwstudio.app.timetable.ui.settings.SettingsFragmentKt.PREFERENCE_NAME_CALENDAR_REQUEST;

public class CampusMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_map);
        setSupportActionBar(findViewById(R.id.toolbar_campusMapAct));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TouchImageView touchImageView = (TouchImageView) findViewById(R.id.touchImageView);
        TextView campusMapErrorMsg = (TextView) findViewById(R.id.campusMapErrorTextView);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String mapPath = prefs.getString(PREFERENCE_NAME_CALENDAR_REQUEST, "");
        Uri mapUri = Uri.parse(mapPath);
        if (!mapPath.isEmpty()) {
            campusMapErrorMsg.setVisibility(View.INVISIBLE);
        }

        touchImageView.setImageURI(mapUri);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) { finish(); }
        return super.onOptionsItemSelected(item);
    }
}
