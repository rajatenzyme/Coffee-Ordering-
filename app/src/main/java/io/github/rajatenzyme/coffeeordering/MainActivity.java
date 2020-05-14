package io.github.rajatenzyme.coffeeordering;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.text.NumberFormat;
import android.widget.*;
import android.content.*;


import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.*;
import java.util.*;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity= 1;

    public void submitOrder(View view)
    {

        String ord_id= UUID.randomUUID().toString().replace("-","");

        EditText text= (EditText) findViewById(R.id.name_field);
        String name= text.getText().toString();
        if (name.matches(""))
        {
            Toast.makeText(this, "Please fill all the entries! ", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText text1= (EditText) findViewById(R.id.mob_field);
        String mob= text1.getText().toString();
        if (mob.matches(""))
        {
            Toast.makeText(this, "Please fill all the entries! ", Toast.LENGTH_SHORT).show();
            return;
        }


        CheckBox whippedCream = (CheckBox) findViewById(R.id.has_whipped_cream);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox Chocolate = (CheckBox) findViewById(R.id.has_chocolate);
        boolean hasChocolate = Chocolate.isChecked();
        String priceMessage ="Order Id: " + ord_id+ "\nName: " + name + "\nMobile No. " + mob + "\nAdd Whipped Cream: " + whipVal(hasWhippedCream)  + "\nAdd Chocolate: " + chocVal(hasChocolate) + "\nQuantity: " + quantity + "\nTotal: " + "₹" + calculatePrice(hasWhippedCream, hasChocolate) + " \nThank You for your order!";



        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"rajatmittal2012@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Order summary of " + name + " from Coffee Ordering App");
        i.putExtra(Intent.EXTRA_TEXT   , priceMessage);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }


//		Intent intent =  new Intent(Intent.ACTION_SENDTO);
//		intent.setData(Uri.parse("mailto:"));
//		intent.putExtra(Intent.EXTRA_SUBJECT, "Order summary of " + name + " from Coffee Ordering App");
//		intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
//		if (intent.resolveActivity(getPackageManager()) != null)
//		{
//			startActivity(intent);
//		}

        displayMessage(priceMessage);

    }


    private String whipVal(boolean whipped)
    {
        if (whipped)
        {
            return "Yes";
        }
        else
        {
            return "No";
        }
    }

    private String chocVal(boolean choco)
    {
        if (choco)
        {
            return "Yes";
        }
        else
        {
            return "No";
        }
    }



    private int calculatePrice(boolean addWhippedCream, boolean addChocolate)
    {
        int basePrice = 100;
        if (addWhippedCream)
        {
            basePrice += 25;
        }
        if (addChocolate)
        {
            basePrice += 50;
        }
        return quantity * basePrice;
    }


    public void increment(View view)
    {
        if (quantity == 10)
        {
            Toast.makeText(this, "You can't order more than 10 Coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }
    public void decrement(View view)
    {
        if (quantity == 1)
        {
            Toast.makeText(this, "You can't order less than 1 Coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number)
    {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message)
    {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }
}
