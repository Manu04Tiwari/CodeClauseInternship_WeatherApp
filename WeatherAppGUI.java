import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherAppGUI {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Weather App");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create GUI elements
        JLabel cityLabel = new JLabel("Enter City:");
        JTextField cityField = new JTextField(15);
        JButton getWeatherButton = new JButton("Get Weather");
        JTextArea weatherResultArea = new JTextArea(5, 30);
        weatherResultArea.setEditable(false);

        // Create panel to hold GUI elements
        JPanel panel = new JPanel();
        panel.add(cityLabel);
        panel.add(cityField);
        panel.add(getWeatherButton);
        panel.add(weatherResultArea);
        frame.add(panel);

        // Add event listener for the "Get Weather" button
        getWeatherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityField.getText();
                String weatherData = WeatherApp.getWeatherData(city);
                weatherResultArea.setText(weatherData);
            }
        });

        // Make frame visible
        frame.setVisible(true);
    }
}
