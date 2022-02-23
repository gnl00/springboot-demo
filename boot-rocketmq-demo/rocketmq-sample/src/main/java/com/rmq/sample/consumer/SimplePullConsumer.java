package com.rmq.sample.consumer;

import com.rmq.sample.contant.SimpleMQCanstant;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SimplePullConsumer 拉模式消费者
 *
 * @author lgn
 * @since 2022/2/23 10:38
 */

public class SimplePullConsumer {

    /**
     * 记录每个队列的消费进度
     */
    private static final Map<MessageQueue, Long> OFFSET_TABLE = new HashMap<>();

    public static void main(String[] args) {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(SimpleMQCanstant.CONSUMER_GROUP);
        consumer.setNamesrvAddr(SimpleMQCanstant.NAME_SERVER);

        try {
            consumer.start();

            // 获取 topic 下的所有队列
            Collection<MessageQueue> queues = consumer.fetchSubscribeMessageQueues(SimpleMQCanstant.TOPIC_DEFAULT);

            System.out.println(queues);
            System.out.println("队列大小： " + queues.size());

            // 遍历所有队列
            for (MessageQueue queue : queues) {
                System.out.println("Consumer from queue: " + queue);

                while (true) {
                    // 拉取消息
                    // pullBlockIfNotFound(消息队列, tag, offset, 最大拉取消息数量)
                    PullResult pullResult =
                            consumer.pullBlockIfNotFound(queue, "*", getQueueOffset(queue), 32);
                    System.out.println("pullResult: " + pullResult);

                    // 更新消费 offset
                    updateQueueOffset(queue, pullResult.getNextBeginOffset());

                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            // 找到消息，输出
                            MessageExt messageExt = pullResult.getMsgFoundList().get(0);

                            System.out.println("收到消息：" + new String(messageExt.getBody()));
                            break;
                        case NO_MATCHED_MSG:
                            // 没有匹配 tag 的消息
                            System.out.println("没有匹配 tag 的消息");
                            break;
                        case NO_NEW_MSG:
                            // 该队列没有新消息，消费offset = 最大offset
                            System.out.println(queue + " 队列没有新消息");
                            break;
                        case OFFSET_ILLEGAL:
                            // 非法 offset
                            System.out.println("非法 offset");
                            break;
                        default:
                            break;
                    }
                }
            }

        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            consumer.shutdown();
        }
    }

    /**
     * 从 OFFSET_TABLE 中获取当前队列的消费 offset
     */
    private static long getQueueOffset(MessageQueue queue) {
        Long offset = OFFSET_TABLE.get(queue);
        if (null != offset) {
            return OFFSET_TABLE.get(queue);
        }
        return 0;
    }

    private static void updateQueueOffset(MessageQueue queue, long offset) {
        OFFSET_TABLE.put(queue, offset);
    }
}
