package ir.ac.sbu.crisismanaging.UI.FormsActivity;

import ir.ac.sbu.crisismanaging.Pojos.FormDescriptorApiResponse;
import ir.ac.sbu.crisismanaging.Service.WebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormsActivityPresenterImpl implements FormsActivityContract.Presenter
{
    private FormsActivityContract.View view;

    public FormsActivityPresenterImpl(FormsActivityContract.View view)
    {
        this.view = view;
    }


    @Override
    public void getFormDescriptorsFromServer()
    {
        WebService.getWebService().getFormDescriptors().enqueue(new Callback<FormDescriptorApiResponse>()
        {
            @Override
            public void onResponse(Call<FormDescriptorApiResponse> call, Response<FormDescriptorApiResponse> response)
            {
                if (response.isSuccessful())
                {
                    view.updateRecyclerView(response.body().getData());
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
