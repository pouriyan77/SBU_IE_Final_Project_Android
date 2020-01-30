package ir.ac.sbu.crisismanaging.UI.FillFormActvity;

import java.util.List;

import ir.ac.sbu.crisismanaging.Pojos.Form;
import ir.ac.sbu.crisismanaging.Pojos.FormDescriptor;

public interface FillFormActivityContract
{
    interface View
    {
        void onFormSentListener();
        void toastMessage(String message);
        void setupForm(FormDescriptor formDescriptor);
        void hideProgressBar();
        void showRetryBtn();
    }

    interface Presenter
    {
        void sendFormToServer(Form form);
        void getFormDescriptorFromServer(String formId);
    }
}
