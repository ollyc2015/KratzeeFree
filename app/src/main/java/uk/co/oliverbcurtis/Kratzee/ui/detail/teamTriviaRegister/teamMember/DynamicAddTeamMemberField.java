package uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaRegister.teamMember;

import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import uk.co.oliverbcurtis.Kratzee.R;

import static android.content.ContentValues.TAG;
import static com.rd.utils.DensityUtils.dpToPx;

public class DynamicAddTeamMemberField implements TeamMemberTriviaRegisterContract.dynamicTeamMember {


    @Override
    public void createNewTeamMember(LinearLayout mLayout, TeamMemberTriviaRegisterView context, List<EditText> dynamicTeamMemberNameList, List<EditText> dynamicTeamMemberStudentNumberList){


        try {

            EditText et_studentName, et_studentNumber;

            et_studentName = new EditText(context);
            et_studentNumber = new EditText(context);

            LinearLayout.LayoutParams studentName = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(43), 1);
            LinearLayout.LayoutParams studentNumber = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(43), 1);

            et_studentName.setBackground(context.getResources().getDrawable(R.drawable.question_background));
            et_studentNumber.setBackground(context.getResources().getDrawable(R.drawable.question_background));

            et_studentName.setHint("Full Name");
            et_studentNumber.setHint("Student Number");

            et_studentName.setMaxLines(1);
            et_studentNumber.setMaxLines(1);

            Typeface regular = ResourcesCompat.getFont(context, R.font.regular);
            et_studentName.setTypeface(regular, Typeface.BOLD);
            et_studentNumber.setTypeface(regular, Typeface.BOLD);


            et_studentName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)}); //Filter to 20 characters
            et_studentNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)}); //Filter to 20 characters

            et_studentName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            et_studentNumber.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

            et_studentName.setGravity(Gravity.CENTER);
            et_studentNumber.setGravity(Gravity.CENTER);

            //to align dynamic text to the static edittext - margin left, top, right, bottom
            studentName.setMargins(dpToPx(60), dpToPx(30), dpToPx(60), 0);
            studentNumber.setMargins(dpToPx(60), dpToPx(20), dpToPx(60), dpToPx(30));

            et_studentName.setLayoutParams(studentName);
            et_studentNumber.setLayoutParams(studentNumber);

            dynamicTeamMemberNameList.add(et_studentName);
            dynamicTeamMemberStudentNumberList.add(et_studentNumber);

            mLayout.addView(et_studentName);
            mLayout.addView(et_studentNumber);


        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

    }
}
