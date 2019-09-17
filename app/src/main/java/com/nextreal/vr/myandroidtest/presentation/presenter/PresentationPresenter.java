package com.nextreal.vr.myandroidtest.presentation.presenter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.nextreal.vr.myandroidtest.presentation.presentation.SecondScreenPresentation;
import com.nextreal.vr.myandroidtest.presentation.service.MultiScreenService;


/**
 * @author : YangHaoYi on  2019/4/30.
 * Email  :  yang.haoyi@qq.com
 * Description :离屏逻辑控制中心
 * Change : YangHaoYi on  2019/4/30.
 * Version : V 1.0
 */
public class PresentationPresenter {

    private MultiScreenService multiScreenService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            multiScreenService = ((MultiScreenService.MultiScreenBinder) service).getService();
            //显示第二块屏幕
            multiScreenService.showSearchPresentation();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //恢复置空
            multiScreenService = null;
        }
    };

    public void openSearchPresentation(Activity activity) {
        Intent intent = new Intent(activity, MultiScreenService.class);
        activity.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    public SecondScreenPresentation getPresentation() {
        if (null != multiScreenService) {
            return multiScreenService.getPresentation();
        }
        return null;
    }
}
