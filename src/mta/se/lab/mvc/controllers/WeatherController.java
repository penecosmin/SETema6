package mta.se.lab.mvc.controllers;

import mta.se.lab.mvc.exceptions.InputException;
import mta.se.lab.mvc.interfaces.IController;
import mta.se.lab.mvc.interfaces.IView;
import mta.se.lab.mvc.model.WeatherModel;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeatherController implements IController {
	// The Controller needs to interact with both the Model and View.
	private WeatherModel mModel;

	// The list of views that listen for updates
	private List<IView> mViews;

	/**
	 * Constructor
	 */
	public WeatherController() {
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(ACTION_CALCULATE)) {
			// Make the operation
			try {
				JButton source = (JButton) event.getSource();
				if (source != null
						&& source.getAction() != null
						&& source.getAction().getValue(ACTION_CALCULATE) != null) {
					String userInput = source.getAction()
							.getValue(ACTION_CALCULATE).toString();
					updateValues(userInput);
				} else {
					notifyViews(true, "Invalid operation data");
				}
			} catch (InputException e) {
				notifyViews(true, e.getMessage());
			} catch (ClassCastException ec) {
				notifyViews(true, ec.getMessage());
			}
		} else if (event.getActionCommand().equals(ACTION_RESET)) {
			// Reset the model to its default state
			if (mModel != null) {
				try {
					mModel.setValue(0, 0);
				} catch (InputException e) {
					notifyViews(true, e.getMessage());
				}
			}
		}
	}

	/**
	 * Adds a view reference in order to interact with it
	 * 
	 * @param view
	 *            The view from the controller will receive events and send
	 *            messages
	 */
	public void addView(IView view) {
		if (mViews == null) {
			mViews = new ArrayList<IView>();
		}

		mViews.add(view);
	}

	/**
	 * Adds a reference to the model, so it can update it
	 * 
	 * @param model
	 *            The data model reference
	 */
	public void addModel(WeatherModel model) {
		mModel = model;
	}

	/**
	 * Notifies the views when an message must be displayed
	 * 
	 * @param isError
	 *            {@code true} if the message is an error, {@code false}
	 *            otherwise
	 * @param message
	 *            The string to be displayed
	 */
	private void notifyViews(boolean isError, String message) {
		if (mViews != null && !mViews.isEmpty()) {
			for (IView view : mViews) {
				view.onMessage(isError, message);
			}
		}
	}

	/**
	 * Multiply current total by a number. The operation can be generalized
	 * 
	 * @param operand
	 *            Number (as string) to multiply total by
	 */
	private void updateValues(String operand) throws InputException {
		if (mModel != null) {
			try {

				// Update the model
				Random rand = new Random();

				int Low = 0;
				int High = 30;
				int number1 = rand.nextInt(High - Low) + Low;// pentru vant

				Low = -30;
				High = 30;
				int number2 = rand.nextInt(High - Low) + Low;// pentru
																// temperatura
				// System.out.printf("aici%d %d\n",number1,number2);
				// 50 is the maximum and the 1 is our minimum

				mModel.setValue(number1, number2);

			} catch (NumberFormatException e) {
				throw new InputException(operand, e.getMessage());
			}
		}
	}
}
