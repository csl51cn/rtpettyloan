package com.pactera.fems.mq.util;


public class MQConfig {
    private String mqServerIP;

    private int mqServerPort;

    private int mqServerCCSID;

    private String mqServerChannel;

    private String queueManagerName;

    private String sendQueueName;
    private String recvQueueName;
    private int timeout;
    private int mqexpiry;

    public String getMqServerIP() {
        return this.mqServerIP;
    }

    public void setMqServerIP(String mqServerIP) {
        this.mqServerIP = mqServerIP;
    }

    public int getMqServerPort() {
        return this.mqServerPort;
    }

    public void setMqServerPort(int mqServerPort) {
        this.mqServerPort = mqServerPort;
    }

    public int getMqServerCCSID() {
        return this.mqServerCCSID;
    }

    public void setMqServerCCSID(int mqServerCCSID) {
        this.mqServerCCSID = mqServerCCSID;
    }

    public String getMqServerChannel() {
        return this.mqServerChannel;
    }

    public void setMqServerChannel(String mqServerChannel) {
        this.mqServerChannel = mqServerChannel;
    }

    public String getQueueManagerName() {
        return this.queueManagerName;
    }

    public void setQueueManagerName(String queueManagerName) {
        this.queueManagerName = queueManagerName;
    }

    public String getSendQueueName() {
        return this.sendQueueName;
    }

    public void setSendQueueName(String sendQueueName) {
        this.sendQueueName = sendQueueName;
    }

    public String getRecvQueueName() {
        return this.recvQueueName;
    }

    public void setRecvQueueName(String recvQueueName) {
        this.recvQueueName = recvQueueName;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMqexpiry() {
        return this.mqexpiry;
    }

    public void setMqexpiry(int mqexpiry) {
        this.mqexpiry = mqexpiry;
    }
}


