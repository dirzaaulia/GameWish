package com.dirzaaulia.gamewish.activity.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.dirzaaulia.gamewish.R;
import com.dirzaaulia.gamewish.activity.editor.EditorActivity;
import com.dirzaaulia.gamewish.model.Wishlist;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter mainPresenter;
    MainAdapter.ItemClickListener itemClickListener;

    List<Wishlist> wishlist;

    TextView textView;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ExtendedFloatingActionButton fab;
    Toolbar toolbar;

    String kode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent1 = getIntent();
        kode = intent1.getStringExtra("kode");

        bindViews();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Game Wish");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), EditorActivity.class);
            intent.putExtra("kode", kode);
            startActivity(intent);
        });

        //Inisiasi class presenter
        mainPresenter = new MainPresenter(this);
        mainPresenter.getWishlist(null, kode);

        //Saat tampilan direfresh
        swipeRefreshLayout.setOnRefreshListener(() -> mainPresenter.getWishlist(null, kode));

        //Saat card di ketuk
        itemClickListener = ((view, position) -> {
            String id = wishlist.get(position).getId();
            String nama = wishlist.get(position).getNama();
            String harga = wishlist.get(position).getHarga();
            String toko = wishlist.get(position).getToko();
            String pre_order = wishlist.get(position).getPre_order();
            String tanggal_rilis = wishlist.get(position).getTanggal_rilis();

            Intent intent = new Intent(this, EditorActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("kode", kode);
            intent.putExtra("nama", nama);
            intent.putExtra("harga", harga);
            intent.putExtra("toko", toko);
            intent.putExtra("pre_order", pre_order);
            intent.putExtra("tanggal_rilis", tanggal_rilis);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        int id = searchView.getContext()
                .getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView textView = searchView.findViewById(id);
        textView.setTextColor(Color.WHITE);

        assert searchManager != null;
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mainPresenter.getWishlist(query, kode);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mainPresenter.getWishlist(newText, kode);
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            mainPresenter.getWishlist(null, kode);
            return false;
        });

        MenuItem searchMenuItem = menu.findItem(R.id.search);
        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do whatever you need
                return true; // KEEP IT TO TRUE OR IT DOESN'T OPEN !!
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mainPresenter.getWishlist(null, kode);
                return true; // OR FALSE IF YOU DIDN'T WANT IT TO CLOSE!
            }
        });

        return true;
    }

    @Override
    public void showLoad() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoad() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetResult(List<Wishlist> wishlistList) {
        MainAdapter mainAdapter = new MainAdapter(this, wishlistList, itemClickListener);
        mainAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mainAdapter);

        wishlist = wishlistList;
    }

    @Override
    public void onErrorLoad(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    /*
    private void daftarToko(String nama_game) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Deal>> call = apiInterface.getDetailGame(nama_game);
        call.enqueue(new Callback<List<Deal>>() {
            @Override
            public void onResponse(@NonNull Call<List<Deal>> call, @NonNull Response<List<Deal>> response) {
                List<Deal> dealList = response.body();

                if (dealList != null && dealList.size() > 0) {
                    String[] gameDetailListString = new String[dealList.size()];

                    for (int i = 0; i < dealList.size(); i++) {
                        gameDetailListString[i] = dealList.get(i).getExternal();

                        textView.setText(gameDetailListString[i]);

                        //ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), R.layout.dropdown_menu, namaObatListString);
                        //autoCompleteNamaObat.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Deal>> call, @NonNull Throwable t) {
                Toast.makeText(getBaseContext(), "Terjadi kesalahan saat mengambil data resep", Toast.LENGTH_SHORT).show();
            }
        });
    }
     */

    private void bindViews() {

        textView = findViewById(R.id.text_view);
        toolbar = findViewById(R.id.toolbar_main);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_main);
        recyclerView = findViewById(R.id.recycler_view_main);
        fab = findViewById(R.id.fab_tambah_wishlist);
    }
}