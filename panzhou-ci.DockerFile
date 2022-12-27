FROM artifactory.dep.devops.cmit.cloud:20101/tools/cmbaas_base/maven:3.6.3-jdk-11 AS builder
WORKDIR /build
ARG PROJECT_PATH
COPY ./${PROJECT_PATH} .
RUN echo "10.252.205.93 artifactory.dep.devops.cmit.cloud" >> /etc/hosts
RUN mvn package -DskipTests --settings ./panzhou-ci-settings.xml

FROM artifactory.dep.devops.cmit.cloud:20101/tools/cmbaas_base/jdk11.0:11.0
WORKDIR /app
#复制上下文目录下的target/demo-1.0.0.jar 到容器里
COPY --from=builder /build/${PROJECT_PATH}/target/shamir-biz-0.0.1-SNAPSHOT.jar  /app/shamir-biz-0.0.1-SNAPSHOT.jar
#COPY /shamir-biz/target/shamir-biz-0.0.1-SNAPSHOT.jar shamir.jar
#bash方式执行，使demo-1.0.0.jar可访问
#RUN新建立一层，在其上执行这些命令，执行结束后，commit 这一层的修改，构成新的镜像。
RUN #bash -c "touch /shamir.jar"

#声明运行时容器提供服务端口，这只是一个声明，在运行时并不会因为这个声明应用就会开启这个端口的服务
EXPOSE 8080

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

#指定容器启动程序及参数   <ENTRYPOINT> "<CMD>"
ENTRYPOINT ["java", "-jar"]
CMD ["/app/shamir-biz-0.0.1-SNAPSHOT.jar"]
