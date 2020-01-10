package com.qubin.downloadmanager;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;


import com.qubin.commondialog.CommonDialog;
import com.qubin.download.DownLoadBuilder;

public class MainActivity extends AppCompatActivity {
    private CommonDialog commonDialog;
    private DownLoadBuilder downLoadBuilder;
    final String url = "https://downpack.baidu.com/appsearch_AndroidPhone_v8.0.3(1.0.65.172)_1012271b.apk";
    final String apkName = "测试";
    private DownloadCompleteBroadcast downloadCompleteBroadcast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadCompleteBroadcast = new DownloadCompleteBroadcast();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadCompleteBroadcast,intentFilter);

        commonDialog = new CommonDialog.Builder(MainActivity.this)
                .view(R.layout.dialog)
                .style(R.style.Dialog)
                .setMessage(R.id.txt_sure,"开始更新")
                .setMessage(R.id.txt_cancel,"取消更新")
                .addViewOnclick(R.id.txt_sure, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        downLoadBuilder = new DownLoadBuilder.Builder(MainActivity.this)
                                .addUrl(url)
                                .isWiFi(true)
                                .addDownLoadName(apkName)
                                .addDscription("开始下载")
                                .builder();


                        commonDialog.dismiss();
                    }
                })
                .addViewOnclick(R.id.txt_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        commonDialog.dismiss();
                    }
                })
                .build();

        commonDialog.show();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(downloadCompleteBroadcast);
    }

    class DownloadCompleteBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){

                DownLoadBuilder.intallApk(MainActivity.this,apkName);
            }
        }
    }

}
