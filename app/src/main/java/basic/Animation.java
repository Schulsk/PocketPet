package basic;

import com.example.pocketpet.R;

import java.util.HashMap;

public class Animation {


    public Animation(){

    }

    public static HashMap<String, Integer> getAnimationSet(Pet pet){
        HashMap<String, Integer> animationSet = new HashMap<>();
        if (pet instanceof NormalEgg){
            animationSet.put("angry", R.drawable.egg_idle_animation);
            animationSet.put("dead", R.drawable.egg_idle_animation);
            animationSet.put("eating", R.drawable.egg_idle_animation);
            animationSet.put("happy", R.drawable.egg_idle_animation);
            animationSet.put("idle", R.drawable.egg_idle_animation);
            animationSet.put("sad", R.drawable.egg_idle_animation);
            animationSet.put("walk", R.drawable.egg_idle_animation);
        }
        else if (pet instanceof BeakyBaby){
            animationSet.put("angry", R.drawable.beakybaby_angry_animation);
            animationSet.put("dead", R.drawable.beakybaby_dead_animation);
            animationSet.put("eating", R.drawable.beakybaby_eat_animation);
            animationSet.put("happy", R.drawable.beakybaby_happy_animation);
            animationSet.put("idle", R.drawable.beakybaby_idle_animation);
            animationSet.put("sad", R.drawable.beakybaby_sad_animation);
            animationSet.put("walk", R.drawable.beakybaby_walk_animation);
        }
        else if (pet instanceof BeakyChild){
            animationSet.put("angry", R.drawable.beakychild_angry_animation);
            animationSet.put("dead", R.drawable.beakychild_dead_animation);
            animationSet.put("eating", R.drawable.beakychild_eat_animation);
            animationSet.put("happy", R.drawable.beakychild_happy_animation);
            animationSet.put("idle", R.drawable.beakychild_idle_animation);
            animationSet.put("sad", R.drawable.beakychild_sad_animation);
            animationSet.put("walk", R.drawable.beakychild_walk_animation);
        }
        else if (pet instanceof Beaky){
            animationSet.put("angry", R.drawable.beaky_angry_animation);
            animationSet.put("dead", R.drawable.beaky_dead_animation);
            animationSet.put("eating", R.drawable.beaky_eat_animation);
            animationSet.put("happy", R.drawable.beaky_happy_animation);
            animationSet.put("idle", R.drawable.beaky_idle_animation);
            animationSet.put("sad", R.drawable.beaky_sad_animation);
            animationSet.put("walk", R.drawable.beaky_walk_animation);
        }

        return animationSet;
    }
}
