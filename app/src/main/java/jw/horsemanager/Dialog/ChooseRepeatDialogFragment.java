package jw.horsemanager.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

import jw.horsemanager.R;

/**
 * Created by jerry on 8/28/2017.
 * Description:
 */

public class ChooseRepeatDialogFragment extends DialogFragment {

    private boolean[] mChoicesBefore = {true, true, true, true, true, true, true, false};
    private boolean[] mChoicesAfter = {true, true, true, true, true, true, true, false};

    public boolean[] getChoicesBefore() {
        return mChoicesBefore;
    }

    public void setChoicesBefore(boolean[] mChoicesBefore) {
        this.mChoicesBefore = Arrays.copyOf(mChoicesBefore, mChoicesBefore.length);
        Log.i("","");
    }

    public void setChoicesAfter(boolean[] mChoicesAfter) {
        this.mChoicesAfter = Arrays.copyOf(mChoicesAfter, mChoicesAfter.length);
    }

    public boolean[] getChoicesAfter() {
        return mChoicesAfter;

    }

    public interface ChooseRepeatDialogListener {
        public void onDialogPositiveClick(ChooseRepeatDialogFragment dialog);
        public void onDialogNegativeClick(ChooseRepeatDialogFragment dialog);
    }

    ChooseRepeatDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose_repeat)
                .setMultiChoiceItems(R.array.repetition_choice,
                        mChoicesBefore, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int index, boolean isChecked) {
                                mChoicesAfter[index] = isChecked;
                            }
                        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogPositiveClick(ChooseRepeatDialogFragment.this);
                        dialogInterface.cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogNegativeClick(ChooseRepeatDialogFragment.this);
                        dialogInterface.cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (ChooseRepeatDialogListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() + " must implement ChooseRepeatDialogListener");
        }
    }
}
