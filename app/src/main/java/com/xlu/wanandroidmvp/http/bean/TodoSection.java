package com.xlu.wanandroidmvp.http.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

public class TodoSection extends SectionEntity<Todo> {

    public TodoSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public TodoSection(Todo todo) {
        super(todo);
    }
}
