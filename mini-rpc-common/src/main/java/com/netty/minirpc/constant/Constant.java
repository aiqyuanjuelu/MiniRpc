package com.netty.minirpc.constant;

final public class Constant {

    //魔数头
    public static final long MAGIC_NUMBER_HEADER = 0X3E5643;
    //魔数体
    public static final long MAGIC_NUMBER_BODY = 0X3488283;
    //主版本号
    public static final short MAIN_VERSION = 0X0010;
    //次版本号
    public static final short SUB_VERSION = 0X0011;
    //修改版本号
    public static final short MODIFY_VERSION = 0X0012;
    //最小字段长度
    public static final short MIN_MESSAGE_LENGTH = 8 * 2 + 3 * 2 + 2 * 2 + 4;
    //最大字段长度
    public static final short MAX_MESSAGE_LENGTH = 2048;
}
