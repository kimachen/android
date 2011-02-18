package net.cyclestreets.views.overlay;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;

import android.content.Context;
import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;

public class ControllerOverlay extends Overlay implements OnDoubleTapListener, OnGestureListener 
{
	private final GestureDetector gestureDetector_;
	private final MapView mapView_;
	
	public ControllerOverlay(final Context context, final MapView mapView)
	{
		super(context);
		
		mapView_ = mapView;
		
		gestureDetector_ = new GestureDetector(context, this);
		gestureDetector_.setOnDoubleTapListener(this);
	} // SingleTapOveraly

	@Override
	public boolean onTouchEvent(final MotionEvent event, final MapView mapView)
	{
		if(gestureDetector_.onTouchEvent(event))
			return true;
		return super.onTouchEvent(event, mapView);
	} // onTouchEvent

	@Override
	public boolean onSingleTapConfirmed(final MotionEvent e) 
	{
		for(final Overlay overlay : mapView_.getOverlays())
		{
			if(!(overlay instanceof TapListener))
				continue;
			final TapListener l = (TapListener)overlay;
			if(l.onSingleTap(e))
				return true;
		}
		return false;
	} // onSingleTapConfirmed

	@Override
	public boolean onDoubleTap(final MotionEvent e) 
	{ 
		for(final Overlay overlay : mapView_.getOverlays())
		{
			if(!(overlay instanceof TapListener))
				continue;
			final TapListener l = (TapListener)overlay;
			if(l.onDoubleTap(e))
				return true;
		}
		return false; 
	} // onDoubleTap
	
	@Override
	protected void onDraw(final Canvas canvas, final MapView mapView) {	}
	@Override
	protected void onDrawFinished(final Canvas canvas, final MapView mapView) 
	{ 		
		if(mapView.isAnimating())
			return;

		for(final Overlay overlay : mapView_.getOverlays())
		{
			if(!(overlay instanceof TapListener))
				continue;
			final TapListener l = (TapListener)overlay;
			l.drawButtons(canvas, mapView);
		}
	} // onDrawFinished
	
	@Override
	public boolean onDoubleTapEvent(MotionEvent e) { return false; }
	@Override
	public boolean onDown(MotionEvent e) { return false; }
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) { return false; }
	@Override
	public void onLongPress(MotionEvent e) { }
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) { return false; }
	@Override
	public void onShowPress(MotionEvent e) { }
	@Override
	public boolean onSingleTapUp(MotionEvent e) { return false; }
} // class SingleTapOverlay
