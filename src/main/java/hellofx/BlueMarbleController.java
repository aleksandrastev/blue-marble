package hellofx;

import java.util.Date;
import javax.swing.JOptionPane;
import org.curiousworks.BlueMarble;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlueMarbleController {

	@FXML
	private ImageView image;

	@FXML
	private DatePicker datePicker;

	@FXML
	private Button closeButton;

	@FXML
	private CheckBox enhanced;

	@FXML
	private CheckBox blackAndWhite;

	Date date = new Date();

	boolean containsEnhanced = true;

	String getDateFromDatePicker() {
		int yearValue = datePicker.getValue().getYear();
		int monthValue = datePicker.getValue().getMonthValue();
		int dayValue = datePicker.getValue().getDayOfMonth();
		String monthValueToString = "";
		if (monthValue < 10)
			monthValueToString = "-0" + Integer.toString(monthValue);
		else
			monthValueToString = "-" + Integer.toString(monthValue);
		System.out.println(monthValueToString);
		String dayValueToString = "";
		if (dayValue < 10)
			dayValueToString = "-0" + Integer.toString(dayValue);
		else
			dayValueToString = "-" + Integer.toString(dayValue);
		System.out.println(dayValueToString);
		return yearValue + monthValueToString + "-" + dayValueToString;
	}

	@SuppressWarnings("deprecation")
	@FXML
	void updateDate(ActionEvent event) {
		BlueMarble blueMarble = new BlueMarble();
		image.setImage(null);
		image.setEffect(null);
		enhanced.setSelected(false);
		blackAndWhite.setSelected(false);
		try {
			blueMarble.setDate(getDateFromDatePicker());
			if (datePicker.getValue().getYear() > (date.getYear() + 1900))
				throw new Exception("Invalid date exception");
			else if ((datePicker.getValue().getYear() == (date.getYear() + 1900))) {
				if (datePicker.getValue().getMonthValue() > date.getMonth() + 1)
					throw new Exception("Invalid date exception");
				else if (datePicker.getValue().getMonthValue() == date.getMonth() + 1) {
					if (datePicker.getValue().getDayOfMonth() > date.getDate())
						throw new Exception("Invalid date exception");
				}
			}
			image.setImage(new Image(blueMarble.getImage()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if (e.getMessage().equals("Invalid date exception")) {
				JOptionPane.showMessageDialog(null, "Invalid date!");
			}
		}
		if (datePicker.getValue().getYear() > 2018)
			enhanced.setDisable(true);
		else if ((datePicker.getValue().getYear() == 2018) && (datePicker.getValue().getMonthValue() > 6))
			enhanced.setDisable(true);
		else
			enhanced.setDisable(false);
	}

	@FXML
	void showEnhanced(ActionEvent event) {
		if (image.getImage() != null) {
			if (enhanced.isSelected()) {
				BlueMarble blueMarble = new BlueMarble();
				blueMarble.setDate(getDateFromDatePicker());
				blueMarble.setEnhanced(true);
				image.setImage(new Image(blueMarble.getImage()));
				blueMarble.setEnhanced(false);
			} else {
				updateDate(event);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please, select a date!");
			enhanced.setSelected(false);
		}
	}

	@FXML
	void showBlackAndWhite(ActionEvent event) {
		if (image.getImage() != null) {
			if (blackAndWhite.isSelected()) {
				datePicker.getOnAction();
				ColorAdjust colorAdjust = new ColorAdjust();
				colorAdjust.setSaturation(-1);
				image.setEffect(colorAdjust);
			} else {
				image.setEffect(null);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please, select a date!");
			blackAndWhite.setSelected(false);
		}
	}

	@FXML
	void close(ActionEvent event) {
		System.exit(0);

	}

}
