package jmbg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JmbgClass extends JFrame {

	private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField jmbgTextField;

    public JmbgClass() {
        setTitle("Provera JMBG");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(176, 224, 230));

        // Create separate GridBagConstraints for each component
        GridBagConstraints gbcNameLabel = new GridBagConstraints();
        GridBagConstraints gbcSurnameLabel = new GridBagConstraints();
        GridBagConstraints gbcJmbgLabel = new GridBagConstraints();
        GridBagConstraints gbcNameTextField = new GridBagConstraints(); 
        GridBagConstraints gbcSurnameTextField = new GridBagConstraints(); 
        GridBagConstraints gbcJmbgTextField = new GridBagConstraints();
        GridBagConstraints gbcButton = new GridBagConstraints();

        gbcNameLabel.insets = new Insets(5, 5, 5, 5);
        gbcSurnameLabel.insets = new Insets(5, 5, 5, 5);
        gbcJmbgLabel.insets = new Insets(5, 5, 5, 5);
        gbcJmbgTextField.insets = new Insets(5, 5, 5, 5);
        gbcButton.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Unesite ime:");
        nameLabel.setFont(new Font("Cambria", Font.BOLD, 14));
        JLabel surnameLabel = new JLabel("Unesite prezime:");
        surnameLabel.setFont(new Font("Cambria", Font.BOLD, 14));
        JLabel jmbgLabel = new JLabel("Unesite JMBG:");
        jmbgLabel.setFont(new Font("Cambria", Font.BOLD, 14));

        // Initialize the text fields
        nameTextField = new JTextField(20);
        nameTextField.setFont(new Font("Cambria", Font.PLAIN, 13));
        surnameTextField = new JTextField(20);
        surnameTextField.setFont(new Font("Cambria", Font.PLAIN, 13));
        jmbgTextField = new JTextField(20);
        jmbgTextField.setFont(new Font("Cambria", Font.PLAIN, 13));

        JButton checkButton = new JButton("Proveri");
        checkButton.setForeground(new Color(255, 255, 255));
        checkButton.setBackground(new Color(0, 128, 128));
        checkButton.setFont(new Font("Cambria", Font.BOLD, 14));

        nameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && c != KeyEvent.VK_SPACE) {
                    e.consume();
                }
            }
        });

        surnameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && c != KeyEvent.VK_SPACE) {
                    e.consume();
                }
            }
        });

        checkButton.addActionListener(e -> checkJMBG());

        gbcNameLabel.gridx = 0;
        gbcNameLabel.gridy = 0;
        panel.add(nameLabel, gbcNameLabel);

        gbcNameTextField.gridx = 1;
        gbcNameTextField.gridy = 0;
        panel.add(nameTextField, gbcNameTextField);

        gbcSurnameLabel.gridx = 0;
        gbcSurnameLabel.gridy = 1;
        panel.add(surnameLabel, gbcSurnameLabel);

        gbcSurnameTextField.gridx = 1;
        gbcSurnameTextField.gridy = 1;
        panel.add(surnameTextField, gbcSurnameTextField);

        gbcJmbgLabel.gridx = 0;
        gbcJmbgLabel.gridy = 2;
        panel.add(jmbgLabel, gbcJmbgLabel);

        gbcJmbgTextField.gridx = 1;
        gbcJmbgTextField.gridy = 2;
        panel.add(jmbgTextField, gbcJmbgTextField);

        gbcButton.gridx = 0;
        gbcButton.gridy = 3;
        panel.add(checkButton, gbcButton);

        getContentPane().add(panel);
    }
    
    private boolean areAllFieldsFilled() {
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String jmbg = jmbgTextField.getText();

        return !name.isEmpty() && !surname.isEmpty() && !jmbg.isEmpty();
    }

    private void checkJMBG() {
    	if (areAllFieldsFilled()) {
            String name = nameTextField.getText();
            String surname = surnameTextField.getText();
            String jmbg = jmbgTextField.getText();

            if (isValidJMBG(jmbg) == "validan") {
                displayInfo(name, surname, jmbg);
            } else if (jmbg.length() != 13){
                displayErrorForJMBGLength();
            } else if (isValidJMBG(jmbg) == "neispravan datum") {
            	displayErrorForJMBGDate();
            } else if (isValidJMBG(jmbg) == "neispravan region") {
            	displayErrorForJMBGRegion();
            } else if (isValidJMBG(jmbg) == "neispravan kontrolni broj") {
            	displayErrorForJMBGControlNo();
            } else if (!jmbg.matches("\\d+")) {
            	displayErrorForJMBGLetter();
            } else {
            	displayErrorForJMBG();
            }
        } else {
            displayErrorForEmptyField();
        }
    }

    private String isValidJMBG(String jmbg) {
        if (jmbg == null || jmbg.length() != 13 || !jmbg.matches("\\d+")) {
            return "neispravan";
        }

        // Extract individual components from JMBG
        int day = Integer.parseInt(jmbg.substring(0, 2));
        int month = Integer.parseInt(jmbg.substring(2, 4));
        int yearSecondDigit = Integer.parseInt(jmbg.substring(4, 5));
        String region = jmbg.substring(7, 9);
        int controlNumber = Integer.parseInt(jmbg.substring(12));
        int A = Integer.parseInt(jmbg.substring(0, 1));
        int B = Integer.parseInt(jmbg.substring(1, 2));
        int V = Integer.parseInt(jmbg.substring(2, 3));
        int G = Integer.parseInt(jmbg.substring(3, 4));
        int D = Integer.parseInt(jmbg.substring(4, 5));
        int Đ = Integer.parseInt(jmbg.substring(5, 6));
        int E = Integer.parseInt(jmbg.substring(6, 7));
        int Ž = Integer.parseInt(jmbg.substring(7, 8));
        int Z = Integer.parseInt(jmbg.substring(8, 9));
        int I = Integer.parseInt(jmbg.substring(9, 10));
        int J = Integer.parseInt(jmbg.substring(10, 11));
        int K = Integer.parseInt(jmbg.substring(11, 12));
        int fullYear;
        if (yearSecondDigit == 9) {
            fullYear = 1000 + Integer.parseInt(jmbg.substring(4, 7));
        } else {
            fullYear = 2000 + Integer.parseInt(jmbg.substring(4, 7));
        }

        // Validate date
        try {
            LocalDate.of(fullYear, month, day);
        } catch (DateTimeException e) {
            return "neispravan datum";
        }
        
        // Validate region
        if (getRegionName(region) == "Ne postoji podatak") {
        	return "neispravan region";
        }
        
        if (getControlNumber(A, B, V, G, D, Đ, E, Ž, Z, I, J, K) != controlNumber) {
        	return "neispravan kontrolni broj";
        }

        return "validan";
    }

    private void displayInfo(String name, String surname, String jmbg) {
        // Extract individual components from JMBG
        int day = Integer.parseInt(jmbg.substring(0, 2));
        int month = Integer.parseInt(jmbg.substring(2, 4));
        int yearSecondDigit = Integer.parseInt(jmbg.substring(4, 5));
        String region = jmbg.substring(7, 9);
        int uniqueNumber = Integer.parseInt(jmbg.substring(9, 12));
        int controlNumber = Integer.parseInt(jmbg.substring(12));
        int fullYear;
        if (yearSecondDigit == 9) {
            fullYear = 1000 + Integer.parseInt(jmbg.substring(4, 7));
        } else {
            fullYear = 2000 + Integer.parseInt(jmbg.substring(4, 7));
        }

        // Format date
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        LocalDate dateOfBirth = LocalDate.of(fullYear, month, day);
        String formattedDate = dateOfBirth.format(dateFormatter);

        String message = "<html><font face='Cambria' size='4'><b>Ime i prezime:</b> " + name + " " + surname + "<br>" +
                "<b>Datum rođenja:</b> " + formattedDate + "<br>" +
                "<b>Pol:</b> " + getGender(uniqueNumber) + "<br>" +
                "<b>Politička regija rođenja:</b> " + getRegionName(region) + "<br>" +
                "<b>Redni broj rođenja:</b> " + getUniqueNumber(uniqueNumber) + "<br>" +
                "<b>Kontrolni broj:</b> " + controlNumber + "</font></html>";

        JOptionPane.showMessageDialog(this, message, "Informacije o osobi", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void displayErrorForJMBG() {
    	String message = "<html><font face='Cambria' size='4'>Neispravan JMBG. Molimo unesite validan JMBG.</html>";
        JOptionPane.showMessageDialog(this,
                message,
                "Greška",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private void displayErrorForJMBGLength() {
    	String message = "<html><font face='Cambria' size='4'>Za JMBG je potrebno uneti tacno 13 cifara. Molimo pokušajte ponovo</html>";
        JOptionPane.showMessageDialog(this,
                message,
                "Greška",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private void displayErrorForJMBGDate() {
    	String message = "<html><font face='Cambria' size='4'>Niste uneli ispravan datum. Molimo pokušajte ponovo.</html>";
        JOptionPane.showMessageDialog(this,
                message,
                "Greška",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private void displayErrorForJMBGRegion() {
    	String message = "<html><font face='Cambria' size='4'>Ne postoji uneti region. Molimo pokušajte ponovo</html>";
        JOptionPane.showMessageDialog(this,
                message,
                "Greška",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private void displayErrorForJMBGLetter() {
    	String message = "<html><font face='Cambria' size='4'>JMBG se sastoji samo od cifara. Molimo pokušajte ponovo.</html>";
        JOptionPane.showMessageDialog(this,
                message,
                "Greška",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private void displayErrorForJMBGControlNo() {
    	String message = "<html><font face='Cambria' size='4'>Uneli ste neispravan kontrolni broj. Molimo pokušajte ponovo</html>";
        JOptionPane.showMessageDialog(this,
                message,
                "Greška",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private void displayErrorForEmptyField() {
    	String message = "<html><font face='Cambria' size='4'>Sva polja su obavezna.</html>";
        JOptionPane.showMessageDialog(this,
                message,
                "Greška",
                JOptionPane.ERROR_MESSAGE);
    }

    private String getRegionName(String region) {
    	switch (region) {
    		case "00":
    			return "Stranci bez državljanstva bivše SFRJ ili njenih naslednica";
    		case "01":
    			return "Stranci u BiH";
    		case "02":
    			return "Stranci u Crnoj Gori";
    		case "03":
    			return "Stranci u Hrvatskoj";
    		case "04":
    			return "Stranci u Makedoniji";
    		case "05":
    			return "Stranci u Sloveniji";
    		case "07":
    			return "Stranci u Srbiji (bez pokrajina)";
    		case "08":
    			return "Stranci u Vojvodini";
    		case "09":
    			return "Stranci na Kosovu i Metohiji";
    		case "10":
    			return "Banja Luka, Bosna i Hercegovina";
    		case "11":
    			return "Bihać, Bosna i Hercegovina";
    		case "12":
    			return "Doboj, Bosna i Hercegovina";
    		case "13":
    			return "Goražde, Bosna i Hercegovina";
    		case "14":
    			return "Livno, Bosna i Hercegovina";
    		case "15":
    			return "Mostar, Bosna i Hercegovina";
    		case "16":
    			return "Prijedor, Bosna i Hercegovina";
    		case "17":
    			return "Sarajevo, Bosna i Hercegovina";
    		case "18":
    			return "Tuzla, Bosna i Hercegovina";
    		case "19":
    			return "Zenica, Bosna i Hercegovina";
    		case "21":
    			return "Podgorica, Crna Gora";
    		case "26":
    			return "Nikšić, Crna Gora";
    		case "29":
    			return "Pljevlja, Crna Gora";
    		case "30":
    			return "Osijek, Slavonija region, Hrvatska";
    		case "31":
    			return "Bjelovar, Virovitica, Koprivnica, Pakrac, Podravina region, Hrvatska";
    		case "32":
    			return "Varažde, Međimurje region, Hrvatska";
    		case "33":
    			return "Zagreb, Hrvatska";
    		case "34":
    			return "Karlovac, Hrvatska";
    		case "35":
    			return "Gospić, Lika region, Hrvatska";
    		case "36":
    			return "Rijeka, Pula, Istra and Primorje region, Hrvatska";
    		case "37":
    			return "Sisak, Banovina region, Hrvatska";
    		case "38":
    			return "Split, Zadar, Dubrovnik, Dalmacija region, Hrvatska";
    		case "39":
    			return "Ostalo, Hrvatska";
    		case "41":
    			return "Bitola, Makedonija";
    		case "42":
    			return "Kumanovo, Makedonija";
    		case "43":
    			return "Ohrid, Makedonija";
    		case "44":
    			return "Prilep, Makedonija";
    		case "45":
    			return "Skoplje, Makedonija";
    		case "46":
    			return "Strumica, Makedonija";
    		case "47":
    			return "Tetovo, Makedonija";
    		case "48":
    			return "Veles, Makedonija";
    		case "49":
    			return "Štip, Makedonija";
    		case "71":
    			return "Beograd region, Centralna Srbija";
    		case "72":
    			return "Šumadija, Centralna Srbija";
    		case "73":
    			return "Niš region, Centralna Srbija";
    		case "74":
    			return "Južna Morava, Centralna Srbija";
    		case "75":
    			return "Zaječar, Centralna Srbija";
    		case "76":
    			return "Podunavlje, Centralna Srbija";
    		case "77":
    			return "Podrinje i Kolubara, Centralna Srbija";
    		case "78":
    			return "Kraljevo region, Centralna Srbija";
    		case "79":
    			return "Užice region, Centralna Srbija";
    		case "80":
    			return "Novi Sad region, Autonomna Pokrajina Vojvodina";
    		case "81":
    			return "Sombor region, Autonomna Pokrajina Vojvodina";
    		case "82":
    			return "Subotica region, Autonomna Pokrajina Vojvodina";
    		case "85":
    			return "Zrenjanin region, Autonomna Pokrajina Vojvodina";
    		case "86":
    			return "Pančevo region, Autonomna Pokrajina Vojvodina";
    		case "87":
    			return "Kikinda region, Autonomna Pokrajina Vojvodina";
    		case "88":
    			return "Ruma region, Autonomna Pokrajina Vojvodina";
    		case "89":
    			return "Sremska Mitrovica region, Autonomna Pokrajina Vojvodina";
    		case "91":
    			return "Priština region, Autonomna Pokrajina Kosovo i Metohija";
    		case "92":
    			return "Kosovska Mitrovica region, Autonomna Pokrajina Kosovo i Metohija";
    		case "93":
    			return "Peć region, Autonomna Pokrajina Kosovo i Metohija";
    		case "94":
    			return "Đakovica region, Autonomna Pokrajina Kosovo i Metohija";
    		case "95":
    			return "Prizren region, Autonomna Pokrajina Kosovo i Metohija";
    		case "96":
    			return "Kosovsko Pomoravski okrug, Autonomna Pokrajina Kosovo i Metohija";
    		default:
    			return "Ne postoji podatak";
    		
    	}
    }
    
    private String getGender(int uniqueNumber) {
    	if (uniqueNumber >= 000 && uniqueNumber <= 499) {
    		return "Muški";
    	} else {
    		return "Ženski";
    	}
    }
    
    private int getUniqueNumber(int uniqueNumber) {
    	if (uniqueNumber >= 000 && uniqueNumber <= 499) {
    		return uniqueNumber+1;
    	} else {
    		return uniqueNumber-499;
    	}
    }
    
    private int getControlNumber(int A, int B, int V, int G, int D, int Đ, int E, int Ž, int Z, int I, int J, int K) {
    	// Izračunavanje kontrolnog broja
        int controlNumber = 11-((7 * (A + E) + 6 * (B + Ž) + 5 * (V + Z) +
                4 * (G + I) + 3 * (D + J) + 2 * (Đ + K)) % 11);

        // Ako je kontrolni broj veći od 9, postaje 0
        if (controlNumber > 9) {
            controlNumber = 0;
        }

        return controlNumber;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JmbgClass app = new JmbgClass();
                app.setVisible(true);
            }
        });
    }
}
