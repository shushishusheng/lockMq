package cn.excellent.seckill.service;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

//import java.util.UUID;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private Redisson redisson;

    @Override
    public String startShop() {
        String lockKey = "linux";
        String msg = "";
//        String uuid = UUID.randomUUID().toString();
//        stringRedisTemplate.delete("linux");
//        Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent("linux", uuid);
//        if (aBoolean) {}
        RLock lock = redisson.getLock(lockKey);

        try {
            lock.lock();
            String stocks = stringRedisTemplate.opsForValue().get("lsd");
            int stock = Integer.parseInt(stocks);
            if (stock > 0) {
                int realstock = stock - 1;
               stringRedisTemplate.opsForValue().set("lsd", "" + realstock);
               msg = "扣减库存成功，当前库存为"+realstock;
//                System.out.println(msg);
            }
            else {
                msg = "库存余额不足";
//                System.out.println(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


        return msg;
    }

    @Override
    public void plan() {
        stringRedisTemplate.delete("lsd");
        stringRedisTemplate.opsForValue().setIfAbsent("lsd","100" );
    }
}
