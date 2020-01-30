
package ir.ac.sbu.crisismanaging.Pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;

public class Field
{

    @Expose
    private String _id;
    @Expose
    private String name;
    @Expose
    private List<Option> options;
    @Expose
    private Boolean required;
    @Expose
    private String title;
    @Expose
    private String type;

    protected Field(Parcel in)
    {
        _id = in.readString();
        name = in.readString();
        byte tmpRequired = in.readByte();
        required = tmpRequired == 0 ? null : tmpRequired == 1;
        title = in.readString();
        type = in.readString();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Option> getOptions()
    {
        return options;
    }

    public void setOptions(List<Option> options)
    {
        this.options = options;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
