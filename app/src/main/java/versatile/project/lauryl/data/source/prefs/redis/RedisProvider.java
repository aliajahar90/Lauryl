package versatile.project.lauryl.data.source.prefs.redis;

import android.content.Context;

import versatile.project.lauryl.data.source.prefs.redis.base.KRedis;
import versatile.project.lauryl.data.source.prefs.redis.impl.KRedisImpl;

public class RedisProvider {
    private static KRedis redis;

    public static KRedis  getRedis(Context context){
        if(redis == null){
            redis = new KRedisImpl(context.getSharedPreferences("lauryl",Context.MODE_PRIVATE));
        }
        return redis;
    }
}
