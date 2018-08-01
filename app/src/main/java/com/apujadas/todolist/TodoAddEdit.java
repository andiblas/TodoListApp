package com.apujadas.todolist;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.apujadas.todolist.connectivity.AWSTodoListClient;
import com.apujadas.todolist.connectivity.ClientExecutor;
import com.apujadas.todolist.connectivity.json.GSONJsonParser;
import com.apujadas.todolist.domain.ToDo;

import java.util.Date;

public class TodoAddEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_add_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> saveToDo());
    }


    private void saveToDo() {
        ToDo newTodo = new ToDo();
        newTodo.setDate(new Date());
        newTodo.setDone(false);
        EditText viewById = findViewById(R.id.txtTodo);
        newTodo.setText(viewById.getText().toString());

        new AddTodoTask(this).execute(newTodo);
    }

    private void showError(Exception ex) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Exception!")
                .setMessage(ex.toString())
                .setPositiveButton("OK", null)
                .show();
    }

    private void addTodoSuccessful(){
        finish();
    }


    private static class AddTodoTask extends AsyncTask<ToDo, Void, Integer> {
        TodoAddEdit activity;
        private Exception ex = null;

        AddTodoTask(TodoAddEdit activity) {
            this.activity = activity;
        }

        protected Integer doInBackground(ToDo... todo) {
            ClientExecutor executor = new ClientExecutor(new AWSTodoListClient(new GSONJsonParser()));
            try {
                executor.addTodo(todo[0]);
            } catch (Exception e) {
                e.printStackTrace();
                ex = e;
            }
            return 0;
        }

        protected void onProgressUpdate(Void... progress) {
        }

        protected void onPostExecute(Integer result) {
            if (ex != null) {
                activity.showError(ex);
            } else {
                activity.addTodoSuccessful();
            }

        }
    }


}
