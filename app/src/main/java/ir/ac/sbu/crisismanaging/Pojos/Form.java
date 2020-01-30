package ir.ac.sbu.crisismanaging.Pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Form
{
    @Expose
    @SerializedName("parentId")
    private String parentId;
    @Expose
    @SerializedName("filledFields")
    private Map<String, Object> fields;

    public Form(String parentId)
    {
        fields = new HashMap<>();
        this.parentId = parentId;
    }

    public Map<String, Object> getFields()
    {
        return fields;
    }

    public void setFields(Map<String, Object> fields)
    {
        this.fields = fields;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }
}
