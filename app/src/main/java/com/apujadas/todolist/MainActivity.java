package com.apujadas.todolist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.apujadas.todolist.connectivity.AWSTodoListClient;
import com.apujadas.todolist.connectivity.ClientExecutor;
import com.apujadas.todolist.connectivity.TodoListClient;
import com.apujadas.todolist.connectivity.json.GSONJsonParser;
import com.apujadas.todolist.domain.ToDo;
import com.apujadas.todolist.resilience.circuitbreaker.CircuitBreakerOpenException;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity implements ToDoFragment.OnListFragmentInteractionListener {

    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

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

        switch (id) {
            case R.id.action_settings:
                return true;

            case R.id.action_refresh:
                new GetTodosTask(toDos -> {
                    ToDoFragment toDoFragment = (ToDoFragment)
                            getSupportFragmentManager().findFragmentById(R.id.fragment);
                    toDoFragment.updateList(toDos);
                }).execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(ToDo item) {

    }

    private static class GetTodosTask extends AsyncTask<Void, Void, List<ToDo>> {

        private Consumer<List<ToDo>> callback;

        GetTodosTask(Consumer<List<ToDo>> callback) {
            this.callback = callback;
        }

        protected List<ToDo> doInBackground(Void... v) {
            ClientExecutor executor = new ClientExecutor(new AWSTodoListClient(new GSONJsonParser()));
            try {
                return executor.getAllTodos();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onProgressUpdate(Void... progress) {

        }

        protected void onPostExecute(List<ToDo> result) {
            callback.accept(result);
        }
    }

}
