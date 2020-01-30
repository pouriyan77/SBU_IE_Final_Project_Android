package ir.ac.sbu.crisismanaging.Pojos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class FormDescriptorApiResponse
{
    @Expose
    private String result;
    @Expose
    private List<FormDescriptor> data;

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public List<FormDescriptor> getData()
    {
        return data;
    }

    public void setData(List<FormDescriptor> data)
    {
        this.data = data;
    }
}
