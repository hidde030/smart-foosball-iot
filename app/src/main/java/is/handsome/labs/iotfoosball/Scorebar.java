package is.handsome.labs.iotfoosball;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;

public class Scorebar {
    //TODO devide to model and view
    ArrayList<ScoreViewPager> scorebarsView;
    CurentGame curentGame;

    public Scorebar (final ArrayList<ScoreViewPager> scorebarsView, Context context) {
        this.scorebarsView = scorebarsView;
        scorebarsView.get(0).addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //TODO need i use Math lib?
                if (scorebarsView.size() > 1) scorebarsView.get(1).setCurrentItem(((position - (position % 10)) / 10), true);
                if (curentGame != null) curentGame.notify_game();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        scorebarsView.get(0).setAdapter(new ScorebarPagerAdapter(context, (int) Math.pow(10,scorebarsView.size())));
        for (int i = 1; i < scorebarsView.size(); i++) {
            ScorebarPagerAdapter spAdapter = new ScorebarPagerAdapter(context, (int) Math.pow(10,scorebarsView.size()-1));
            scorebarsView.get(i).setFirstDigitViewPager(scorebarsView.get(0));
            scorebarsView.get(i).setListing(false);
            scorebarsView.get(i).setAdapter(spAdapter);
            if (i != (scorebarsView.size() - 1)) {
                final int finalI = i;
                scorebarsView.get(i).addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        scorebarsView.get(finalI +1).setCurrentItem(((position - (position % 10)) / 10), true);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        }
    }

    public void setCurentGame(CurentGame curentGame) {
        this.curentGame = curentGame;
    }

    public void goal() {
        Log.d("score2", "REALY GOAL" + (scorebarsView.get(0).getCurrentItem() + 1));
        scorebarsView.get(0).setCurrentItem(scorebarsView.get(0).getCurrentItem() + 1);
    }
}
