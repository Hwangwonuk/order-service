
# Kafka Docker compose
#version: '2'
#services:
#  zookeeper:
#    image: wurstmeister/zookeeper
#    ports:
#      - "2181:2181"
#    networks:
#      my-network:
#        ipv4_address: 172.19.0.100
#  kafka:
#    # build: .
#    image: wurstmeister/kafka
#    ports:
#      - "9092:9092"
#    environment:
#      KAFKA_ADVERTISED_HOST_NAME: 172.19.0.101
#      KAFKA_CREATE_TOPICS: "test:1:1"
#      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
#    volumes:
#      - /var/run/docker.sock:/var/run/docker.sock
#    depends_on:
#      - zookeeper
#    networks:
#      my-network:
#        ipv4_address: 172.19.0.101
#
#networks:
#  my-network:
#    name: ecommerce-network

# docker compose up
# docker-compose -f docker-compose-single-broker.yml up -d

# Zipkin 컨테이너 생성 및 실행
# docker run -d -p 9411:9411 --network ecommerce-network --name zipkin openzipkin/zipkin

