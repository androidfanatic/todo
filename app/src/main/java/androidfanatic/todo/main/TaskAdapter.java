package androidfanatic.todo.main;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import androidfanatic.todo.R;
import androidfanatic.todo.models.Task;

public class TaskAdapter extends BaseAdapter {

    private final Typeface mTypeface;
    private final TodoActivity mActivity;
    private List<Task> mTasks;

    public TaskAdapter(TodoActivity activity, Context context) {
        mActivity = activity;
        mTypeface = Typeface.createFromAsset(activity.getAssets(), "font.ttf");
        refresh();
    }

    public void refresh() {
        mTasks = Task.listAll(Task.class, "id DESC");
    }

    public void toggleDone(int position) {
        mTasks.get(position).toggleDone();
        mTasks.get(position).save();
        mActivity.updateTasks();
    }

    public void remove(int position) {
        mTasks.get(position).delete();
        mActivity.updateTasks();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        Task task = mTasks.get(i);
        view = inflater.inflate(R.layout.layout_row, viewGroup, false);
        TextView textView = (TextView) view.findViewById(R.id.tv_task_name);
        textView.setText(task.getName());
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_task_icon);
        String first = "?";
        try {
            first = String.valueOf(task.getName().charAt(0)).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageView.setImageDrawable(TextDrawable.builder().buildRound(first, ColorGenerator.MATERIAL.getColor(first)));
        if (task.isDone()) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        textView.setTypeface(mTypeface);
        return view;
    }

    @Override
    public int getCount() {
        return mTasks.size();
    }

    @Override
    public Object getItem(int i) {
        return mTasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mTasks.get(i).getId();
    }
}