package com.xlu.wanandroidmvp.common;

public interface ScrollTopListener {

    void scrollToTop();

    default void scrollToTopRefresh() {};

}
