package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.QuitExplorerButton;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.SendMusicService;

public class Explorer extends AppCompatActivity{
    /**
     * Repr�sente le texte qui s'affiche quand la liste est vide
     */
    private TextView mEmpty = null;

    private QuitExplorerButton quitExplorerButton;
    /**
     * La liste qui contient nos fichiers et r�pertoires
     */
    private ListView mList = null;
    /**
     * Notre Adapter personnalis� qui lie les fichiers � la liste
     */
    private FileAdapter mAdapter = null;

    /**
     * Repr�sente le r�pertoire actuel
     */
    private File mCurrentFile = null;

    /**
     * Représente le service chargé d'envoyer la musique sur le serveur
     */
    private SendMusicService sendMusicService;

    /**
     * Fichier choisi
     */
    private File  mChosenFile = null;


    public static Explorer instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explorer);
        QuitExplorerButton quitExplorerButton = findViewById(R.id.floating_quit_explorer);
        sendMusicService = new SendMusicService(getApplicationContext());
        mList = findViewById(R.id.directories);
        instance = this;

        if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            mEmpty = (TextView) mList.getEmptyView();
            mEmpty.setText(R.string.accesDenied);
        }else{
            registerForContextMenu(mList);

            /*
            *  mCurrentFile = Environment.getExternalStorageDirectory();
            *  mCurrentFile = Environment.getRootDirectory();
            * */
            mCurrentFile = Environment.getExternalStorageDirectory();
            setTitle(mCurrentFile.getAbsolutePath());

            File[] files = mCurrentFile.listFiles();
            ArrayList<File> list = new ArrayList<>();
            if(files != null)
                list.addAll(Arrays.asList(files));

            mAdapter = new FileAdapter(this, android.R.layout.simple_list_item_1, list);
            mList.setAdapter(mAdapter);
            mAdapter.sort();

            mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    File file = mAdapter.getItem(position);
                    if(Objects.requireNonNull(file).isDirectory()){
                        updateDirectory(file);
                    }
                    else
                        chooseItem(file);
                }
            });

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        File file = mAdapter.getItem(info.position);
        if(!Objects.requireNonNull(file).isDirectory())
            inflater.inflate(R.menu.context_file, menu);
        else
            inflater.inflate(R.menu.context_dir, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        File file = mAdapter.getItem(info.position);
        switch (item.getItemId()) {
            case R.id.choose_file:
                chooseItem(file);
                return true;
            case R.id.open_dir:
                updateDirectory(file);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void chooseItem(final File file){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.confirmation_message) + " '" + file.getName() + "' ?");
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mChosenFile = file;
                String title = file.getName();
                sendMusicService.sendMusic(file,title);
            }
        });

        builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setTitle(R.string.Confirmation);
        builder.create().show();

    }

    public void updateDirectory(File file){
        setTitle(file.getAbsolutePath());
        mCurrentFile = file;
        setEmpty();
        File[] files = mCurrentFile.listFiles();
        if(files != null)
            for(File fileInDir : files)
            {
                if(fileInDir.isDirectory() || isMP3File(fileInDir.getName())){
                    mAdapter.add(fileInDir);
                }
            }
        mAdapter.sort();
    }

    private boolean isMP3File(String name) {
        String extension = name.substring(name.indexOf(".") + 1).toLowerCase();
        return extension.equals(getString(R.string.mp3));
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event){
        if(keycode == KeyEvent.KEYCODE_BACK) {
            File parent = mCurrentFile.getParentFile();
            if (parent != null)
                updateDirectory(parent);
            else {
                Toast.makeText(this, R.string.root, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onKeyDown(keycode, event);
    }

    public void setEmpty(){
        if(!mAdapter.isEmpty())
            mAdapter.clear();
    }

    private class FileAdapter extends ArrayAdapter<File> {

        private LayoutInflater mInflater = null;
        FileAdapter(Context context, int textViewResourceId, List<File> objects){
            super(context, textViewResourceId, objects);
            mInflater = LayoutInflater.from(context);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            TextView view = null;

            if(convertView != null)
                view = (TextView) convertView;
            else
                view= (TextView) mInflater.inflate(android.R.layout.simple_list_item_1, null);

            File item = getItem(position);
            view.setTextColor(Color.BLACK);
            view.setText(item.getName());
            return view;
        }

        void sort(){
            super.sort(new FileComparator());
        }
        private class FileComparator implements Comparator<File> {

            public int compare(File lhs, File rhs) {

                if(lhs.isDirectory() && rhs.isFile())
                    return -1;
                if(lhs.isFile() && rhs.isDirectory())
                    return 1;
                return lhs.getName().compareToIgnoreCase(rhs.getName());
            }

        }
    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }

}
