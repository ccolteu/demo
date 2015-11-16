package com.cc.demo.modules;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EventBusModule {
    @Provides
    @Singleton
    public Bus provideBus() {
        return new Bus(ThreadEnforcer.MAIN);
    }
}
