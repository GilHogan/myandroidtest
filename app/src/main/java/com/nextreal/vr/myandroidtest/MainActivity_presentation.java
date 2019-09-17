package com.nextreal.vr.myandroidtest;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.media.MediaRouter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import com.nextreal.vr.myandroidtest.presentation.presentation.SecondScreenPresentation;
import com.nextreal.vr.myandroidtest.presentation.presenter.PresentationPresenter;

public class MainActivity_presentation extends AppCompatActivity {
    private MediaRouter mMediaRouter;
    TextView firstTextView;
    SecondScreenPresentation first_presentation;
    /** Presentation逻辑控制中心 **/
    private PresentationPresenter presentationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_presentation);

        firstTextView = findViewById(R.id.firstTextView);
        presentationPresenter = new PresentationPresenter();

        MediaRouter mediaRouter = (MediaRouter) getSystemService(Context.MEDIA_ROUTER_SERVICE);
        MediaRouter.RouteInfo route = mediaRouter.getSelectedRoute(MediaRouter.ROUTE_TYPE_LIVE_VIDEO);
        System.out.println("MediaRouter.RouteInfo route = " + route);
        if (route != null) {
            Display presentationDisplay = route.getPresentationDisplay();
            System.out.println("MediaRouter.RouteInfo presentationDisplay = " + presentationDisplay);

            if (presentationDisplay != null) {
//                Presentation first_presentation = new Presentation(this, presentationDisplay);
//                first_presentation.show();
            }
        }

        DisplayManager displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        Display[] allDisplays = displayManager.getDisplays();
        Display[] presentationDisplays = displayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
        System.out.println("DisplayManager presentationDisplays = " + presentationDisplays.length);
        System.out.println("DisplayManager allDisplays = " + allDisplays.length);

        if (presentationDisplays.length > 0) {
            // If there is more than one suitable first_presentation display, then we could consider
            // giving the user a choice.  For this example, we simply choose the first display
            // which is the one the system recommends as the preferred first_presentation display.
            Display display = presentationDisplays[0];
//            first_presentation = new SecondScreenPresentation(this, display);
//            first_presentation.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
//        mediaPlayer.stop();
//        mediaPlayer.release();
//        mediaPlayer = null;
    }

    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                firstTextView.setText("first button 1");
                break;
            case R.id.button2:
                firstTextView.setText("first button 2");
                presentationPresenter.openSearchPresentation(MainActivity_presentation.this);
                break;
            case R.id.button3:
                first_presentation = presentationPresenter.getPresentation();
                if (null != first_presentation) {
                    first_presentation.setSecondTextViewText("second button 1");
                }
                break;
            case R.id.button4:
                first_presentation = presentationPresenter.getPresentation();
                if (null != first_presentation) {
                    first_presentation.startSecondVideo();
                }
                break;
        }
    }
}
