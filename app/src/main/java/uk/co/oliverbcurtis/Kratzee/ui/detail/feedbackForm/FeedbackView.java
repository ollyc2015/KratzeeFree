package uk.co.oliverbcurtis.Kratzee.ui.detail.feedbackForm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;

public class FeedbackView extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        final EditText your_name     =  findViewById(R.id.your_name);
        final EditText your_email    =  findViewById(R.id.your_email);
        final EditText your_subject  =  findViewById(R.id.your_subject);
        final EditText your_message  =  findViewById(R.id.your_message);

        Button email =  findViewById(R.id.post_message);
        email.setOnClickListener(v -> {

            String name      = your_name.getText().toString();
            String email1    = your_email.getText().toString();
            String subject   = your_subject.getText().toString();
            String message   = your_message.getText().toString();

            if (TextUtils.isEmpty(name)){
                your_name.setError("Enter Your Name");
                your_name.requestFocus();
                return;
            }

            Boolean onError = false;
            if (!isValidEmail(email1)) {
                onError = true;
                your_email.setError("Invalid Email");
                return;
            }

            if (TextUtils.isEmpty(subject)){
                your_subject.setError("Enter Your Subject");
                your_subject.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(message)){
                your_message.setError("Enter Your Message");
                your_message.requestFocus();
                return;
            }

            Intent sendEmail = new Intent(Intent.ACTION_SEND);

            /* Fill it with Data */
            sendEmail.setType("plain/text");
            sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"kratzee@outlook.com"});
            sendEmail.putExtra(Intent.EXTRA_SUBJECT, subject);
            sendEmail.putExtra(Intent.EXTRA_TEXT,
                    "name:"+name+'\n'+"Email ID:"+ email1 +'\n'+"Message:"+'\n'+message);

            /* Send it off to the Activity-Chooser */
            startActivity(Intent.createChooser(sendEmail, "Send mail..."));


        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //Get a Tracker (should auto-report)


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
