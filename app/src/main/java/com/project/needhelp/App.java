package com.project.needhelp;

import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.project.needhelp.api.NeedHelpService;

public class App extends Application {

    private NeedHelpService needHelpService;

    @Override
    public void onCreate() {
        super.onCreate();

        needHelpService = new NeedHelpService();

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new LruMemoryCache(20 * 1024 * 1024))
                .build();

        ImageLoader.getInstance().init(config);
    }

    public NeedHelpService getNeedHelpService() {
        return needHelpService;
    }
}
