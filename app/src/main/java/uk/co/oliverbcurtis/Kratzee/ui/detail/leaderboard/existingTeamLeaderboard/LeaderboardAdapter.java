package uk.co.oliverbcurtis.Kratzee.ui.detail.leaderboard.existingTeamLeaderboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.TeamMember;

public class LeaderboardAdapter  extends ArrayAdapter<TeamMember> {

    public LeaderboardAdapter(@NonNull Context context, List<TeamMember> leaderboardList) {
        super(context,0,leaderboardList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get the data item for this position
        TeamMember teamMember = getItem(position);
        notifyDataSetChanged();


        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.leaderboard_layout, parent, false);
        }

        // Lookup view for data population
        TextView tv_position = convertView.findViewById(R.id.tv_position);
        TextView tv_points = convertView.findViewById(R.id.tv_points);
        TextView tv_student_name = convertView.findViewById(R.id.tv_student_name);

        int playerPostion = position +1;

        // Populate the data into the template view using the data object
        tv_position.setText("#"+playerPostion);
        tv_points.setText(teamMember.getPoints()+ " Points");
        tv_student_name.setText(teamMember.getFullName());

        // Return the completed view to render on screen
        return convertView;
    }

}
