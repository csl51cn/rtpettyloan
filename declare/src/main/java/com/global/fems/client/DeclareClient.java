package com.global.fems.client;

import com.global.fems.client.handler.DeclareClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by senlin.deng on 2017-05-16.
 */
public class DeclareClient {

        private  String host;
        private  int port;

        public DeclareClient() {
            this(0);
        }

        public DeclareClient(int port) {
            this("localhost", port);
        }

        public DeclareClient(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public void start() throws Exception {
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(group) // 注册线程池
                        .channel(NioSocketChannel.class) // 使用NioSocketChannel来作为连接用的channel类
                        .remoteAddress(new InetSocketAddress(this.host, this.port)) // 绑定连接端口和host信息
                        .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                System.out.println("connected...");
                                ch.pipeline().addLast(new DeclareClientHandler());
                            }
                        });
                System.out.println("created..");

                ChannelFuture cf = b.connect().sync(); // 异步连接服务器
                System.out.println("connected..."); // 连接完成

                cf.channel().closeFuture().sync(); // 异步等待关闭连接channel
                System.out.println("closed.."); // 关闭完成
            } finally {
                group.shutdownGracefully().sync(); // 释放线程池资源
            }
        }

    public static void main(String[] args) throws Exception {
        new DeclareClient("127.0.0.1", 65535).start(); // 连接127.0.0.1/65535，并启动
    }
}
