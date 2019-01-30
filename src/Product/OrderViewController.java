package Product;

import Customer.CustomerController;
import connectors.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrderViewController implements Initializable {
    static private int id_cart;
    static private int id_client;

    static public void setOrderViewID(int idca, int idcl){
       id_cart = idca;
       id_client = idcl;
    }


    @FXML
    private TableView<ShortcutProduct> cartTable;

    @FXML
    private TableColumn<ShortcutProduct, String> cartNameColumn;
    @FXML
    private TableColumn<ShortcutProduct, Integer> cartQuantityColumn, cartIDColumn;
    @FXML
    private TableColumn<ShortcutProduct, Double> cartPriceColumn;

    public void initialize(URL url, ResourceBundle rb){
            fillCartTable();
            cartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id_product"));
            cartNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
            cartQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
            cartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

    }

    private void fillCartTable(){

        String sqlQuery = "SELECT p.id_product,p.product_name,pl.quantity,p.price, c.id_cart, c.id_client FROM product_list pl " +
                "JOIN cart c ON pl.id_cart=c.id_cart JOIN product p ON pl.id_product=p.id_product" +
                " WHERE c.id_client = ? AND c.id_cart = ?";

        showCartTable(sqlQuery);
    }

    public void showCartTable(String sqlQuery){
        try{
            PreparedStatement preparedStatement = Database.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, OrderViewController.id_client);
            preparedStatement.setInt(2, OrderViewController.id_cart);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                ShortcutProduct product = new ShortcutProduct(
                        result.getInt(1), // id_product
                        result.getString(2), // name
                        result.getInt(3), // quantity
                        result.getDouble(4)// price
                );

                System.out.println(product);
                cartTable.getItems().add(product);
            }
        }catch (SQLException ex){
            ex.printStackTrace();

        }

    }

}
