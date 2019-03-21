// YMWYAidl.aidl
package com.liaction.ymwy.myapplication;

// Declare any non-default types here with import statements
import com.liaction.ymwy.myapplication.bean.YMWYMessage2;

interface YMWYAidl {
    YMWYMessage2 send(in YMWYMessage2 message);
}
