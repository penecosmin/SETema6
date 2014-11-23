package mta.se.lab.mvc.views;

import mta.se.lab.mvc.interfaces.IController;
import mta.se.lab.mvc.interfaces.IModelListener;
import mta.se.lab.mvc.interfaces.IView;
import mta.se.lab.mvc.model.WeatherModel;
import mta.se.lab.mvc.utils.WeatherAction;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class WeatherView extends JFrame implements IModelListener, IView {
	private static final long serialVersionUID = -5758555454500685115L;

	// View Components
	private JTextField mWindSpeed = new JTextField(6);
	private JTextField mTemperature = new JTextField(6);
	private JButton mUpdate = new JButton("Update");


	private WeatherModel mModel;

	/**
	 * Constructor
	 */
	public WeatherView() {
 		// Initialize components
 		mWindSpeed.setEditable(false);
 		mTemperature.setEditable(false);
 		
 		
 		Random rand = new Random();

 		int  number = rand.nextInt(50) + 1;
 		//50 is the maximum and the 1 is our minimum 


 		if (mUpdate.getAction() == null) {
 			mUpdate.setAction(new WeatherAction());
        }
 		mUpdate.getAction().putValue(IController.ACTION_CALCULATE, number);
 		mUpdate.setActionCommand(IController.ACTION_CALCULATE);
 		mUpdate.setText("Update");
 		
 		this.setTitle("Informatii meteo");
 		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
 		
 		// Layout the components.
 		JPanel panel = new JPanel( new GridBagLayout());

 		this.setVisible(true);
 		
 		this.setSize(300, 300);

 		JLabel windLabel = new JLabel("Viteza vant (km/h): ",SwingConstants.LEFT);
 		JLabel temperatureLabel = new JLabel("Temperatura (grade Celsius): ",SwingConstants.LEFT);

 		
 		
 		GridBagConstraints c = new GridBagConstraints();
 		
 		c.anchor = GridBagConstraints.WEST; //aliniere la staga
 		
 		c.insets = new Insets(10, 10, 10, 10); // distanta in pixeli intre elemente
 		
 			
 		c.gridx = 0;
 		c.gridy = 2;
 		panel.add(windLabel,c);
 		
 		
 		c.gridx = 0;
 		c.gridy = 3;
 		panel.add(temperatureLabel,c);

 		c.gridx = 1;
 		c.gridy = 3;
 		panel.add(mWindSpeed,c);
 		
 		c.gridx = 1;
 		c.gridy = 2;
 		panel.add(mTemperature,c);
 		
 		c.gridx = 0;
 		c.gridy = 4;
 		panel.add(mUpdate,c);
 				
 		
 		
 		//adaugam jPanel la Frame
 		add(panel,BorderLayout.WEST);  //aliniere la stanga
 		
 		
 	}

	/**
	 * Sets the view's reference to the model - Only get operations allowed
	 * 
	 * @param model
	 *            The calc model
	 */
	public void addModel(WeatherModel model) {
		mModel = model;
		mTemperature.setText(model.getTempValue());
		mWindSpeed.setText(model.getWindValue());
	}

	/**
	 * Sets the view's event listener - the controller - so that the changes
	 * made by the user in the view, can be reflected in the model
	 * 
	 * @param controller
	 *            The controller (event listener)
	 */
	public void addController(IController controller) {
		mUpdate.setActionCommand(IController.ACTION_CALCULATE);
		mUpdate.addActionListener(controller);
	}

	@Override
	public void onMessage(boolean isError, String message) {
		if (isError) {
			JOptionPane.showMessageDialog(this, message, "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, message, "Calc MVC",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void onUpdate() {
		mTemperature.setText(mModel.getWindValue());
		mWindSpeed.setText(mModel.getTempValue());
	}
}