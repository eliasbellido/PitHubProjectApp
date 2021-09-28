package com.beyondthecode.pithubproject.util.rx;

import io.reactivex.Scheduler;


public interface RxSchedulers {

    Scheduler runOnBackground();

    Scheduler io();

    Scheduler androidThread();

    Scheduler internet();
}
