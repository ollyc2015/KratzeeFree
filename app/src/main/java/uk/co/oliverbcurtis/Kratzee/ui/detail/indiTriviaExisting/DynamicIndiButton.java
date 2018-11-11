package uk.co.oliverbcurtis.Kratzee.ui.detail.indiTriviaExisting;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import java.util.List;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Individual;
import static android.content.ContentValues.TAG;
import static com.rd.utils.DensityUtils.dpToPx;


public class DynamicIndiButton {

public void createButton(IndiTriviaExistView view, Individual individual) {

    List indiIDArray = individual.getIndividual_student_id_list();
    List indiPointsArray = individual.getStudent_points_list();
    int count = 0;

    for (final Object value : indiIDArray)

        try {

            /*Dynamically create new Button which includes the student name/number
             */
            final AppCompatButton btn_indiName = new AppCompatButton(view);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            btn_indiName.setBackgroundColor(Color.parseColor("#FF4DCBBF"));
            btn_indiName.setTextColor(Color.WHITE);
            btn_indiName.setText(individual.getFullNameList().get(count).toString() + " | " + individual.getStudent_number_list().get(count).toString());
            btn_indiName.setHint(indiPointsArray.get(count).toString());
            count++;
            //set the Tag value of the question ID for database submission for selection during edit method
            btn_indiName.setTag(value);
            btn_indiName.setTypeface(null, Typeface.BOLD);
            //set button style to custom button
            btn_indiName.setBackground(view.getResources().getDrawable(R.drawable.mybutton));
            //btn_question.setText("Question " + value);
            btn_indiName.setGravity(Gravity.CENTER);
            params.setMargins(150, dpToPx(10), 150, dpToPx(10));
            btn_indiName.setPadding(0, 0, 0, 0);
            btn_indiName.setLayoutParams(params);

            //Pass the created button to populateStudentNames method found in IndiTriviaExistView
            view.populateStudentNames(btn_indiName, view);

        } catch (Exception e) {
            Log.d(TAG, "Failed to create new button");
        }
    }
}
