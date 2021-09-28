package com.beyondthecode.pithubproject.di;

import com.beyondthecode.pithubproject.util.rx.PitHubAppRxSchedulers;
import com.beyondthecode.pithubproject.util.rx.RxSchedulers;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {

    @Provides
    RxSchedulers provideRxSchedulers(){
        return new PitHubAppRxSchedulers();
    }
}
