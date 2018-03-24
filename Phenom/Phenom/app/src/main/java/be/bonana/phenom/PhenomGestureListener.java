package be.bonana.phenom;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by liron.bonana on 29/05/2017.
 */

public class PhenomGestureListener extends GestureDetector.SimpleOnGestureListener {

    private Boolean swipeLeft = false;

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {

         /*
         Toast.makeText(getBaseContext(),
          event1.toString() + "\n\n" +event2.toString(),
          Toast.LENGTH_SHORT).show();
         */

        if(event2.getX() < event1.getX()){
         /*   Toast.makeText(getBaseContext(),
                    "Swipe left - startActivity()",
                    Toast.LENGTH_SHORT).show();

            //switch another activity
            Intent intent = new Intent(
                    MainActivity.this, SecondActivity.class);
            startActivity(intent);*/
           return(swipeLeft = true);
        }else{
            return(swipeLeft = false);
        }


    }

    public Boolean isLeftSwipe(){
        return swipeLeft;
    }
}


