package frontend;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class GestureListener extends GestureDetector.SimpleOnGestureListener{

    private View view;
    private ClickHandler handler;

    public GestureListener(View view, ClickHandler handler) {
        this.view = view;
        this.handler = handler;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.i("Taps", "SINGLE");
        handler.onBeerClick(view);
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i("Taps", "DOUBLE");
        handler.onBeerDoubleClick(view);
        return true;
    }

}
