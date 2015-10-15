package com.allen.apkplugproject;

import android.app.Application;

import com.apkplug.AdsPlug.AdsCloudAgent;
import com.apkplug.AdsPlug.ApkplugAdsPlugAgent;
import com.apkplug.CloudService.ApkplugCloudAgent;

import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;

/**
 * Created by allen on 15/10/14.
 */
public class MyApplication extends Application {
    private FrameworkInstance frame=null;

    public FrameworkInstance getFrame() {
        return frame;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        try
        {
            frame=FrameworkFactory.getInstance().start(null,this);
            BundleContext context =frame.getSystemBundleContext();
            //启动云服务包括插件搜索 下载 更新功能
            ApkplugCloudAgent.getInstance(context).init();
            //ApkplugAdsPlugAgent.getInstance(frame.getSystemBundleContext()).InitAdsPlug();
            ApkplugAdsPlugAgent.getInstance(frame.getSystemBundleContext()).setAdsDownloadWifi(true);
            ApkplugAdsPlugAgent.getInstance(frame.getSystemBundleContext()).setAdsShowAuto(AdsCloudAgent.top_bottom_activity_auto_show);
        }
        catch (Exception ex)
        {
            System.err.println("Could not create : " + ex);
            ex.printStackTrace();
            int nPid = android.os.Process.myPid();
            android.os.Process.killProcess(nPid);
        }
    }
}
