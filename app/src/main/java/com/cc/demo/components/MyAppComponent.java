package com.cc.demo.components;

import com.cc.demo.modules.ApiModule;
import com.cc.demo.modules.EventBusModule;
import com.cc.demo.modules.MyAppModule;
import com.cc.demo.modules.RemoteServiceApiModule;
import com.cc.demo.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

// Had to add scope (@Singleton) since Dagger 2 does not allow
// un-scoped components to use modules with scoped bindings
@Singleton
@Component (modules={
        MyAppModule.class,
        ApiModule.class,
        RemoteServiceApiModule.class,
        EventBusModule.class
})
public interface MyAppComponent {
    void inject(MainActivity mainActivity);
}
