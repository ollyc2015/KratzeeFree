package uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaRegister.teamMember;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;

public interface TeamMemberTriviaRegisterContract {

    interface View {

        void initView();
        void confirmTeamMemberSubmission(List<String> teamMemberNameArray, List<String> teamMemberStudentNumberArray);
    }


    interface Presenter {

        void attachView(TeamMemberTriviaRegisterContract.View view);
        void confirmAddTeamMember(EditText et_team_name1, EditText et_team_student_number1, EditText et_team_name2, EditText et_team_student_number2, LinearLayout mLayout, TeamMemberTriviaRegisterView teamMemberTriviaRegisterView);
        void validateSubmittedNames(EditText et_team_name1, EditText et_team_student_number1, EditText et_team_name2, EditText et_team_student_number2, TeamMemberTriviaRegisterView context, ProgressBar progress);
        boolean storeTeamMembersInSQLiteDB(List<String> teamMemberNameArray, List<String> teamMemberStudentNumberArray, KratzeeDatabase kratzeeDatabase);

    }

    interface dynamicTeamMember {

        void createNewTeamMember(LinearLayout mLayout, TeamMemberTriviaRegisterView context, List<EditText> dynamicTeamMemberNameList, List<EditText> dynamicTeamMemberStudentNumberList);
    }

}
