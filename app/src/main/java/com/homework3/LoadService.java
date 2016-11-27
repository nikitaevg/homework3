package com.homework3;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class LoadService extends Service {

    private final String url = "http://fonday.ru/images/tmp/16/7/original/16710fBjLzqnJlMXhoFHAG.jpg";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        File f = new File(getFilesDir(), MainActivity.fileName);
        if (!f.exists()) {
            ImageLoader l = new ImageLoader(url, f);
            new Thread(l).start();
        }
        return START_STICKY;
    }

    public class ImageLoader implements Runnable {
        private final String url;
        private final File f;

        ImageLoader (String url, File f) {
            this.url = url;
            this.f = f;
        }

        @Override
        public void run() {
            InputStream in = null;
            FileOutputStream out = null;
            try {
                URL u = new URL(url);
                in = new BufferedInputStream(u.openStream());
                out = new FileOutputStream(f);
                int cnt;
                byte[] buff = new byte[1024];
                while ((cnt = in.read(buff)) != -1) {
                    out.write(buff, 0, cnt);
                }
                sendBroadcast(new Intent(MainActivity.IS));
            } catch (IOException e) {
                e.printStackTrace();
                f.delete();
            } finally {
                try {
                    if (in != null)
                        in.close();
                    if (out != null)
                        out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


