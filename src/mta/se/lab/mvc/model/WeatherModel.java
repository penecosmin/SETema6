package mta.se.lab.mvc.model;

import mta.se.lab.mvc.exceptions.InputException;
import mta.se.lab.mvc.interfaces.IModelListener;

import java.util.ArrayList;
import java.util.List;

public class WeatherModel {
	// Constants
	public static final String INITIAL_VALUE = "1";

	// Member variable defining state of calculator, the total current value
	
	private int mWindSpeed;
	private int mTemp;

	private List<IModelListener> mListeners;

	/**
	 * Constructor
	 */
	public WeatherModel() {
	}

	/**
	 * Set the total value.
	 * 
	 * @param value
	 *            New value that should be used for the calculator total.
	 */
	public void setValue(int wind, int temp) throws InputException {
		try {
			// mTotal = new BigInteger(value);

			mWindSpeed = wind;
			mTemp = temp;
			// System.out.printf("aici2%d %d\n",mWindSpeed,mTemp);
			notifyListeners();
		} catch (NumberFormatException e) {
			throw new InputException("Eroare", e.getMessage());
		}
	}

	/**
	 * Return current calculator total.
	 */
	public String getWindValue() {
		return mWindSpeed + ""; // .toString();
	}

	public String getTempValue() {
		return mTemp + ""; // .toString();
	}

	/**
	 * Adds the view listener to the list
	 * 
	 * @param listener
	 *            The model event listener
	 */
	public void addModelListener(IModelListener listener) {
		if (mListeners == null) {
			mListeners = new ArrayList<IModelListener>();
		}

		mListeners.add(listener);
	}

	/**
	 * Notifies the views listeners of the changed state (value)
	 */
	private void notifyListeners() {
		if (mListeners != null && !mListeners.isEmpty()) {
			for (IModelListener listener : mListeners)
				listener.onUpdate();
		}
	}
}
