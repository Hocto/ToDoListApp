package com.example.asus.ceng427hw1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListDB listDB;
    ArrayAdapter<String> listAdapter;
    ListView listTask;
    EditText message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listDB = new ListDB(this);
        listTask = (ListView)findViewById(R.id.listTask);
        loadTasks();
    }

    private void loadTasks() {
        ArrayList<String> taskList = listDB.getTaskList();
        if(listAdapter==null){
            listAdapter = new ArrayAdapter<String>(this,R.layout.row,R.id.taskTitle, taskList);
            listTask.setAdapter(listAdapter);
        }
        else{
            listAdapter.clear();
            listAdapter.addAll(taskList);
            listAdapter.notifyDataSetChanged();
        }
    }

    public void btnClick(View v){
        Button add = (Button)v;
        message = (EditText)findViewById(R.id.edtText);
        String messageText = String.valueOf(message.getText());
        if(messageText.equals("")){
            Toast.makeText(MainActivity.this, "Task can't be empty!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            listDB.insertTask(messageText);
            loadTasks();
            message.setText("");
        }
    }

    public void btnDelete(View view){
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.taskTitle);
        String task = String.valueOf(taskTextView.getText());
        listDB.deleteTask(task);
        loadTasks();
    }
    public void btnUpdate(View view){
        View parent = (View) view.getParent();
        final TextView taskTextView = (TextView) parent.findViewById(R.id.taskTitle);
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Update Your Task")
                .setMessage("Set your to do thing")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String updatedTask = String.valueOf(taskEditText.getText());
                        if(updatedTask.equals("")){
                            Toast.makeText(MainActivity.this, "Task can't be empty!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String task = String.valueOf(taskTextView.getText());
                            updateTask(task, updatedTask);
                            loadTasks();
                        }
                    }
                })
                .setNegativeButton("Cancel",null)
                .create();
        dialog.show();
    }
    public void updateTask(String task, String updatedTask){
        listDB.updateTask(task, updatedTask);
    }
}
