package jw.horsemanager.Objects;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by jerry on 8/19/2017.
 * Description: This class stores bitmap to make it serializable
 */

public class SerializedBitmap implements Serializable {

    private static final long serialVersionUID = 5270776134173757404L;
    public byte[] bitmapImage;
}
