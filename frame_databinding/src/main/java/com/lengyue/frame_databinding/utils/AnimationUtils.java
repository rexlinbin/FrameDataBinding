package com.lengyue.frame_databinding.utils;

import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

public class AnimationUtils {
    public static Animation showTop(int duration){
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(duration);
        return animation;
    }

    public static Animation showTop(int duration, Animation.AnimationListener listener){
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(duration);
        animation.setAnimationListener(listener);
        return animation;
    }

    public static Animation hideTop(int duration){
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(duration);
        return animation;
    }

    public static Animation hideTop(int duration, Animation.AnimationListener listener){
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(duration);
        animation.setAnimationListener(listener);
        return animation;
    }

    public static Animation showBottom(int duration){
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(duration);
        return animation;
    }

    public static Animation showBottom(int duration, Animation.AnimationListener listener){
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(duration);
        animation.setAnimationListener(listener);
        return animation;
    }

    public static Animation hideBottom(int duration){
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(duration);
        return animation;
    }

    public static Animation hideBottom(int duration, Animation.AnimationListener listener){
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(duration);
        animation.setAnimationListener(listener);
        return animation;
    }
}
