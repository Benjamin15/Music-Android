package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.usersList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.communication.CommunicationRest;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.ComponentsListener;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.list.ListMusicAdapter;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.model.User;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.utils.DeviceInformation;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.ListMusic.Admin.FragmentUsersList;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.ListMusic.ListMusic;

public class UserListener implements RecyclerUserTouchHelperListener, ComponentsListener {

    private final ListUserAdapter adapter;
    private String fragmentType;
    public UserListener(ListUserAdapter adapter,String fragment) {
        this.adapter = adapter;
        fragmentType = fragment;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {
        final String POST = "POST";
        if (viewHolder instanceof ListMusicAdapter.MyViewHolder) {
            CommunicationRest communication = new CommunicationRest(
                    ListMusic.view.getResources().getString(R.string.delete_music) + Integer.toString(DeviceInformation.idUser) + "/" +
                            adapter.getUsers().get(position).getId(),
                    POST,
                    ListMusic.view);
            communication.send();
            adapter.removeItem(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onMoved(RecyclerView.ViewHolder viewHolder, int positionStart, int positionEnd) {
        if (viewHolder instanceof ListMusicAdapter.MyViewHolder) {
            adapter.onItemMove(positionStart, positionEnd);
        }
    }

    private void fillBlackList(JSONArray array, ArrayList<User> users, Context context){
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject object = (JSONObject) array.get(i);
                if(object.getBoolean(context.getString(R.string.user_blocked))) {
                    User user = craftUser(object, context);
                    users.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!adapter.compare(users)) {
            adapter.clear();
            adapter.addAll(users);
        }
    }

    private void fillUsersList(JSONArray array,ArrayList<User> users,Context context){
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject object = (JSONObject) array.get(i);
                if(!object.getBoolean(context.getString(R.string.user_blocked))) {
                    User user = craftUser(object, context);
                    users.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!adapter.compare(users)) {
            adapter.clear();
            adapter.addAll(users);
        }
    }
    private User craftUser(JSONObject object,Context context) throws JSONException {
        User user = new User(object.getString(context.getString(R.string.user_name)),
                object.getInt(context.getString(R.string.user_ip)),
                object.getString(context.getString(R.string.user_mac)),object.getBoolean(context.getString(R.string.user_blocked)));
        return user;
    }

    @Override
    public void update(JSONObject json) {
        JSONArray array = null;
        Context context = FragmentUsersList.view.getContext();
        ArrayList<User> users = new ArrayList<>();
        try {
            array = json.getJSONArray(context.getString(R.string.users_json));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (array != null && fragmentType.equals(context.getString(R.string.users_list))) {
            fillUsersList(array,users,context);
        }if(array != null && fragmentType.equals(context.getString(R.string.black_list))){
            fillBlackList(array,users,context);
        }
    }
}
