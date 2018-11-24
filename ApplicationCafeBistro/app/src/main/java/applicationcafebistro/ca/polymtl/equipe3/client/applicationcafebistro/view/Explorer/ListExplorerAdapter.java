package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.Explorer;

import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;

public class ListExplorerAdapter extends RecyclerView.Adapter<ListExplorerAdapter.MyViewHolder> {
    private final ArrayList<File> cardFiles;
    public ListExplorerAdapter() {
        this.cardFiles = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("EXPLORER FILE : ", "CREATE VIEW");

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_explorer, parent, false);
        recursiveScan(Environment.getExternalStorageDirectory());
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final File file = cardFiles.get(position);
        holder.file_name.setText(file.getName());
    }

    @Override
    public int getItemCount() {
        return cardFiles.size();
    }


    public void recursiveScan(File f) {
        Log.d("EXPLORER FILE : ", "DEBUT DE LA RECHERCHE");

        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isDirectory())
                recursiveScan(f);
            if (file.isFile() && file.getPath().endsWith(".mp3")) {
                Log.d("EXPLORER FILE : ", file.toString());
                cardFiles.add(file);
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView file_name;

        MyViewHolder(View view) {
            super(view);
            file_name = view.findViewById(R.id.file_name);
        }
    }
}