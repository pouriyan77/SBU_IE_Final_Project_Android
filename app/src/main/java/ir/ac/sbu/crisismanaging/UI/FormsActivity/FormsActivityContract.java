package ir.ac.sbu.crisismanaging.UI.FormsActivity;

import java.util.List;

import ir.ac.sbu.crisismanaging.Pojos.FormDescriptor;

public interface FormsActivityContract
{
    interface View
    {
        void updateRecyclerView(List<FormDescriptor> formDescriptorList);
        void hideProgressBar();
        void showRetryBtn();
    }

    interface Presenter
    {
        void getFormDescriptorsFromServer();
    }
}
