package com.apujadas.todolist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.apujadas.todolist.connectivity.AWSTodoListClient;
import com.apujadas.todolist.connectivity.TodoListClient;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        textView1 = findViewById(R.id.textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.action_settings:
                return true;

            case R.id.action_refresh:
                new GetTodosTask().execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetTodosTask extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... v) {
            TodoListClient client = new AWSTodoListClient();
            try {
                return client.getAllTodos();
            } catch (IOException e) {
                return "Error while obtaining ToDos. Error: " + e.toString();
            }
        }

        protected void onProgressUpdate(Void... progress) {
            //
        }

        protected void onPostExecute(String result) {
            textView1.setText(result);
        }
    }

}
