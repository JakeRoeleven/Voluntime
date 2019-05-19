package team7.voluntime.Utilities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import team7.voluntime.Activities.EventDetailsActivity;
import team7.voluntime.Activities.EventRegisterActivity;
import team7.voluntime.Activities.RateVolunteerActivity;
import team7.voluntime.Activities.VolunteerDetailsActivity;
import team7.voluntime.Activities.VolunteerHistoryActivity;
import team7.voluntime.Domains.Event;
import team7.voluntime.Domains.Volunteer;
import team7.voluntime.Fragments.Charities.CharityViewEventsFragment;
import team7.voluntime.Fragments.Volunteers.VolunteerEventsListFragment;
import team7.voluntime.R;


public class EventListAdapter<T> extends ArrayAdapter<Event> {
    private static final String TAG = "EventListAdapter";

    private VolunteerHistoryActivity volunteerHistoryActivity; // Only accessed from VolunteerHistoryActivity
    private VolunteerDetailsActivity volunteerDetailsActivity; // Only accessed from VolunteerDetailsActivity

    private Context mContext;
    private T screen;
    int mResource;
    private String eventId;


    public EventListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Event> objects, T screen) {
        super(context, resource, objects);
        this.mContext = context;
        this.screen = screen;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        eventId = Objects.requireNonNull(getItem(position)).getId();

        String title = getItem(position).getTitle();
        String description = getItem(position).getDescription();
        String category = getItem(position).getCategory();
        String location = getItem(position).getLocation();
        String date = getItem(position).getDate();
        String createdTime = getItem(position).getCreatedTime();
        String organisers = getItem(position).getOrganisers();
        int minimum = getItem(position).getMinimum();
        int maximum = getItem(position).getMaximum();

        final boolean isPastEvent = getItem(position).isPastEvent();

        final HashMap<String, String> volunteers = getItem(position).getVolunteers();

        final Event event = new Event(eventId, title, description, category, location, date, createdTime, organisers, minimum, maximum, volunteers);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView dateTV = convertView.findViewById(R.id.adapterEventDateTV);
        TextView titleTV = convertView.findViewById(R.id.adapterEventTitleTV);
        ImageView adapterEventIV1 = convertView.findViewById(R.id.adapterEventIV1);
        ImageView adapterEventInfoIV = convertView.findViewById(R.id.adapterEventInfoIV);
        ImageView adapterEventRatingIV = convertView.findViewById(R.id.adapterEventRatingIV);


        if (screen.getClass().equals(CharityViewEventsFragment.class)) {
            adapterEventInfoIV.setVisibility(View.VISIBLE);

            adapterEventInfoIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(@NonNull View view) {
                    Intent intent = new Intent(mContext, EventDetailsActivity.class);
                    intent.putExtra("event", event);
                    intent.putExtra("parentActivity", CharityViewEventsFragment.class.toString());

                    // This is being used on event details to remove pending and registered list and will just show the attended volunteers
                    intent.putExtra("isPastEvent", isPastEvent);
                    intent.putExtra("volunteers", event.getVolunteers());
                    mContext.startActivity(intent);
                }

            });
        } else if (screen.getClass().equals(VolunteerDetailsActivity.class)) {
            adapterEventInfoIV.setVisibility(View.VISIBLE);
            adapterEventRatingIV.setVisibility(View.VISIBLE);
            adapterEventInfoIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(@NonNull  View view) {
                    Intent intent = new Intent(mContext, EventDetailsActivity.class);
                    intent.putExtra("event", event);
                    intent.putExtra("parentActivity", VolunteerDetailsActivity.class.toString());
                    mContext.startActivity(intent);
                }
            });

            adapterEventRatingIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(@NonNull  View view) {
                    volunteerDetailsActivity = (VolunteerDetailsActivity) screen;
                    Intent intent = new Intent(mContext, RateVolunteerActivity.class);
                    intent.putExtra("event", event);
                    intent.putExtra("volunteer", volunteerDetailsActivity.getVolunteer());
                    intent.putExtra("isPastEvent", isPastEvent);
                    mContext.startActivity(intent);
            }
            });

        } else if (screen.getClass().equals(VolunteerEventsListFragment.class)) {
            adapterEventIV1.setVisibility(View.VISIBLE);

            adapterEventIV1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(@NonNull  View view) {
                    Intent intent = new Intent(mContext, EventRegisterActivity.class);
                    intent.putExtra("event", event);
                    mContext.startActivity(intent);

                }
            });

        } else if (screen.getClass().equals(VolunteerHistoryActivity.class)) {
            adapterEventInfoIV.setVisibility(View.VISIBLE);
            adapterEventRatingIV.setVisibility(View.VISIBLE);

            adapterEventInfoIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(@NonNull View view) {
                    Intent intent = new Intent(mContext, EventDetailsActivity.class);
                    intent.putExtra("event", event);
                    intent.putExtra("parentActivity", VolunteerHistoryActivity.class.toString());

                    // This is being used on event details to remove pending and registered list and will just show the attended volunteers
                    intent.putExtra("isPastEvent", isPastEvent);
                    mContext.startActivity(intent);


                }
            });
            adapterEventRatingIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(@NonNull View view) {
                    volunteerHistoryActivity = (VolunteerHistoryActivity) screen;
                    Intent intent = new Intent(mContext, RateVolunteerActivity.class);
                    intent.putExtra("event", event);
                    intent.putExtra("volunteer", volunteerHistoryActivity.getVolunteer());
                    intent.putExtra("isPastEvent", isPastEvent);
                    mContext.startActivity(intent);
                }
            });

        }

        dateTV.setText(date);
        titleTV.setText(title);
        return convertView;
    }
}

