---- zset的大key，小key，currentTime，最大限制个数
local key = KEYS[1] -- 定义从参数中获取的key
local minKey = ARGV[1] -- 定义从参数中获取的zset的小键
local timeStamp = ARGV[2] -- 定义从参数中获取的时间戳
local maxLen = tonumber(ARGV[3]) -- 使用ZCARD获取key对应的集合最大长度
----redis.call("ZADD", key , minKey , timeStamp) -- 这里不太一样，直接把本次的数据放入进来了

local result = redis.call("ZCARD", key) -- 然后直接获取集合的长度
print(result)
if(result > maxLen)  -- 超过直接裁剪,因为时间戳放进去后，直接就排好序了，放心裁剪
then
	redis.call("ZREMRANGEBYRANK", key , 0 , result - maxLen) -- 裁剪掉集合前面超出maxLen的数据，也就是旧数据
end
