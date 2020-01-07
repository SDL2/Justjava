package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
/*se quito este import android.icu.text.NumberFormat; y se dejo el antiguo import java,, por conflicto de apis*/

/**
 * This app displays an order to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override //ONCREATE
    protected void onCreate(Bundle savedInstanceState ) {


        TextView newTextP = new TextView(this);
/**
        newTextP.setText("this is a object of the TextView Method??");
        newTextP.setTextSize(25);
        newTextP.setTextColor(Color.BLUE);
 */
        super.onCreate(savedInstanceState);
       // setContentView(newTextP);

        setContentView(R.layout.activity_main);

    }

    int quantity = 0; //THE VARIABLE!!!!
    double totalPrice;

    boolean hasTopping = false;
    boolean hasChocolate = false;


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view){
        if (quantity == 100){
            Toast.makeText(this, getString(R.string.too_many_coffees), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view){
        if(quantity == 0){
            Toast.makeText(this, getString(R.string.too_few_coffees), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }

    /**
     *  This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheck = (CheckBox) findViewById(R.id.has_whipped_cream);
        hasTopping = whippedCreamCheck.isChecked();
        CheckBox chocolateCheck = (CheckBox) findViewById(R.id.has_chocolate);
        hasChocolate = chocolateCheck.isChecked();
        EditText customerNameEditText = (EditText) findViewById(R.id.name_field);
        String customerName = customerNameEditText.getText().toString();

        double price = calculatePrice(hasTopping, hasChocolate);
        String priceMessage = createOrderSummary(price, hasTopping, hasChocolate, customerName);


        Intent ourIntentTest = new Intent(Intent.ACTION_SENDTO);
        ourIntentTest.setData(Uri.parse("mailto:"));
        ourIntentTest.putExtra(Intent.EXTRA_EMAIL, "alguien@gmail.com");
        ourIntentTest.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for: " + customerName);
        ourIntentTest.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (ourIntentTest.resolveActivity(getPackageManager()) != null) {
            startActivity(ourIntentTest);
        }

    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream
     * @param addChocolate is whether or not the user wants chocolate
     * @return gives the total price
     */
    private double calculatePrice(boolean addWhippedCream, boolean addChocolate) {

        totalPrice = quantity * 5;

        if (addWhippedCream){
            totalPrice += (quantity * 1);
        }
        if(addChocolate){
            totalPrice += (quantity * 2);
        }

        return totalPrice;
    }

    /**
     * Create summary of the order.
     *
     * @param customerName is the name the customer type
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants whipped cream topping
     * @param totalPrice of the order
     * @return text summary
     */
    private String createOrderSummary(double totalPrice, boolean addWhippedCream,
                                      boolean addChocolate, String customerName){
        String yesNoCream = "No";
        String yesNoChocolate = "No";
        if(addWhippedCream){
            yesNoCream = "Yes!";
        }
        if(addChocolate){
            yesNoChocolate = "Yes!!";
        }

        String priceMessage = getString(R.string.order_summary_name, customerName);
        priceMessage += "\n" + getString(R.string.add_whipped_cream_permaline, yesNoCream);
        priceMessage += "\n" + getString(R.string.add_chocolate_permaline, yesNoChocolate);
        priceMessage += "\n" + getString(R.string.quantity_permaline, quantity);
        priceMessage += "\n" + getString(R.string.total_permaline,
                NumberFormat.getCurrencyInstance().format(totalPrice));
        priceMessage += "\n" + getString(R.string.thank_you_permaline);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

}
