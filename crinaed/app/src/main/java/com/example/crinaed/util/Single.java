package com.example.crinaed.util;

public class Single<T> {
    private T val;
    public Single(T val){
        this.val = val;
    }
    public Single(){
        this.val = null;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }
}
