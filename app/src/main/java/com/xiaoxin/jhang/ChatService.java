package com.xiaoxin.jhang;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoxin
 * @date 2017/12/8
 * @describe ：即时通讯拦截
 * 修改内容
 */

public class ChatService extends AccessibilityService {
    private static final String TAG = "ChatService";

    ArrayList<String> keyWordList = new ArrayList<>();//数据源

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        keyWordList.add("太子党");
        keyWordList.add("共产党");
        keyWordList.add("小迷妹");
        keyWordList.add("只手遮天");

        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED) {

            interceptMethod(event);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void interceptMethod(AccessibilityEvent event) {

        if (!"".equals(event.getText().toString()) && event.getText().toString() != null) {
            for (String str : keyWordList) {
                if (event.getText().toString().contains(str)) {
                    AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
                    if (accessibilityNodeInfo == null) {
                        return;
                    }
                    Toast.makeText(this,"注意你的言语",Toast.LENGTH_LONG).show();
                    List<AccessibilityNodeInfo> editNodeInfo = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str);
                    if (editNodeInfo != null && editNodeInfo.size()>=0) {
                        Bundle arguments = new Bundle();
                        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, " ");
                        editNodeInfo.get(0).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                    }
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
