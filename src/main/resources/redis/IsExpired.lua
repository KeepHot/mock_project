---- zset的大key，小key，currentTime，最大限制个数
local key = KEYS[1] -- 定义从参数中获取的key
local value = ARGV[1] -- 定义从参数中获取的value
local currentTime = ARGV[2] -- 定义从参数中获取的时间戳
local score = redis.call("zscore", key, value)+300000 -- 放入数据
print(score)
local curTime = tonumber(currentTime)
print(curTime)
if (score < curTime)  -- 判断是否过期
then
    redis.call("zrem", key, value) -- 删除过期元素
    return true
end
return false

