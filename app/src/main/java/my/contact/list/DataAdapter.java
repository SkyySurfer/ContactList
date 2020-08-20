package my.contact.list;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Contact> contacts;

    DataAdapter(Context context, List<Contact> phones) {
        this.contacts = phones;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.nameView.setText(contact.getName());
        holder.numberView.setText(contact.getNumber());


        Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, contact.getPhoto());

        if (!(photoUri==null) && !(Uri.EMPTY.equals(photoUri))) {
            holder.photoView.setImageURI(photoUri);
        }

    }

    @Override
    public int getItemCount() {
        if (contacts==null){
            return 0;
        }
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photoView;
        TextView nameView, numberView;
        ViewHolder(View view){
            super(view);
            photoView =view.findViewById(R.id.photo);
            nameView = view.findViewById(R.id.name);
            numberView = view.findViewById(R.id.phone);
        }
    }
}