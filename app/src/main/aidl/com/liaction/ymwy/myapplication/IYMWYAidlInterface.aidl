// IYMWYAidlInterface.aidl
package com.liaction.ymwy.myapplication;

// Declare any non-default types here with import statements
import com.liaction.ymwy.myapplication.bean.YMWYMessage;

interface IYMWYAidlInterface {

    void connect();

    YMWYMessage sendMessage(in YMWYMessage message);
}
