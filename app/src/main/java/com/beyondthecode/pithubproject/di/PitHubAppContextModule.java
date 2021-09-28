package com.beyondthecode.pithubproject.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class PitHubAppContextModule {

    private final Context context;

    public PitHubAppContextModule(Context context) {
        this.context = context;
    }

    @PitHubAppScope
    @Provides
    Context providePitHubContext() {
        return context;
    }
}
