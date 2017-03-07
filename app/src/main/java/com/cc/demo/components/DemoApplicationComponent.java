package com.cc.demo.components;

import com.cc.demo.modules.ApiModule;
import com.cc.demo.modules.EventBusModule;
import com.cc.demo.modules.LocalServiceApiModule;
import com.cc.demo.modules.DemoApplicationModule;
import com.cc.demo.modules.RemoteServiceApiModule;
import com.cc.demo.services.LocalService;
import com.cc.demo.services.RemoteService;
import com.cc.demo.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

// Had to add scope (@Singleton) since Dagger 2 does not allow
// un-scoped components to use modules with scoped bindings
@Singleton
@Component (modules={
        DemoApplicationModule.class,
        ApiModule.class,
        RemoteServiceApiModule.class,
        LocalServiceApiModule.class,
        EventBusModule.class
})
public interface DemoApplicationComponent {
    void inject(MainActivity activity);
    void inject(RemoteService service);
    void inject(LocalService service);
}
