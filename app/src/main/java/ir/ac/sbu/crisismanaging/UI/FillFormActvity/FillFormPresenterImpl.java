package ir.ac.sbu.crisismanaging.UI.FillFormActvity;

import com.google.android.gms.common.GoogleApiAvailability;

import ir.ac.sbu.crisismanaging.Pojos.Form;
import ir.ac.sbu.crisismanaging.Pojos.FormDescriptorApiResponse;
import ir.ac.sbu.crisismanaging.Service.WebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FillFormPresenterImpl implements FillFormActivityContract.Presenter
{
    private FillFormActivityContract.View view;

    public FillFormPresenterImpl(FillFormActivityContract.View view)
    {
        this.view = view;
    }


    @Override
    public void sendFormToServer(Form form)
    {
        WebService.getWebService().sendForm(form).enqueue(new Callback<FormDescriptorApiResponse>()
        {
            @Override
            public void onResponse(Call<FormDescriptorApiResponse> call,
                                   Response<FormDescriptorApiResponse> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body() != null && response.body().getResult().equals("success"))
                    {
                        view.onFormSentListener();
                        view.toastMessage("فرم با موفقیت ارسال شد.");
                    }
                    else
                    {
                        view.toastMessage("خطا در ارسال فرم");
                    }
                }
                else
                {
                    view.toastMessage("خطا در ارسال فرم");
                }
                view.hideProgressBar();
            }

            @Override
            public void onFailure(Call<FormDescriptorApiResponse> call, Throwable t)
            {
                view.toastMessage("خطا در ارتباط با سرور");
                view.hideProgressBar();
            }
        });
    }

    @Override
    public void getFormDescriptorFromServer(String formId)
    {
        WebService.getWebService().getFormDescriptors(formId).enqueue(new Callback<FormDescriptorApiResponse>()
        {
            @Override
            public void onResponse(Call<FormDescriptorApiResponse> call, Response<FormDescriptorApiResponse> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        view.setupForm(response.body().getData().get(0));
                    }
                }
                else
                {
                    view.showRetryBtn();
                }
                view.hideProgressBar();
            }

            @Override
            public void onFailure(Call<FormDescriptorApiResponse> call, Throwable t)
            {
                view.hideProgressBar();
                view.showRetryBtn();
            }
        });
    }
}
