package com.example.yuri.contentsharer;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener, MyRecyclerViewAdapter.ItemLongClickListener{

    MyRecyclerViewAdapter adapter;
    Tree tree;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tree = new Tree("root");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = tree.getParentNodeId();

                if(tmp != "-1"){
                    tree.setLevel(tree.getLevel() - 1);
                    tree.setCurrentNodeId(tmp);
                    setRV();
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(), "You are on node: " + tree.getCurrentNodeId() + ", with content: " + tree.getCurrentNodeContent(), Toast.LENGTH_SHORT).show();

                LayoutInflater li = getLayoutInflater();
                final View addView = li.inflate(R.layout.alert, null);

                addView.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View arg0){
                        EditText content = (EditText) addView.findViewById(R.id.textContent);
                        tree.addChild(content.getText().toString());
                        //Toast.makeText(getApplicationContext(), content.getText().toString(), Toast.LENGTH_SHORT).show();
                        alert.dismiss();

                        setRV();
                    }

                });


                alert = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
                alert.setTitle("Adicionar novo nó");
                alert.setView(addView);

                alert.show();

            }
        });

        // set up the RecyclerView
        setRV();

    }

    @Override
    public void onItemClick(View view, int position) {
        tree.setLevel(tree.getLevel() + 1);
        tree.setCurrentNodeId(tree.getCurrentNodeId() + Integer.toString(position));
        Toast.makeText(this, "You clicked " + tree.getCurrentNodeContent() + " on row number " + tree.getCurrentNodeId() , Toast.LENGTH_SHORT).show();

        setRV();
    }

    @Override
    public boolean onItemLongClick(View view, int position){
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position , Toast.LENGTH_SHORT).show();
        tree.removeChild(Integer.toString(position));
        setRV();

        return true;
    }

    protected void setRV(){
        RecyclerView recyclerView = findViewById(R.id.rvContent);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, tree.getNodeContent());
        adapter.setClickListener(this);
        adapter.setLongClickListener(this);
        recyclerView.setAdapter(adapter);
    }
}
