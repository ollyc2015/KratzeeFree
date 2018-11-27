package uk.co.oliverbcurtis.Kratzee.ui.detail.leaderboard.existingTeamLeaderboard;

import android.content.SharedPreferences;

import java.util.List;

import uk.co.oliverbcurtis.Kratzee.model.TeamMember;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;

public interface LeaderboardContract {

    interface View {
        //Method used to set some data

        void initView();
        void populateLeaderboard(List<TeamMember> array1);

    }

    interface Presenter {

        void attachView(LeaderboardContract.View view);
        void getTeamMemberPoints(KratzeeDatabase kratzeeDatabase, SharedPreferences pref);

    }
}
