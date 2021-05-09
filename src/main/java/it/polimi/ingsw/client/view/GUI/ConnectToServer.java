package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.abstractview.ConnectToServerViewBuilder;
import it.polimi.ingsw.server.controller.SessionController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnectToServer extends ConnectToServerViewBuilder implements GUIView {
    @FXML
    private StackPane connectionPane;
    @FXML
    private Button connectionButton;
    @FXML
    private TextField addressText;
    @FXML
    private TextField portText;

    @Override
    public void run() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ConnectToServerScene.fxml"));
        Parent root = null;
        try {
            root = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }



        Scene scene = new Scene(root);

        getClient().getStage().setScene(scene);

        getClient().getStage().show();
    }

    //Add buttons here that call client.changeViewBuilder(new *****, this);

    public void handleButton()
    {
        if(!addressText.getCharacters().toString().isEmpty())
            if(!portText.getCharacters().toString().isEmpty())
            {
                Client.getInstance().changeViewBuilder(new CreateJoinLoadMatch(), null);
                return;
            }
        Text text=new Text("INSERISCI I DATI CORRETTI!");
        text.setTextOrigin(VPos.TOP);
        text.setFont(Font.font(null, FontWeight.BOLD,10));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //connectionButton.setOnAction(e -> Client.getInstance().changeViewBuilder(new CreateJoinLoadMatch(), null));
    }
}
