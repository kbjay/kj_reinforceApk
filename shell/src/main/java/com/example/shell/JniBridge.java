package com.example.shell;

import android.content.Context;

public class JniBridge {
    static {
        System.loadLibrary("reinforce");
    }

    native void reinforce(Context appContext);
}