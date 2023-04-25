package com.example.vizoralejelento;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NotificationJobService extends JobService {
    private NotificationHandler notificationHandler;

    @Override
    public boolean onStartJob(JobParameters params) {
        notificationHandler = new NotificationHandler(getApplicationContext());
        notificationHandler.send("Ez a fuknkció nincs lefejlesztve.");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}