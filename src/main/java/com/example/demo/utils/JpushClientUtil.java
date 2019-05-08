//package com.example.demo.utils;
//
//import cn.jiguang.common.resp.APIConnectionException;
//import cn.jiguang.common.resp.APIRequestException;
//import cn.jpush.api.JPushClient;
//import cn.jpush.api.push.PushResult;
//import cn.jpush.api.push.model.Message;
//import cn.jpush.api.push.model.Options;
//import cn.jpush.api.push.model.Platform;
//import cn.jpush.api.push.model.PushPayload;
//import cn.jpush.api.push.model.audience.Audience;
//import cn.jpush.api.push.model.notification.AndroidNotification;
//import cn.jpush.api.push.model.notification.IosNotification;
//import cn.jpush.api.push.model.notification.Notification;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//
//
///**
// * @author liuxinghong
// * @Description: 极光推送 工具类
// * @date 2018/5/21/0219:48
// */
//public class JpushClientUtil {
//
//    private static Logger log= LoggerFactory.getLogger(JpushClientUtil.class);
//
//        private final static String appKey = "73be2b3c81006530d63e009a";
//
//        private final static String masterSecret = "17832f1b426a3cdb47bfaf8f";
//
//        private static JPushClient jPushClient = new JPushClient(masterSecret.trim(),appKey.trim());
//
//        /**
//         * 推送给别名参数的用户
//         * @param alias 别名参数（单个）
//         * @param notification_title 通知内容标题
//         * @param msg_title 消息内容标题
//         * @param msg_content 消息内容
//         * @param extrasparam 扩展字段
//         * @return 0推送失败，1推送成功
//         */
//        public static int sendToAlias(String alias, String notification_title, String msg_title, String msg_content, String extrasparam) {
//            int result = 0;
//            try {
//                PushPayload pushPayload= JpushClientUtil.buildPushObject_all_alias_alertWithTitle(alias,notification_title,msg_title,msg_content,extrasparam);
//                System.out.println(pushPayload);
//                PushResult pushResult=jPushClient.sendPush(pushPayload);
//                System.out.println("极光推送返回结果："+pushResult+"，返回状态码："+pushResult.getResponseCode());
//                if(pushResult.getResponseCode()==200){
//                    result=1;
//                }
//            } catch (APIConnectionException e) {
//                e.printStackTrace();
//                log.debug("极光推送服务调用失败！请检查相关连接数据！");
//            } catch (APIRequestException e) {
//                e.printStackTrace();
//                log.debug("极光推送服务调用失败！请检查相关数据！");
//            }
//
//            return result;
//        }
//
//    /**
//     * 推送给别名参数的用户
//     * @param alias 别名参数（多个）
//     * @param notification_title 通知内容标题
//     * @param msg_title 消息内容标题
//     * @param msg_content 消息内容
//     * @param extrasparam 扩展字段
//     * @return 0推送失败，1推送成功
//     */
//    public static int sendToAliasList(List<String> alias, String notification_title, String msg_title, String msg_content,String extrasparam) {
//        int result = 0;
//        try {
//            PushPayload pushPayload= JpushClientUtil.buildPushObject_all_aliasList_alertWithTitle(alias,notification_title,msg_title,msg_content,extrasparam);
//            System.out.println(pushPayload);
//            PushResult pushResult=jPushClient.sendPush(pushPayload);
//            System.out.println(pushResult);
//            if(pushResult.getResponseCode()==200){
//                result=1;
//            }
//        } catch (APIConnectionException e) {
//            e.printStackTrace();
//
//        } catch (APIRequestException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//
//    /**
//     * 推送给别名参数的用户
//     * @param alias 别名参数（多个）
//     * @param notification_title 通知内容标题
//     * @param msg_title 消息内容标题
//     * @param msg_content 消息内容
//     * @param key 扩展字段key
//     * @param extrasparam 扩展字段
//     * @return 0推送失败，1推送成功
//     */
//    public static int sendToAliasListTopic(List<String> alias, String notification_title, String msg_title, String msg_content,String key, String extrasparam) {
//        int result = 0;
//        try {
//            PushPayload pushPayload= JpushClientUtil.buildPushObject_all_aliasList_Topic_alertWithTitle(alias,notification_title,msg_title,msg_content,key,extrasparam);
//            System.out.println(pushPayload);
//            PushResult pushResult=jPushClient.sendPush(pushPayload);
//            System.out.println(pushResult);
//            if(pushResult.getResponseCode()==200){
//                result=1;
//            }
//        } catch (APIConnectionException e) {
//            e.printStackTrace();
//
//        } catch (APIRequestException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//
//
//    /**
//     * 推送给设备标识参数的用户
//     * @param registrationId 设备标识(单一设备)
//     * @param notification_title 通知内容标题
//     * @param msg_title 消息内容标题
//     * @param msg_content 消息内容
//     * @param extrasparam 扩展字段
//     * @return 0推送失败，1推送成功
//     */
//    public static int sendToRegistrationId( String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam) {
//        int result = 0;
//        try {
//            PushPayload pushPayload= JpushClientUtil.buildPushObject_all_registrationId_alertWithTitle(registrationId,notification_title,msg_title,msg_content,extrasparam);
//            System.out.println(pushPayload);
//            PushResult pushResult=jPushClient.sendPush(pushPayload);
//            System.out.println(pushResult);
//            if(pushResult.getResponseCode()==200){
//                result=1;
//            }
//        } catch (APIConnectionException e) {
//            e.printStackTrace();
//
//        } catch (APIRequestException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//
//    /**
//     * 推送给设备标识参数的用户
//     * @param registrationIdList 设备标识(同时发送给多个设备)
//     * @param notification_title 通知内容标题
//     * @param msg_title 消息内容标题
//     * @param msg_content 消息内容
//     * @param extrasparam 扩展字段
//     * @return 0推送失败，1推送成功
//     */
//    public static int sendToRegistrationIdList( List<String> registrationIdList,String notification_title, String msg_title, String msg_content, String extrasparam) {
//        int result = 0;
//        try {
//            PushPayload pushPayload= JpushClientUtil.buildPushObject_all_registrationIdList_alertWithTitle(registrationIdList,notification_title,msg_title,msg_content,extrasparam);
//            System.out.println(pushPayload);
//            PushResult pushResult=jPushClient.sendPush(pushPayload);
//            System.out.println(pushResult);
//            if(pushResult.getResponseCode()==200){
//                result=1;
//            }
//        } catch (APIConnectionException e) {
//            e.printStackTrace();
//
//        } catch (APIRequestException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//
//
//
//        /**
//         * 发送给所有安卓用户
//         * @param notification_title 通知内容标题
//         * @param msg_title 消息内容标题
//         * @param msg_content 消息内容
//         * @param extrasparam 扩展字段
//         * @return 0推送失败，1推送成功
//         */
//        public static int sendToAllAndroid( String notification_title, String msg_title, String msg_content, String extrasparam) {
//            int result = 0;
//            try {
//                PushPayload pushPayload= JpushClientUtil.buildPushObject_android_all_alertWithTitle(notification_title,msg_title,msg_content,extrasparam);
//                System.out.println(pushPayload);
//                PushResult pushResult=jPushClient.sendPush(pushPayload);
//                System.out.println(pushResult);
//                if(pushResult.getResponseCode()==200){
//                    result=1;
//                }
//            } catch (Exception e) {
//
//                e.printStackTrace();
//            }
//
//            return result;
//        }
//
//        /**
//         * 发送给所有IOS用户
//         * @param notification_title 通知内容标题
//         * @param msg_title 消息内容标题
//         * @param msg_content 消息内容
//         * @param extrasparam 扩展字段
//         * @return 0推送失败，1推送成功
//         */
//        public static int sendToAllIos(String notification_title, String msg_title, String msg_content, String extrasparam) {
//            int result = 0;
//            try {
//                PushPayload pushPayload= JpushClientUtil.buildPushObject_ios_all_alertWithTitle(notification_title,msg_title,msg_content,extrasparam);
//                System.out.println(pushPayload);
//                PushResult pushResult=jPushClient.sendPush(pushPayload);
//                System.out.println(pushResult);
//                if(pushResult.getResponseCode()==200){
//                    result=1;
//                }
//            } catch (Exception e) {
//
//                e.printStackTrace();
//            }
//
//            return result;
//        }
//
//        /**
//         * 发送给所有用户
//         * @param notification_title 通知内容标题
//         * @param msg_title 消息内容标题
//         * @param msg_content 消息内容
//         * @param extrasparam 扩展字段
//         * @return 0推送失败，1推送成功
//         */
//        public static int sendToAll( String notification_title, String msg_title, String msg_content, String extrasparam) {
//            int result = 0;
//            try {
//                PushPayload pushPayload= JpushClientUtil.buildPushObject_android_and_ios(notification_title,msg_title,msg_content,extrasparam);
//                System.out.println(pushPayload);
//                PushResult pushResult=jPushClient.sendPush(pushPayload);
//                System.out.println(pushResult);
//                if(pushResult.getResponseCode()==200){
//                    result=1;
//                }
//            } catch (Exception e) {
//
//                e.printStackTrace();
//            }
//
//            return result;
//        }
//
//
//
//        private static PushPayload buildPushObject_android_and_ios(String notification_title, String msg_title, String msg_content, String extrasparam) {
//            return PushPayload.newBuilder()
//                    .setPlatform(Platform.android_ios())
//                    .setAudience(Audience.all())
//                    .setNotification(Notification.newBuilder()
//                            .setAlert(notification_title)
//                            .addPlatformNotification(AndroidNotification.newBuilder()
//                                    .setAlert(notification_title)
//                                    .setTitle(notification_title)
//                                    //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                    .addExtra("androidNotification extras key",extrasparam)
//                                    .build()
//                            )
//                            .addPlatformNotification(IosNotification.newBuilder()
//                                    //传一个IosAlert对象，指定apns title、title、subtitle等
//                                    .setAlert(notification_title)
//                                    //直接传alert
//                                    //此项是指定此推送的badge自动加1
//                                    .incrBadge(1)
//                                    //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
//                                    // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
//                                    .setSound("sound.caf")
//                                    //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                    .addExtra("iosNotification extras key",extrasparam)
//                                    //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
//                                    // .setContentAvailable(true)
//
//                                    .build()
//                            )
//                            .build()
//                    )
//                    //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
//                    // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
//                    // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
//                    .setMessage(Message.newBuilder()
//                            .setMsgContent(msg_content)
//                            .setTitle(msg_title)
//                            .addExtra("message extras key",extrasparam)
//                            .build())
//
//                    .setOptions(Options.newBuilder()
//                            //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
//                            //.setApnsProduction(false)
//                            .setApnsProduction(true)
//                            //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
//                            .setSendno(1)
//                            //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
//                            .setTimeToLive(86400)
//                            .build()
//                    )
//                    .build();
//        }
//
//        private static PushPayload buildPushObject_all_registrationId_alertWithTitle(String registrationId, String notification_title, String msg_title, String msg_content, String extrasparam) {
//
//            System.out.println("----------buildPushObject_all_all_alert");
//            //创建一个IosAlert对象，可指定APNs的alert、title等字段
//            //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();
//
//            return PushPayload.newBuilder()
//                    //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
//                    .setPlatform(Platform.all())
//                    //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
//                    .setAudience(Audience.registrationId(registrationId))
//
//                    //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
//                    .setNotification(Notification.newBuilder()
//                            //指定当前推送的android通知
//                            .addPlatformNotification(AndroidNotification.newBuilder()
//
//                                    .setAlert(msg_title)
//                                    .setTitle(notification_title)
//                                    //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                   // .addExtra("androidNotification extras key",extrasparam)
//                                    .addExtra("msgType",extrasparam)
//                                    .build())
//                            //指定当前推送的iOS通知
//                            .addPlatformNotification(IosNotification.newBuilder()
//                                    //传一个IosAlert对象，指定apns title、title、subtitle等
//                                    .setAlert(msg_title)
//                                    //直接传alert
//                                    //此项是指定此推送的badge自动加1
//                                    .incrBadge(1)
//                                    //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
//                                    // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
//                                    .setSound("sound.caf")
//                                    //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                   // .addExtra("iosNotification extras key",extrasparam)
//                                    .addExtra("msgType",extrasparam)
//                                    //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
//                                    //取消此注释，消息推送时ios将无法在锁屏情况接收
//                                    // .setContentAvailable(true)
//
//                                    .build())
//
//
//                            .build())
//                    //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
//                    // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
//                    // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
//                    .setMessage(Message.newBuilder()
//
//                            .setMsgContent(msg_content)
//
//                            .setTitle(msg_title)
//
//                            .addExtra("message extras key",extrasparam)
//
//                            .build())
//
//                    .setOptions(Options.newBuilder()
//                            //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
//                            //.setApnsProduction(false)
//                            .setApnsProduction(true)
//                            //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
//                            .setSendno(1)
//                            //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
//                            .setTimeToLive(86400)
//
//                            .build())
//
//                    .build();
//
//        }
//
//
//    private static PushPayload buildPushObject_all_registrationIdList_alertWithTitle(List<String> registrationIdList, String notification_title, String msg_title, String msg_content, String extrasparam) {
//
//        System.out.println("----------buildPushObject_all_all_alert");
//        //创建一个IosAlert对象，可指定APNs的alert、title等字段
//        //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();
//
//        return PushPayload.newBuilder()
//                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
//                .setPlatform(Platform.all())
//                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
//               // .setAudience(Audience.registrationId(registrationId))
//                .setAudience(Audience.registrationId(registrationIdList))
//
//                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
//                .setNotification(Notification.newBuilder()
//                        //指定当前推送的android通知
//                        .addPlatformNotification(AndroidNotification.newBuilder()
//                                //消息通知内容
//                                .setAlert(msg_title)
//                                //消息通知标题
//                                .setTitle(notification_title)
//                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                //.addExtra("androidNotification extras key",extrasparam)
//                                .addExtra("msgType",extrasparam)
//                                .build())
//                        //指定当前推送的iOS通知
//                        .addPlatformNotification(IosNotification.newBuilder()
//                                //传一个IosAlert对象，指定apns title、title、subtitle等
//                                .setAlert(notification_title)
//                                //直接传alert
//                                //此项是指定此推送的badge自动加1
//                                .incrBadge(1)
//                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
//                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
//                                .setSound("sound.caf")
//                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                //.addExtra("iosNotification extras key",extrasparam)
//                                .addExtra("msgType",extrasparam)
//                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
//                                //取消此注释，消息推送时ios将无法在锁屏情况接收
//                                // .setContentAvailable(true)
//
//                                .build())
//
//
//                        .build())
//                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
//                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
//                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
//                .setMessage(Message.newBuilder()
//
//                        .setMsgContent(msg_content)
//
//                        .setTitle(msg_title)
//
//                        .addExtra("message extras key",extrasparam)
//
//                        .build())
//
//                .setOptions(Options.newBuilder()
//                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
//                        //.setApnsProduction(false)
//                        .setApnsProduction(true)
//                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
//                        .setSendno(1)
//                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
//                        .setTimeToLive(86400)
//
//                        .build())
//
//                .build();
//
//    }
//
//
//
//    /**
//     * 通过别名来推送消息
//     * @param alias 别名，单条数据
//     * @param notification_title
//     * @param msg_title
//     * @param msg_content
//     * @param extrasparam
//     * @return
//     */
//    private static PushPayload buildPushObject_all_alias_alertWithTitle(String alias, String notification_title, String msg_title, String msg_content, String extrasparam) {
//
//        System.out.println("----------buildPushObject_all_all_alert");
//        //创建一个IosAlert对象，可指定APNs的alert、title等字段
//        //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();
//
//        return PushPayload.newBuilder()
//                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
//                .setPlatform(Platform.all())
//                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
//                .setAudience(Audience.alias(alias))
//                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
//                .setNotification(Notification.newBuilder()
//                        //指定当前推送的android通知
//                        .addPlatformNotification(AndroidNotification.newBuilder()
//
//                                .setAlert(notification_title)
//                               // .setTitle(notification_title)
//                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                .addExtra("androidNotification extras key",extrasparam)
//                                .build())
//                        //指定当前推送的iOS通知
//                        .addPlatformNotification(IosNotification.newBuilder()
//                                //传一个IosAlert对象，指定apns title、title、subtitle等
//                                .setAlert(notification_title)
//                                //直接传alert
//                                //此项是指定此推送的badge自动加1
//                                .incrBadge(1)
//                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
//                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
//                                .setSound("sound.caf")
//                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                .addExtra("iosNotification extras key",extrasparam)
//                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
//                                //取消此注释，消息推送时ios将无法在锁屏情况接收
//                                // .setContentAvailable(true)
//
//                                .build())
//
//
//                        .build())
//                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
//                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
//                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
//                .setMessage(Message.newBuilder()
//
//                        .setMsgContent(msg_content)
//
//                        .setTitle(msg_title)
//
//                        .addExtra("message extras key",extrasparam)
//
//                        .build())
//
//                .setOptions(Options.newBuilder()
//                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
//                        //.setApnsProduction(false)
//                        .setApnsProduction(true)
//                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
//                        .setSendno(1)
//                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
//                        .setTimeToLive(86400) //存活一天（秒）
//
//                        .build())
//
//                .build();
//
//    }
//
//
//    /**
//     * 通过别名来推送消息
//     * @param alias 别名，多条数据
//     * @param notification_title
//     * @param msg_title
//     * @param msg_content
//     * @param extrasparam
//     * @return
//     */
//    private static PushPayload buildPushObject_all_aliasList_alertWithTitle(List<String> alias, String notification_title, String msg_title, String msg_content, String extrasparam) {
//
//        System.out.println("----------buildPushObject_all_all_alert");
//        //创建一个IosAlert对象，可指定APNs的alert、title等字段
//        //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();
//
//        return PushPayload.newBuilder()
//                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
//                .setPlatform(Platform.all())
//                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
//                .setAudience(Audience.alias(alias))
//                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
//                .setNotification(Notification.newBuilder()
//                        //指定当前推送的android通知
//                        .addPlatformNotification(AndroidNotification.newBuilder()
//
//                                .setAlert(notification_title)
//                               // .setTitle(notification_title)
//                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                              //  .addExtra("无聊的",extrasparam)
//                                .addExtra("androidNotification extras key",extrasparam)
//                                .build())
//                        //指定当前推送的iOS通知
//                        .addPlatformNotification(IosNotification.newBuilder()
//                                //传一个IosAlert对象，指定apns title、title、subtitle等
//                                .setAlert(notification_title)
//                                //直接传alert
//                                //此项是指定此推送的badge自动加1
//                                .incrBadge(1)
//                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
//                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
//                                .setSound("sound.caf")
//                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                .addExtra("iosNotification extras key",extrasparam)
//                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
//                                //取消此注释，消息推送时ios将无法在锁屏情况接收
//                                // .setContentAvailable(true)
//
//                                .build())
//
//
//                        .build())
//                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
//                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
//                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
//                .setMessage(Message.newBuilder()
//
//                        .setMsgContent(msg_content)
//
//                        .setTitle(msg_title)
//
//                     .addExtra("message extras key",extrasparam)
//                        .build())
//
//                .setOptions(Options.newBuilder()
//                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
//                        //.setApnsProduction(false)
//                        .setApnsProduction(true)
//                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
//                        .setSendno(1)
//                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
//                        .setTimeToLive(86400) //存活一天（秒）
//
//                        .build())
//
//                .build();
//
//    }
//
//
//    /**
//     * 通过别名来推送消息
//     * @param alias 别名，多条数据
//     * @param notification_title
//     * @param msg_title
//     * @param msg_content
//     * @param topic 扩展字段key
//     * @param extrasparam
//     * @return
//     */
//    private static PushPayload buildPushObject_all_aliasList_Topic_alertWithTitle(List<String> alias, String notification_title, String msg_title, String msg_content, String topic ,String extrasparam) {
//
//        System.out.println("----------buildPushObject_all_all_alert");
//        //创建一个IosAlert对象，可指定APNs的alert、title等字段
//        //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();
//
//        return PushPayload.newBuilder()
//                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
//                .setPlatform(Platform.all())
//                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
//                .setAudience(Audience.alias(alias))
//                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
//                .setNotification(Notification.newBuilder()
//                        //指定当前推送的android通知
//                        .addPlatformNotification(AndroidNotification.newBuilder()
//
//                                .setAlert(notification_title)
//                                // .setTitle(notification_title)
//                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                //  .addExtra("无聊的",extrasparam)
//                                .addExtra(topic,extrasparam)
//
//                                .build())
//                        //指定当前推送的iOS通知
//                        .addPlatformNotification(IosNotification.newBuilder()
//                                //传一个IosAlert对象，指定apns title、title、subtitle等
//                                .setAlert(notification_title)
//                                //直接传alert
//                                //此项是指定此推送的badge自动加1
//                                .incrBadge(1)
//                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
//                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
//                                .setSound("sound.caf")
//                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                //.addExtra("iosNotification extras key",extrasparam)
//                                .addExtra(topic,extrasparam)
//                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
//                                //取消此注释，消息推送时ios将无法在锁屏情况接收
//                                // .setContentAvailable(true)
//
//                                .build())
//
//
//                        .build())
//                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
//                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
//                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
//                .setMessage(Message.newBuilder()
//
//                        .setMsgContent(msg_content)
//
//                        .setTitle(msg_title)
//
//                        .addExtra(topic,extrasparam)
//                        .build())
//
//                .setOptions(Options.newBuilder()
//                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
//                        //.setApnsProduction(false)
//                        .setApnsProduction(true)
//                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
//                        .setSendno(1)
//                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
//                        .setTimeToLive(86400) //存活一天（秒）
//
//                        .build())
//
//                .build();
//
//    }
//
//
//
//
//
//
//
//    private static PushPayload buildPushObject_android_all_alertWithTitle(String notification_title, String msg_title, String msg_content, String extrasparam) {
//            System.out.println("----------buildPushObject_android_registrationId_alertWithTitle");
//            return PushPayload.newBuilder()
//                    //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
//                    .setPlatform(Platform.android())
//                    //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
//                    .setAudience(Audience.all())
//                    //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
//                    .setNotification(Notification.newBuilder()
//                            //指定当前推送的android通知
//                            .addPlatformNotification(AndroidNotification.newBuilder()
//                                    .setAlert(notification_title)
//                                    .setTitle(notification_title)
//                                    //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                    .addExtra("androidNotification extras key",extrasparam)
//                                    .build())
//                            .build()
//                    )
//                    //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
//                    // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
//                    // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
//                    .setMessage(Message.newBuilder()
//                            .setMsgContent(msg_content)
//                            .setTitle(msg_title)
//                            .addExtra("message extras key",extrasparam)
//                            .build())
//
//                    .setOptions(Options.newBuilder()
//                            //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
//                            //.setApnsProduction(false)
//                            .setApnsProduction(true)
//                            //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
//                            .setSendno(1)
//                            //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
//                            .setTimeToLive(86400)
//                            .build())
//                    .build();
//        }
//
//        private static PushPayload buildPushObject_ios_all_alertWithTitle( String notification_title, String msg_title, String msg_content, String extrasparam) {
//            System.out.println("----------buildPushObject_ios_registrationId_alertWithTitle");
//            return PushPayload.newBuilder()
//                    //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
//                    .setPlatform(Platform.ios())
//                    //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
//                    .setAudience(Audience.all())
//                    //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
//                    .setNotification(Notification.newBuilder()
//                            //指定当前推送的android通知
//                            .addPlatformNotification(IosNotification.newBuilder()
//                                    //传一个IosAlert对象，指定apns title、title、subtitle等
//                                    .setAlert(notification_title)
//                                    //直接传alert
//                                    //此项是指定此推送的badge自动加1
//                                    .incrBadge(1)
//                                    //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
//                                    // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
//                                    .setSound("sound.caf")
//                                    //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                    .addExtra("iosNotification extras key",extrasparam)
//                                    //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
//                                    // .setContentAvailable(true)
//
//                                    .build())
//                            .build()
//                    )
//                    //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
//                    // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
//                    // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
//                    .setMessage(Message.newBuilder()
//                            .setMsgContent(msg_content)
//                            .setTitle(msg_title)
//                            .addExtra("message extras key",extrasparam)
//                            .build())
//
//                    .setOptions(Options.newBuilder()
//                            //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
//                            //.setApnsProduction(false)
//                            .setApnsProduction(true)
//                            //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
//                            .setSendno(1)
//                            //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
//                            .setTimeToLive(86400)
//                            .build())
//                    .build();
//        }
//
///**
//    public static void main(String[] args){
//
//        //利用注册id
//        //   int i = JpushClientUtil.sendToRegistrationId("13065ffa4e5adde7e96", "消息推送00000", "订单结算通知", "您有一笔订单已处理,订单金额为：\"800.00\"", "");
//
//        //单个别名
//        //   int i = JpushClientUtil.sendToAlias("349695133", "别名消息推送", "别名订单结算通知", "（别名）您有一笔订单已处理,订单金额为：\"50000.00\"", "");
//
//        //批量别名
//        ArrayList<String> list = Lists.newArrayList();
//        list.add("349695133");
//        list.add("18292576706");
//        int i1 = JpushClientUtil.sendToAliasList(list, "李琪琪生宝宝水电费", "别名订单结算通知", "（别名）您有一笔订单已处理,订单金额为：\"10000.00\"", "");
//
//        System.out.println(i1+"-----"+"////////");
//
//    }
//
//
//   */
//
//
//}
