package androidfanatic.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TodoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView mTaskView;
    private TaskAdapter mTaskAdapter;

    private boolean backPressed = false;
    private SwipeDetector swipeDetector;
    private FloatingActionButton fabAddBtn;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_todo);

        // List and Adapter
        mTaskView = (ListView) findViewById(R.id.listView_tasks);
        mTaskView.setEmptyView(findViewById(R.id.empty_view));
        mTaskAdapter = new TaskAdapter(this, this);
        mTaskView.setAdapter(mTaskAdapter);

        // Buttons and Listeners
        fabAddBtn = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAddBtn.setOnClickListener(this);
        swipeDetector = new SwipeDetector();
        mTaskView.setOnTouchListener(swipeDetector);
        mTaskView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
        if (swipeDetector.swipeDetected()) {
            if (swipeDetector.getAction() == SwipeDetector.Action.LR) {
                new AlertDialog.Builder(TodoActivity.this)
                        .setTitle(getString(R.string.confirm_delete))
                        .setMessage(getString(R.string.delete_confirm_prompt))
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mTaskAdapter.remove(position);
                                showToast(getString(R.string.task_removed));
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), null)
                        .create().show();
            }
        } else {
            mTaskAdapter.toggleDone(position);
        }
    }

    private void showDialog() {
        final AlertDialog dialog = new AlertDialog
                .Builder(this)
                .setView(getLayoutInflater().inflate(R.layout.dialog_add, null))
                .create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.MyAnimation_Window;
        dialog.show();
        dialog.findViewById(R.id.btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_dialog_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = ((EditText) dialog.findViewById(R.id.et_dialog_add)).getText().toString();
                new Task(taskName).save();
                showToast("Task added");
                dialog.dismiss();
                updateTasks();
            }
        });
    }

    @Override
    public Context getApplicationContext() {
        return TodoActivity.this;
    }

    public void showError(String msg) {
        new AlertDialog.Builder(getApplicationContext()).setMessage("Error: " + msg).create().show();
    }

    public void showToast(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
    }

    public void updateTasks() {
        mTaskAdapter.refresh();
        mTaskAdapter.notifyDataSetChanged();
        TaskWidgetProvider.updateWidgets(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        if (view == fabAddBtn) {
            showDialog();
        }
    }
}