# 先 build
# docker build -t kfk-docker .
# 再 run，run 之前需要修改 server.properties 中的 advertised.listeners
# docker run --name=kfk -d -v "./server.properties:/opt/kafka/config/server.properties" -p 9092:9092 kfk-docker

# 使用基础镜像
FROM adoptopenjdk/openjdk8

# 先下载 Kafka 并解压到当前目录，重命名为 kafka
# https://kafka.apache.org/documentation/#quickstart
# 复制本地的 Kafka
COPY kafka /opt/kafka

# 设置工作目录
WORKDIR /opt

# 设置环境变量
ENV KAFKA_HOME /opt/kafka
ENV PATH $KAFKA_HOME/bin:$PATH

# 从当前目录 kafka 文件夹内复制 zookeeper.properties 和 server.properties 到当前文件夹，并配置好
# 这步可以省略
COPY zookeeper.properties $KAFKA_HOME/config/zookeeper.properties
COPY server.properties $KAFKA_HOME/config/server.properties

# 暴露 Kafka 和 ZooKeeper 端口
EXPOSE 2181 9092

# 启动 ZooKeeper 和 Kafka
CMD ["sh", "-c", "$KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties & $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties"]