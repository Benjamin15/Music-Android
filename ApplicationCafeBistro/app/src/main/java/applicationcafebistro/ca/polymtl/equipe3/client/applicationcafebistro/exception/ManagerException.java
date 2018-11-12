package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.exception;

import android.content.res.Resources;
import android.view.View;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.snackbar.SnackBarError;

public class ManagerException {
    private final int codeError;
    private String message;
    private final View view;

    public ManagerException(int codeError, String message, View view) {
        this.codeError = codeError;
        this.message = message;
        this.view = view;
    }

    public void findError() {
        Resources res = view.getContext().getResources();
        message = (codeError == res.getInteger(R.integer.number_400)) ?
                res.getString(R.string.error_400) : (codeError == res.getInteger(R.integer.number_401)) ?
                res.getString(R.string.error_401) : (codeError == res.getInteger(R.integer.number_403)) ?
                res.getString(R.string.error_403) : (codeError == res.getInteger(R.integer.number_405)) ?
                res.getString(R.string.error_405) : (codeError == res.getInteger(R.integer.number_409)) ?
                res.getString(R.string.error_409) : (codeError == res.getInteger(R.integer.number_413)) ?
                res.getString(R.string.error_413) : (codeError == res.getInteger(R.integer.number_415)) ?
                res.getString(R.string.error_415) : res.getString(R.string.error_500);
    }

    public void display() {
        SnackBarError.make(view, view.getContext(), message, 3000);
        SnackBarError.show();
    }
}
