package Defaults;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DELL1 on 12/27/2015.
 */
public class PreferenceSelection {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    public static final String MyPREFERENCES = "User_pref" ;

public PreferenceSelection(Context context)
{
    this.context=context;
    pref = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    editor =pref.edit();
}
    public void insertLanguage(Boolean value)
    {
editor.putBoolean("Lang",value);
        editor.commit();
    }
    public void  insertThemeColor(Boolean value)
    {
        editor.putBoolean("Color",value);
        editor.commit();
    }
    public boolean getLanguage()
    {
      return pref.getBoolean("Lang",false);
    }

    public boolean getColor()
    {
        return pref.getBoolean("Color",false);
    }
}
