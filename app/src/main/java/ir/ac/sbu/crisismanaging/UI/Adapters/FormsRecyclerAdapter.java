package ir.ac.sbu.crisismanaging.UI.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.ac.sbu.crisismanaging.Pojos.FormDescriptor;
import ir.ac.sbu.crisismanaging.R;

public class FormsRecyclerAdapter extends RecyclerView.Adapter<FormsRecyclerAdapter.ViewHolder>
{
    private List<FormDescriptor> formDescriptorList;
    private OnItemClickedListener onItemClickedListener;

    public FormsRecyclerAdapter(List<FormDescriptor> formDescriptorList, OnItemClickedListener onItemClickedListener)
    {
        this.formDescriptorList = formDescriptorList;
        this.onItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_descriptor_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.formNameTextView.setText(formDescriptorList.get(position).getTitle());
    }

    @Override
    public int getItemCount()
    {
        return formDescriptorList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView formNameTextView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            formNameTextView = itemView.findViewById(R.id.formNameTextView);
            itemView.setOnClickListener(v -> onItemClickedListener.onItemClicked(
                    formDescriptorList.get(getAdapterPosition()).get_id())
            );
        }
    }

    public interface OnItemClickedListener
    {
        void onItemClicked(String formId);
    }
}
