package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.adapter.ListMusicAdminAdapter;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.listener.DragListener;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.listener.DropListener;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.listener.RemoveListener;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.model.Music;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.model.User;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.CommunicationRest;

public class ListMusicForUser extends ListView implements ComponentsListener {
    int startPosition;
    int endPosition;
    int dragPointOffset;
    ImageView dragView;
    private List<Music> musics;
    private DropListener dropListener;
    private DragListener dragListener;
    private RemoveListener removeListener;
    private boolean dragMode;

    public ListMusicForUser(Context context) {
        super(context);
        musics = new ArrayList();
        initListener();
    }

    public ListMusicForUser(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        musics = new ArrayList();
        initListener();
    }

    public ListMusicForUser(Context context, AttributeSet attrs) {
        super(context, attrs);
        musics = new ArrayList();
        initListener();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        final int x = (int) ev.getX();
        final int y = (int) ev.getY();

        if (action == MotionEvent.ACTION_DOWN && y < this.getHeight()) {
            dragMode = true;
        }

        if (!dragMode)
            return super.onTouchEvent(ev);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startPosition = pointToPosition(x, y);
                if (startPosition != INVALID_POSITION) {
                    int mItemPosition = startPosition - getFirstVisiblePosition();
                    dragPointOffset = y - getChildAt(mItemPosition).getTop();
                    dragPointOffset -= ((int) ev.getRawY()) - y;
                    startDrag(mItemPosition, y);
                    drag(0, y);// replace 0 with x if desired
                }
                break;
            case MotionEvent.ACTION_MOVE:
                drag(0, y);// replace 0 with x if desired
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            default:
                dragMode = false;
                endPosition = pointToPosition(x, y);
                stopDrag(startPosition - getFirstVisiblePosition());
                if (dropListener != null && startPosition != INVALID_POSITION && endPosition != INVALID_POSITION)
                    dropListener.onDrop(startPosition, endPosition);
                break;
        }
        return true;
    }

    // move the drag view
    private void drag(int x, int y) {
        if (dragView != null) {
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) dragView.getLayoutParams();
            layoutParams.x = x;
            layoutParams.y = y - dragPointOffset;
            WindowManager mWindowManager = (WindowManager) getContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            mWindowManager.updateViewLayout(dragView, layoutParams);

            if (dragListener != null) {
                dragListener.onDrag(x, y, null);// change null to "this" when ready to
            }
        }
    }

    // enable the drag view for dragging
    private void startDrag(int itemIndex, int y) {
        stopDrag(itemIndex);

        View item = getChildAt(itemIndex);
        if (item == null) return;
        item.setDrawingCacheEnabled(true);
        if (dragListener != null)
            dragListener.onStartDrag(item);

        // Create a copy of the drawing cache so that it does not get recycled
        // by the framework when the list tries to clean up memory
        Bitmap bitmap = Bitmap.createBitmap(item.getDrawingCache());

        WindowManager.LayoutParams mWindowParams = new WindowManager.LayoutParams();
        mWindowParams.gravity = Gravity.TOP;
        mWindowParams.x = 0;
        mWindowParams.y = y - dragPointOffset;

        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mWindowParams.format = PixelFormat.TRANSLUCENT;
        mWindowParams.windowAnimations = 0;

        Context context = getContext();
        ImageView v = new ImageView(context);
        v.setImageBitmap(bitmap);

        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.addView(v, mWindowParams);
        dragView = v;
    }

    // destroy drag view
    private void stopDrag(int itemIndex) {
        if (dragView != null) {
            if (dragListener != null)
                dragListener.onStopDrag(getChildAt(itemIndex));
            dragView.setVisibility(GONE);
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            wm.removeView(dragView);
            dragView.setImageDrawable(null);
            dragView = null;
        }
    }

    public void initListener() {
        dropListener = new DropListener() {
            public void onDrop(int from, int to) {
                System.out.println("from : " + from);
                System.out.println("to : " + to);
                for (Music music : musics)
                    System.out.println(music.getUser().getName());
                ListAdapter adapter = getAdapter();
                CommunicationRest communicationRest = new CommunicationRest(
                        getResources().getString(R.string.inversion_test),
                        "POST",
                        getContext(),
                        null
                );
                System.out.println("une : " + musics.get(from).getId());
                System.out.println("autre : " + musics.get(to).getId());
                System.out.println(musics.get(from).getUser().getName());
                System.out.println(musics.get(to).getUser().getName());

                HashMap<String, Integer> parameters = new HashMap<>();
                for (Music music : musics)
                    System.out.println(music.getUser().getName());
                System.out.println("une : " + musics.get(from).getId() + ", " + musics.get(from).getUser().getName());
                System.out.println("autre : " + musics.get(to).getId() + ", " + musics.get(to).getUser().getName());
                parameters.put(getResources().getString(R.string.inversion_first),
                        musics.get(from).getId());
                parameters.put(getResources().getString(R.string.inversion_second),
                        musics.get(to).getId());
                try {
                    if (adapter instanceof ListMusicAdminAdapter) {
                        ((ListMusicAdminAdapter)adapter).onDrop(from, to);
                        invalidateViews();
                    }
                    communicationRest.send(parameters);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        removeListener = new RemoveListener() {
            public void onRemove(int which) {
                ListAdapter adapter = getAdapter();
                if (adapter instanceof ListMusicAdminAdapter) {
                    ((ListMusicAdminAdapter) adapter).onRemove(which);
                    invalidateViews();
                }
            }
        };

        dragListener = new DragListener() {

            int backgroundColor = 0xe0103010;
            int defaultBackgroundColor;

            public void onDrag(int x, int y, ListView listView) {
            }

            public void onStartDrag(View itemView) {
                itemView.setVisibility(View.INVISIBLE);
                defaultBackgroundColor = itemView.getDrawingCacheBackgroundColor();
                itemView.setBackgroundColor(backgroundColor);
            }

            public void onStopDrag(View itemView) {
                itemView.setVisibility(View.VISIBLE);
                itemView.setBackgroundColor(defaultBackgroundColor);
            }

        };
    }


    @Override
    public void update(JSONObject json) {
        JSONArray array = null;
        try {
            array = json.getJSONArray("chansons");
        } catch (JSONException e) {
            invalidateViews();
        }
        musics.clear();

        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject object = (JSONObject) array.get(i);
                User user = new User(object.getString("proposeePar"));
                Music music = new Music(object.getInt("no"), object.getString("titre"),
                        object.getString("artiste"), object.getString("duree"),
                        user, object.getString("proprietaire") == "0" ? false : true);
                musics.add(music);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for (Music music : musics)
            System.out.println(music.getUser().getName());
        ListMusicAdminAdapter adapter = new ListMusicAdminAdapter(getContext(),
                R.layout.component_list_music_user, musics);
        setAdapter(adapter);
        invalidateViews();

    }
}
