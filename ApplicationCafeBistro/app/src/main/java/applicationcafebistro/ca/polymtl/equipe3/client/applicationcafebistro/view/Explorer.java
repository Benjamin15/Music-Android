package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;


public class Explorer extends AppCompatActivity{

    /**
     * Repr�sente le texte qui s'affiche quand la liste est vide
     */
    private TextView mEmpty = null;
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
     * Indique si l'utilisateur est � la racine ou pas
     * pour savoir s'il veut quitter
     */
    private boolean mCountdown = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mList = (ListView) getListView();
        mList = findViewById(R.id.directories);

        if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            mEmpty = (TextView) mList.getEmptyView();
            mEmpty.setText("Access to files not allowed");
        }else{
            registerForContextMenu(mList);

            mCurrentFile = Environment.getExternalStorageDirectory();
            mCurrentFile = Environment.getRootDirectory();
            mCurrentFile = new File(String.valueOf(getExternalFilesDir(Environment.DIRECTORY_MUSIC)));
            setTitle(mCurrentFile.getAbsolutePath());

            File[] files = mCurrentFile.listFiles();
            ArrayList<File> list = new ArrayList<File>();
            if(files != null)
                for(File file : files){
                    list.add(file);
                }


            mAdapter = new FileAdapter(this, android.R.layout.simple_list_item_1, list);

            mList.setAdapter(mAdapter);
            mAdapter.sort();

            mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    File file = mAdapter.getItem(position);
                    if(file.isDirectory())
                        updateDirectory(file);
                    else
                        displayItem(file);
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
        if(!file.isDirectory())
            inflater.inflate(R.menu.context_file, menu);
        else
            inflater.inflate(R.menu.context_dir, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        // On r�cup�re la position de l'item concern�
        File file = mAdapter.getItem(info.position);
        switch (item.getItemId()) {
            case R.id.deleteItem:
                mAdapter.remove(file);
                file.delete();
                return true;

            case R.string.see_item:
                displayItem(file);
                return true;
        }
        return super.onContextItemSelected(item);
    }


    public void displayItem(File file){
        Intent intent = new Intent(Intent.ACTION_VIEW);

        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String extension = file.getName().substring(file.getName().indexOf(".") + 1).toLowerCase();
        extension = mime.getFileExtensionFromUrl(file.getAbsolutePath());
        String type = mime.getMimeTypeFromExtension(extension);
        type = mime.getMimeTypeFromExtension(extension);
        intent.setDataAndType(Uri.fromFile(file), type);

    }

    public void updateDirectory(File file){
        setTitle(file.getAbsolutePath());
        mCountdown = false;
        mCurrentFile = file;
        setEmpty();
        File[] files = mCurrentFile.listFiles();
        if(files != null)
            for(File fileInDir : files)
                mAdapter.add(fileInDir);

        mAdapter.sort();
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event){
        if(keycode == KeyEvent.KEYCODE_BACK) {
            File parent = mCurrentFile.getParentFile();
            if (parent != null)
                updateDirectory(parent);
            else {
                Toast.makeText(this, "You've already reached the root", Toast.LENGTH_SHORT).show();
                mCountdown = true;
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

        public FileAdapter(Context context, int textViewResourceId, List<File> objects){
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
            //Si c'est un r�pertoire, on choisit la couleur dans les pr�f�rences
            /*if(item.isDirectory())
                view.setTextColor(mColor);
            else*/
            // Sinon c'est du noir
            view.setTextColor(Color.BLACK);
            view.setText(item.getName());
            return view;
        }

        public void sort(){
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

}
