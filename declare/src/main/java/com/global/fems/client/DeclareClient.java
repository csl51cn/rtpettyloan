package com.global.fems.client;

import com.global.fems.client.handler.DeclareClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DeclareClient {
    private static final Logger logger = LoggerFactory.getLogger(DeclareClient.class);
    public static String HOST = "127.0.0.1";
    public static int PORT = 9999;

    public static Bootstrap bootstrap = getBootstrap();
    /**
     * 初始化Bootstrap
     * @return
     */
    public static final Bootstrap getBootstrap(){
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class);
        b.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                pipeline.addLast("handler", new DeclareClientHandler());
            }
        });
//		b.option(ChannelOption.SO_KEEPALIVE, true);
        return b;
    }

    public static final Channel getChannel(String host,int port){
        Channel channel = null;
        try {
            channel = bootstrap.connect(host, port).sync().channel();
        } catch (Exception e) {
            logger.error(String.format("连接Server(IP[%s],PORT[%s])失败", host,port),e);
            return null;
        }
        return channel;
    }

    public static void sendMsg(Channel channel,Object msg) throws Exception {
        if(channel!=null){
            channel.writeAndFlush(msg).sync();
        }else{
            logger.warn("消息发送失败,连接尚未建立!");
        }
    }

    public static void sendMsg(Object msg) throws Exception {
        Channel channel = getChannel("113.200.27.105", 7074);
        if(channel !=null){
            channel.writeAndFlush(msg).sync();
        }else{
            logger.warn("消息发送失败,连接尚未建立!");
        }
    }
    public static void main(String[] args) throws Exception {
        try {
            long t0 = System.nanoTime();
            byte[] value = null;
            Channel channel = null;
            for (int i = 0; i < 50000; i++) {
                channel = getChannel(HOST, PORT);
                value = (i+",你好").getBytes();
                ByteBufAllocator alloc = channel.alloc();
                ByteBuf buf = alloc.buffer(value.length);
                buf.writeBytes(value);
                DeclareClient.sendMsg(channel,buf);
            }
            long t1 = System.nanoTime();
            System.out.println((t1-t0)/1000000.0);
            Thread.sleep(5000);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
