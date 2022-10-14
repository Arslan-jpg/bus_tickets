package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class Control implements Initializable{
	
	@FXML
	Button btnPretraga, btnUnesi, btnPotvrda, btnClear;
	
	@FXML
	TextField txtPolazak, txtOdrediste, txtPrevoznik, txtPolazakUnos, txtOdredisteUnos, txtSatiP, txtMinutaP, txtSatiD, txtMinutaD, txtProlazak, txtSjedista, txtSlobodna, txtVoznja, txtImePrezime, txtKartaOdrediste;
	
	@FXML
	DatePicker dateDatum, dateDatumP_unos, dateDatumD_unos;
	
	@FXML
	TableView<ModelTable> tableBaza;
	
	@FXML
	RadioButton rbDa, rbNe;
	
	@FXML
	ToggleGroup Toggle1;
	
	@FXML
	TableColumn<ModelTable, String> colId, colPrevoznik, colMjPolaska, colOdrediste, colVrPolaska, colVrDolaska, colSlobodnih;

	ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		tableReload();
		
		
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colPrevoznik.setCellValueFactory(new PropertyValueFactory<>("prevoznik"));
		colMjPolaska.setCellValueFactory(new PropertyValueFactory<>("mjestoP"));
		colOdrediste.setCellValueFactory(new PropertyValueFactory<>("odrediste"));
		colVrPolaska.setCellValueFactory(new PropertyValueFactory<>("vrP"));
		colVrDolaska.setCellValueFactory(new PropertyValueFactory<>("vrD"));
		colSlobodnih.setCellValueFactory(new PropertyValueFactory<>("slobodnoSj"));
		
		tableBaza.setItems(oblist);
	}
	
	
	void tableReload() {
		
		oblist.clear();
		
		try {
			Connection con = DBConnector.getConnection();
			
			ResultSet rs = con.createStatement().executeQuery("select * from raspored_autobusa WHERE raspored_autobusa.polazak > NOW()");
			
			while (rs.next()) {
				oblist.add(new ModelTable(rs.getString("idVoznje"), rs.getString("prevoznik"), rs.getString("mjestoPolaska"), rs.getString("odredište"), rs.getString("polazak"), rs.getString("dolazak"), rs.getString("prolazak"), rs.getString("ukupnoSjedista"), rs.getString("slobodnihSjedista")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@FXML
	void onButtonSearch(ActionEvent Event) {
		oblist.clear();
		
		Connection con;
		try {
			con = DBConnector.getConnection();
			
			ResultSet rs;
			
			if(dateDatum.getValue() != null) {
				String datum = dateDatum.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				rs = con.createStatement().executeQuery("SELECT * FROM autobuskastanica.raspored_autobusa WHERE ((raspored_autobusa.mjestoPolaska = '" + txtPolazak.getText() + "') OR (raspored_autobusa.prolazak LIKE '%" + txtPolazak.getText() + "%')) AND raspored_autobusa.polazak LIKE '" + datum + "%' AND raspored_autobusa.odredište = '" + txtOdrediste.getText() + "';");
			}
			else {
				rs = con.createStatement().executeQuery("SELECT * FROM autobuskastanica.raspored_autobusa WHERE (raspored_autobusa.mjestoPolaska = '" + txtPolazak.getText() + "') OR (raspored_autobusa.prolazak LIKE '%" + txtPolazak.getText() + "%');");
			}
			
			
			while (rs.next()) {
				oblist.add(new ModelTable(rs.getString("idVoznje"), rs.getString("prevoznik"), rs.getString("mjestoPolaska"), rs.getString("odredište"), rs.getString("polazak"), rs.getString("dolazak"), rs.getString("prolazak"), rs.getString("ukupnoSjedista"), rs.getString("slobodnihSjedista")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@FXML
	void onButtonClear(ActionEvent Event) {
		
		txtPolazak.setText("");
		txtOdrediste.setText("");
		dateDatum.setValue(null);
		
		tableReload();
		
	}
	
	@FXML 
	void onButtonUnos(ActionEvent Event){
		
		String vrPolaska = dateDatumP_unos.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " " + txtSatiP.getText() + ":" + txtMinutaP.getText() + ":00";
		String vrDolaska = dateDatumD_unos.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " " + txtSatiP.getText() + ":" + txtMinutaP.getText() + ":00";

		try {
			Connection con = DBConnector.getConnection();
			
			con.createStatement().executeUpdate("INSERT INTO `autobuskastanica`.`raspored_autobusa`\r\n"
					+ "		(`prevoznik`,\r\n"
					+ "		`mjestoPolaska`,\r\n"
					+ "		`odredište`,\r\n"
					+ "		`polazak`,\r\n"
					+ "		`dolazak`,\r\n"
					+ "		`prolazak`,\r\n"
					+ "		`ukupnoSjedista`,\r\n"
					+ "		`slobodnihSjedista`)\r\n"
					+ "		VALUES\r\n"
					+ "		('" + txtPrevoznik.getText() + "',\r\n"
					+ "		'" + txtPolazakUnos.getText() + "',\r\n"
					+ "		'" + txtOdredisteUnos.getText() + "',\r\n"
					+ "		'" + vrPolaska + "',\r\n"
					+ "		'" + vrDolaska + "',\r\n"
					+ "		'" + txtProlazak.getText() + "',\r\n"
					+ "		'" + txtSjedista.getText() + "',\r\n"
					+ "		'" + txtSlobodna.getText() + "');");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		txtPrevoznik.setText(null);
		txtPolazakUnos.setText(null);
		txtOdredisteUnos.setText(null);
		txtSatiP.setText(null);
		txtMinutaP.setText(null);
		txtSatiD.setText(null);
		txtMinutaD.setText(null);
		dateDatumP_unos.setValue(null);
		dateDatumD_unos.setValue(null);
		txtProlazak.setText(null);
		txtSjedista.setText(null);
		txtSlobodna.setText(null);
		
		tableReload();
		
	}
	
	@FXML
	void onButtonPotvrda(ActionEvent Event) {
		
		boolean povratna = false;
		
		if(rbDa.isSelected()) {
			povratna = true;
		}
		else if(rbNe.isSelected()) {
			povratna = false;
		}
		
		try {
			Connection con = DBConnector.getConnection();
			
			con.createStatement().executeUpdate("INSERT INTO `autobuskastanica`.`karte`\r\n"
					+ "(`ime_prezime_putnika`,\r\n"
					+ "`brVožnje`,\r\n"
					+ "`odredište`,\r\n"
					+ "`povratna`)\r\n"
					+ "VALUES\r\n"
					+ "('" + txtImePrezime.getText() + "',\r\n"
					+ " '" + txtVoznja.getText() + "',\r\n"
					+ " '" + txtKartaOdrediste.getText() + "',\r\n"
					+ povratna + ");");
			con.createStatement().executeUpdate("UPDATE `autobuskastanica`.`raspored_autobusa`\r\n"
					+ "SET\r\n"
					+ "`slobodnihSjedista` = `slobodnihSjedista` - 1\r\n"
					+ "WHERE `idVoznje` = " + txtVoznja.getText() + ";");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		txtImePrezime.setText(null);
		txtVoznja.setText(null);
		txtKartaOdrediste.setText(null);
		Toggle1.selectToggle(null);
		
		tableReload();
		
	}
	
}
