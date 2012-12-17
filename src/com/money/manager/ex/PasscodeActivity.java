/*******************************************************************************
 * Copyright (C) 2012 The Android Money Manager Ex Project
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/
package com.money.manager.ex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class PasscodeActivity extends Activity {
	public static final String INTENT_REQUEST_PASSWORD = "com.money.manager.ex.custom.intent.action.REQUEST_PASSWORD";
	public static final String INTENT_MESSAGE_TEXT = "INTENT_MESSAGE_TEXT";
	public static final String INTENT_RESULT_PASSCODE = "INTENT_RESULT_PASSCODE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.passcode_activity);
		// create a listener for button
		OnClickListener clickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				ImageButton click = (ImageButton) v;
				EditText getFocus = (EditText) getWindow().getCurrentFocus();
				if (getFocus != null && click.getTag() != null) {
					getFocus.setText(click.getTag().toString());
					switch (getFocus.getId()) {
					case R.id.editTextPasscode1:
						((EditText) findViewById(R.id.editTextPasscode2)).requestFocus();
						break;
					case R.id.editTextPasscode2:
						((EditText) findViewById(R.id.editTextPasscode3)).requestFocus();
						break;
					case R.id.editTextPasscode3:
						((EditText) findViewById(R.id.editTextPasscode4)).requestFocus();
						break;
					case R.id.editTextPasscode4:
						((EditText) findViewById(R.id.editTextPasscode5)).requestFocus();
						break;
					case R.id.editTextPasscode5:
						Intent result = new Intent();
						// set result
						result.putExtra(INTENT_RESULT_PASSCODE, ((EditText) findViewById(R.id.editTextPasscode1)).getText().toString()
								+ ((EditText) findViewById(R.id.editTextPasscode2)).getText().toString()
								+ ((EditText) findViewById(R.id.editTextPasscode3)).getText().toString()
								+ ((EditText) findViewById(R.id.editTextPasscode4)).getText().toString()
								+ ((EditText) findViewById(R.id.editTextPasscode5)).getText().toString());
						// return result
						setResult(RESULT_OK, result);
						finish();
						break;
					}
				}
			}
		};
		// arrays of button id
		int ids[] = { R.id.buttonPasscode0, R.id.buttonPasscode1, R.id.buttonPasscode2, R.id.buttonPasscode3, R.id.buttonPasscode4, R.id.buttonPasscode5,
				R.id.buttonPasscode6, R.id.buttonPasscode7, R.id.buttonPasscode8, R.id.buttonPasscode9 };
		for (int i : ids) {
			ImageButton button = (ImageButton) findViewById(i);
			button.setOnClickListener(clickListener);
		}
		// key back
		ImageButton buttonKeyBack = (ImageButton) findViewById(R.id.buttonPasscodeKeyBack);
		buttonKeyBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText getFocus = (EditText) getWindow().getCurrentFocus();
				if (getFocus != null) {
					boolean nullRequestFocus = false;
					if (!TextUtils.isEmpty(getFocus.getText())) {
						getFocus.setText(null);
					} else nullRequestFocus = true;
					switch (getFocus.getId()) {
					case R.id.editTextPasscode1:
						break;
					case R.id.editTextPasscode2:
						((EditText) findViewById(R.id.editTextPasscode1)).requestFocus();
						if (nullRequestFocus) {
							((EditText) findViewById(R.id.editTextPasscode1)).setText(null);
						}
						break;
					case R.id.editTextPasscode3:
						((EditText) findViewById(R.id.editTextPasscode2)).requestFocus();
						if (nullRequestFocus) {
							((EditText) findViewById(R.id.editTextPasscode2)).setText(null);
						}
						break;
					case R.id.editTextPasscode4:
						((EditText) findViewById(R.id.editTextPasscode3)).requestFocus();
						if (nullRequestFocus) {
							((EditText) findViewById(R.id.editTextPasscode3)).setText(null);
						}
						break;
					case R.id.editTextPasscode5:
						((EditText) findViewById(R.id.editTextPasscode4)).requestFocus();
						if (nullRequestFocus) {
							((EditText) findViewById(R.id.editTextPasscode4)).setText(null);
						}
						break;
					}
				}
			}
		});
		// textview message
		TextView textView = (TextView) findViewById(R.id.textViewMessage);
		textView.setText(null);
		// intent and action
		if (getIntent() != null && getIntent().getAction() != null) {
			if (getIntent().getAction().equals(INTENT_REQUEST_PASSWORD)) {
				if (getIntent().getStringExtra(INTENT_MESSAGE_TEXT) != null) {
					textView.setText(getIntent().getStringExtra(INTENT_MESSAGE_TEXT));
				}
			}
		}
	}
}
