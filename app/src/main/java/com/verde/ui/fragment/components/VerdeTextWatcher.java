package com.verde.ui.fragment.components;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

public class VerdeTextWatcher implements TextWatcher {

    private Button button;

    public VerdeTextWatcher(Button button) {
        this.button = button;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            button.setEnabled(true);
        } else button.setEnabled(false);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

}
