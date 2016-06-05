package com.example.sinhaguild.staycationapp;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sinhaguild.staycationapp.data.Venue;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.mutualmobile.cardstack.CardStackAdapter;
import com.squareup.picasso.Picasso;
import com.tramsun.libs.prefcompat.Pref;

/**
 * Created by anuragsinha on 16-06-04.
 */
public class StaycationAdapter extends CardStackAdapter implements CompoundButton.OnCheckedChangeListener {

    public static final String TAG = StaycationAdapter.class.getSimpleName();
    private static int[] bgColorIds;
    private static String[] mStringCardTitles;
    private String[] mWeatherTagList;
    private Venue[] mVenuesFood;
    private Venue[] mVenuesShop;
    private final LayoutInflater mInflater;
    private final Context mContext;
    private int mShopsCardCounter = 0;
    private int mFoodCardCounter = 0;
    private OnRestartRequest mCallback;
    private Runnable updateSettingsView;

    public StaycationAdapter(StaycationActivity activity, String[] mWeatherTagList, Venue[] food, Venue[] shops) {
        super(activity);
        mContext = activity.getApplicationContext();
        mInflater = LayoutInflater.from(activity);
        mCallback = activity;
        this.mWeatherTagList = mWeatherTagList;
        this.mVenuesFood = food;
        this.mVenuesShop = shops;
        mStringCardTitles = new String[]{
                "Breakfast",
                "Activity",
                "Lunch",
                "Activity",
                "Dinner",
                "Activity"
        };
//        bgColorIds = new int[]{
//                R.color.card1_bg,
//                R.color.card2_bg,
//                R.color.card3_bg,
//                R.color.card4_bg,
//                R.color.card5_bg,
//                R.color.card6_bg,
//                R.color.card7_bg,
//        };

        /**
         * card animation constants
         */
        Pref.putBoolean(Prefs.PARALLAX_ENABLED, true);
        Prefs.setReverseClickAnimationEnabled(true);
        Pref.putBoolean(Prefs.SHOW_INIT_ANIMATION, false);

    }

    @Override
    public int getCount() {
        return mStringCardTitles.length;//bgColorIds.length;
    }


    @Override
    public View createView(final int position, final ViewGroup container) {
//        if (position == 0) return getSettingsView(container);

        CardView root = (CardView) mInflater.inflate(R.layout.staycation_card, container, false);
        TextViewPlus cardTitle = (TextViewPlus) root.findViewById(R.id.card_title);
        TextViewPlus cardSubtitle = (TextViewPlus) root.findViewById(R.id.card_title_subtext);
        cardTitle.setText(mStringCardTitles[position]);
        //cardSubtitle.setText(mWeatherTagList[position]);

        ImageView restaurantPoster = (ImageView) root.findViewById(R.id.restaurant_poster);
        TextViewPlus restaurantTitle = (TextViewPlus) root.findViewById(R.id.restaurant_title);
        TextViewPlus countCheckins = (TextViewPlus) root.findViewById(R.id.countCheckins);
        TextViewPlus restaurantDescriptions = (TextViewPlus) root.findViewById(R.id.restaurant_description);
        LikeButton likeButton = (LikeButton) root.findViewById(R.id.heart_button);
        LikeButton disLikeButton = (LikeButton) root.findViewById(R.id.heart_break_button);


        //Make sure venues and shops keep cycling
        Venue mShopTemp;
        Venue mFoodTemp;

        if (mShopsCardCounter < mVenuesShop.length){
            mShopTemp = mVenuesFood[mShopsCardCounter++];
        }else {
            mShopsCardCounter = 0;
            mShopTemp = mVenuesFood[mShopsCardCounter++];
            Toast.makeText(mContext, "No more venues remain!", Toast.LENGTH_SHORT).show();
        }

        if (mFoodCardCounter < mVenuesFood.length){
            mFoodTemp = mVenuesShop[mShopsCardCounter++];
        }else {
            mFoodCardCounter = 0;
            mFoodTemp = mVenuesShop[mShopsCardCounter++];
            Toast.makeText(mContext, "No more venues remain!", Toast.LENGTH_SHORT).show();
        }

        switch (position) {
            case 0:
            case 2:
            case 4:
                Picasso.with(mContext).load(mFoodTemp.getPhoto_url()).into(restaurantPoster);
                restaurantTitle.setText(mFoodTemp.getName());
                countCheckins.setText(String.valueOf(mFoodTemp.getRating()));
                restaurantDescriptions.setText(mFoodTemp.getLocation_formatted_address());
                break;
            case 1:
            case 3:
            case 5:
                Picasso.with(mContext).load(mShopTemp.getPhoto_url()).into(restaurantPoster);
                restaurantTitle.setText(mShopTemp.getName());
                countCheckins.setText(String.valueOf(mShopTemp.getRating()));
                restaurantDescriptions.setText(mShopTemp.getLocation_formatted_address());
                break;
        }


        /**
         * Like Button behavior
         */
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Toast.makeText(mContext, "Great! You like our suggestion.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                createView(position, container);
            }
        });

        /**
         * disLike Button behavior
         */
        disLikeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                createView(position, container);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(mContext, "Does this mean you like this suggestion ?", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
//        root.setCardBackgroundColor(ContextCompat.getColor(mContext, bgColorIds[position]));
//        TextView cardTitle = (TextView) root.findViewById(R.id.card_title);
//        cardTitle.setText(mContext.getResources().getString(R.string.card_title, position));

    }

    @Override
    protected Animator getAnimatorForView(View view, int currentCardPosition, int selectedCardPosition) {
        if (Prefs.isReverseClickAnimationEnabled()) {
            if (currentCardPosition > selectedCardPosition) {
                return ObjectAnimator.ofFloat(view, View.Y, (int) view.getY(), getCardFinalY(currentCardPosition));
            } else {
                return ObjectAnimator.ofFloat(view, View.Y, (int) view.getY(), getCardOriginalY(0) + (currentCardPosition * getCardGapBottom()));
            }
        } else {
            return super.getAnimatorForView(view, currentCardPosition, selectedCardPosition);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

//    private View getSettingsView(ViewGroup container) {
//        CardView root = (CardView) mInflater.inflate(R.layout.settings_card, container, false);
//        root.setCardBackgroundColor(ContextCompat.getColor(mContext, bgColorIds[0]));
//
//        final Switch showInitAnimation = (Switch) root.findViewById(R.id.show_init_animation);
//        final Switch parallaxEnabled = (Switch) root.findViewById(R.id.parallax_enabled);
//        final Switch reverseClickAnimation = (Switch) root.findViewById(R.id.reverse_click_animation);
//        final EditText parallaxScale = (EditText) root.findViewById(R.id.parallax_scale);
//        final EditText cardGap = (EditText) root.findViewById(R.id.card_gap);
//        final EditText cardGapBottom = (EditText) root.findViewById(R.id.card_gap_bottom);
//
//        updateSettingsView = new Runnable() {
//            @Override
//            public void run() {
//                showInitAnimation.setChecked(Prefs.isShowInitAnimationEnabled());
//                showInitAnimation.setOnCheckedChangeListener(MyCardStackAdapter.this);
//
//                reverseClickAnimation.setChecked(Prefs.isReverseClickAnimationEnabled());
//                reverseClickAnimation.setOnCheckedChangeListener(MyCardStackAdapter.this);
//
//                boolean isParallaxEnabled = Prefs.isParallaxEnabled();
//                parallaxEnabled.setChecked(isParallaxEnabled);
//                parallaxEnabled.setOnCheckedChangeListener(MyCardStackAdapter.this);
//
//                parallaxScale.setText("" + Prefs.getParallaxScale(mContext));
//                parallaxScale.setEnabled(isParallaxEnabled);
//
//                cardGap.setText("" + Prefs.getCardGap(mContext));
//
//                cardGapBottom.setText("" + Prefs.getCardGapBottom(mContext));
//            }
//        };
//
//        updateSettingsView.run();
//
//        Button restartActivityButton = (Button) root.findViewById(R.id.restart_activity);
//        restartActivityButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updatePrefsIfRequired(parallaxScale);
//                updatePrefsIfRequired(cardGap);
//                updatePrefsIfRequired(cardGapBottom);
//                mCallback.requestRestart();
//            }
//        });
//
//        Button resetDefaultsButton = (Button) root.findViewById(R.id.reset_defaults);
//        resetDefaultsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Prefs.resetDefaults(mContext);
//                updateSettingsView.run();
//            }
//        });
//
//        return root;
//    }

//    private void updatePrefsIfRequired(EditText view) {
//        String text = view.getText().toString();
//        int value;
//
//        try {
//            value = Integer.parseInt(text);
//        } catch (Exception e) {
//            value = Integer.MIN_VALUE;
//        }
//        if (value == Integer.MIN_VALUE) {
//            log.e("Invalid value for " + view.getResources().getResourceName(view.getId()));
//            return;
//        }
//
//        switch (view.getId()) {
//            case R.id.parallax_scale:
//                log.d("parallax_scale now is " + Integer.parseInt(text));
//                Pref.putInt(Prefs.PARALLAX_SCALE, Integer.parseInt(text));
//                break;
//            case R.id.card_gap:
//                log.d("card_gap now is " + Integer.parseInt(text));
//                Pref.putInt(Prefs.CARD_GAP, Integer.parseInt(text));
//                break;
//            case R.id.card_gap_bottom:
//                log.d("card_gap_bottom now is " + Integer.parseInt(text));
//                Pref.putInt(Prefs.CARD_GAP_BOTTOM, Integer.parseInt(text));
//                break;
//        }
//    }

}
