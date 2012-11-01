package com.marakana.android.currencyconverter.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.marakana.android.currencyconverter.CurrencyConverter;
import com.marakana.android.currencyconverter.R;
import com.marakana.android.currencyconverter.CurrencyConverterActivity;

public class CurrencyConverterActivityTest extends
		ActivityInstrumentationTestCase2<CurrencyConverterActivity> {
	private CurrencyConverterActivity activity;
	private Spinner fromCurrency;
	private Spinner toCurrency;
	private EditText inputAmount;
	private TextView outputAmount;
	private View convert;
	private View reverseCurrencies;
	private View clearInput;
	private View copyResult;
	private CurrencyConverter currencyConverter;

	public CurrencyConverterActivityTest() {
		super(CurrencyConverterActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// Get the activity to test
		this.activity = getActivity();

		// Get the UI elements you care about
		this.fromCurrency = (Spinner) activity.findViewById(R.id.from_currency);
		this.toCurrency = (Spinner) activity.findViewById(R.id.to_currency);
		this.inputAmount = (EditText) activity.findViewById(R.id.input_amount);
		this.outputAmount = (TextView) activity
				.findViewById(R.id.output_amount);
		this.convert = (View) activity.findViewById(R.id.convert);
		this.clearInput = (View) activity.findViewById(R.id.clear_input);
	}

	// --- Test cases ---

	public void testSomeTest() {
		// Assert something
		assertTrue(true);

	}

	public void testCheckUIElementsAreOnTheScreen() {
		View rootView = this.activity.getCurrentFocus().getRootView();
		ViewAsserts.assertOnScreen(rootView, this.fromCurrency);
		ViewAsserts.assertOnScreen(rootView, this.toCurrency);
		// ...
	}

	public void testDollarToDollarConversion() {
		double actual = 100.00;
		double expected = -1;
		float delta = 3;

		this.activity.runOnUiThread(new Runnable() {
			public void run() {
				// Dollar is at position 11 - must run on UI thread
				fromCurrency.setSelection(11);
				toCurrency.setSelection(11);
			}
		});

		getInstrumentation().waitForIdleSync();

		// Enter 100 - must be run on non-UI thread
		TouchUtils.clickView(this, this.clearInput);
		TouchUtils.tapView(this, inputAmount);
		sendKeys(KeyEvent.KEYCODE_1, KeyEvent.KEYCODE_0, KeyEvent.KEYCODE_0);
		TouchUtils.clickView(this, this.convert);

		// Check the result
		String result = this.outputAmount.getText().toString();
		super.assertNotSame("Empty string", "", result);
		expected = Double.parseDouble(result);

		super.assertEquals(expected, actual, delta);
	}

	public void testUSDollarToEuroConversion() {
		// Very similar to testDollarToDollarConversion()
		// ...
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
