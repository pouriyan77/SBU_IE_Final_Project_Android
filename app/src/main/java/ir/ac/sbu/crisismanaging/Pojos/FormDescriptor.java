
package ir.ac.sbu.crisismanaging.Pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;

public class FormDescriptor
{

    @Expose
    private String _id;
    @Expose
    private List<Field> fields;
    @Expose
    private String title;

    protected FormDescriptor(Parcel in)
    {
        _id = in.readString();
        title = in.readString();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
