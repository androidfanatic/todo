package androidfanatic.todo;

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

public class TaskAdapter extends BaseAdapter {

    Typeface mTypeface;
    private List<Task> mTasks;
    private androidfanatic.todo.TodoActivity mActivity;
    private Context mContext;

    public TaskAdapter(androidfanatic.todo.TodoActivity activity, Context context) {
        mActivity = activity;
        mContext = context;
        mTypeface = Typeface.createFromAsset(activity.getAssets(), "font.ttf");
        refresh();
    }

    public void refresh() {
        mTasks = Task.listAll(Task.class, "id desc");
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
        view = inflater.inflate(R.layout.layout_row, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_task_name);
        textView.setText(task.getName());
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_task_icon);
        String first = "?";
        try {
            first = String.valueOf(task.getName().charAt(0)).toUpperCase();
        } catch (Exception e) {

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