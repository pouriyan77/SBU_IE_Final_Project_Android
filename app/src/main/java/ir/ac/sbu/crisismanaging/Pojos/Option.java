
package ir.ac.sbu.crisismanaging.Pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Option
{

    @Expose
    private String _id;
    @Expose
    private String label;
    @Expose
    private String value;

    protected Option(Parcel in)
    {
        _id = in.readString();
        label = in.readString();
        value = in.readString();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
