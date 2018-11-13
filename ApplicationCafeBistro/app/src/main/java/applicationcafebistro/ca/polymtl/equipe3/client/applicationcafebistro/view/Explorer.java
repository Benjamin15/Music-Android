package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.communication.SendMusicService;

/**
 * this Activity is used to choice a mp3 file in the repository of the device
 */
public class Explorer extends AppCompatActivity {
    public static Explorer instance = null;
    /**
     * Notre Adapter personnalise qui lie les fichiers a la liste
     */
    private FileAdapter mAdapter = null;
    /**
     * Represente le repertoire actuel
     */
    private File mCurrentFile = null;
    /**
     * Représente le service chargé d'envoyer la musique sur le serveur
     */
    private SendMusicService sendMusicService;
    /**
     * Fichier choisi
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explorer);
        sendMusicService = new SendMusicService(getApplicationContext());
        ListView mList = findViewById(R.id.directories);
        instance = this;

        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            TextView mEmpty = (TextView) mList.getEmptyView();
            mEmpty.setText(R.string.accesDenied);
        } else {
            registerForContextMenu(mList);
            mCurrentFile = Environment.getExternalStorageDirectory();
            setTitle(mCurrentFile.getAbsolutePath());

            File[] files = mCurrentFile.listFiles();
            ArrayList<File> list = new ArrayList<>();
            if (files != null)
                list.addAll(Arrays.asList(files));

            mAdapter = new FileAdapter(this, android.R.layout.simple_list_item_1, list);
            mList.setAdapter(mAdapter);
            mAdapter.sort();

            mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    File file = mAdapter.getItem(position);
                    if (Objects.requireNonNull(file).isDirectory()) {
                        updateDirectory(file);
                    } else
                        chooseItem(file);
                }
            });

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        File file = mAdapter.getItem(info.position);
        if (!Objects.requireNonNull(file).isDirectory())
            inflater.inflate(R.menu.context_file, menu);
        else
            inflater.inflate(R.menu.context_dir, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        File file = mAdapter.getItem(info.position);
        if (file != null) {
            switch (item.getItemId()) {
                case R.id.choose_file:
                    chooseItem(file);
                    return true;
                case R.id.open_dir:
                    updateDirectory(file);
                    return true;
            }
        }
        return super.onContextItemSelected(item);
    }

    /**
     * this method is call when we choose a file. Then, we try to send it in the rest api.
     * @param file
     */
    private void chooseItem(final File file) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.confirmation_message) + " '" + file.getName() + "' ?");
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String title = file.getName();
                sendMusicService.sendMusic(file, title);
                finish();
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

    /**
     * This method is called when we click on a directory, to display each other file and directory
     * @param file
     */
    public void updateDirectory(File file) {
        setTitle(file.getAbsolutePath());
        mCurrentFile = file;
        setEmpty();
        File[] files = mCurrentFile.listFiles();
        if (files != null)
            for (File fileInDir : files) {
                if (fileInDir.isDirectory() || isMP3File(fileInDir.getName())) {
                    mAdapter.add(fileInDir);
                }
            }
        mAdapter.sort();
    }

    /**
     * this methode is used to know if a file is a mp3 file
     * @param name
     * @return
     */
    private boolean isMP3File(String name) {
        String extension = name.substring(name.indexOf(".") + 1).toLowerCase();
        return extension.equals(getString(R.string.mp3));
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
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

    public void setEmpty() {
        if (!mAdapter.isEmpty())
            mAdapter.clear();
    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }

    /**
     * this class is used to display each file and directory in the current directory
     */
    private class FileAdapter extends ArrayAdapter<File> {

        private final LayoutInflater mInflater;

        FileAdapter(Context context, int textViewResourceId, List<File> objects) {
            super(context, textViewResourceId, objects);
            mInflater = LayoutInflater.from(context);
        }

        @NonNull
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            TextView view;

            if (convertView != null)
                view = (TextView) convertView;
            else
                view = (TextView) mInflater.inflate(android.R.layout.simple_list_item_1, null);
            view.setTextColor(Color.BLACK);

            File item = getItem(position);
            if (item != null)
                view.setText(item.getName());
            return view;
        }

        /**
         * this methode is call to sort file in the directory
         */
        void sort() {
            super.sort(new FileComparator());
        }

        /**
         * this class is used to compare 2 files
         */
        private class FileComparator implements Comparator<File> {

            /**
             * this method is call to compare 2 files
             * @param lhs
             * @param rhs
             * @return
             */
            public int compare(File lhs, File rhs) {

                if (lhs.isDirectory() && rhs.isFile())
                    return -1;
                if (lhs.isFile() && rhs.isDirectory())
                    return 1;
                return lhs.getName().compareToIgnoreCase(rhs.getName());
            }

        }
    }

}
