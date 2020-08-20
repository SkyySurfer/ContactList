package my.contact.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    List<Contact> contacts = new ArrayList<>();
    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 10;
    RecyclerView recyclerView;
    DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkPermissions()){
            showContacts();
        }
        else{
            requestPermissions();
        }


    }

    private void showContacts(){
        contacts = ContactReader.getAllContacts(this);

        recyclerView = findViewById(R.id.list);
        adapter = new DataAdapter(this, contacts);
        recyclerView.setAdapter(adapter);
    }


    private Boolean checkPermissions(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission
                                .READ_CONTACTS},
                PERMISSIONS_REQUEST_READ_CONTACTS);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS : {

                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    showContacts();
                } else {
                    Toast.makeText(this,R.string.need_permissions,Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchViewItem = menu.findItem(R.id.menu_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);


        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText = newText.toLowerCase();

                List<Contact> filteredContacts = new ArrayList<>();

                for (int i = 0; i < contacts.size(); i++) {

                    String name = contacts.get(i).getName().toLowerCase();
                    String number = contacts.get(i).getNumber().toLowerCase();
                    if (name.contains(newText.toLowerCase()) || number.contains(newText.toLowerCase())) {
                        filteredContacts.add(contacts.get(i));
                    }
                    adapter = new DataAdapter(getApplicationContext(), filteredContacts);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });

            return super.onCreateOptionsMenu(menu);
    }

}
