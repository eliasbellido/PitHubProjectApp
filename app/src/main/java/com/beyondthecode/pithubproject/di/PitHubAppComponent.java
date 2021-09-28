package com.beyondthecode.pithubproject.di;

import com.beyondthecode.pithubproject.data.datasource.rest.api.IApiClient;
import com.beyondthecode.pithubproject.util.rx.RxSchedulers;

import dagger.Component;

@PitHubAppScope
@Component(modules = {
        PitHubAppContextModule.class,
        RxModule.class
})
public interface PitHubAppComponent {

    RxSchedulers rxSchedulers();

   // IApiClient apiService();


}
